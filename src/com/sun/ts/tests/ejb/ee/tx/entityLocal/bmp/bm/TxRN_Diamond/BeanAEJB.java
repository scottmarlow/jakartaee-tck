/*
 * Copyright (c) 2007, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

/*
 * @(#)BeanAEJB.java	1.19 03/05/16
 */

package com.sun.ts.tests.ejb.ee.tx.entityLocal.bmp.bm.TxRN_Diamond;

import com.sun.ts.lib.util.*;
import com.sun.ts.lib.porting.*;
import com.sun.ts.tests.ejb.ee.tx.txEbeanLocal.*;

import java.util.*;
import java.rmi.*;
import jakarta.ejb.*;
import jakarta.transaction.*;

public class BeanAEJB implements SessionBean {

  // testProps represent the test specific properties passed in
  // from the test harness.
  private Properties testProps = null;

  // The TSNamingContext abstracts away the underlying distribution protocol.
  private TSNamingContext jctx = null;

  private SessionContext sctx = null;

  // Bean variables
  private TxEBeanHome beanHome = null;

  private TxEBean RefTxEBean = null;

  private BeanCHome HomeC = null;

  private BeanC RefC = null;

  private BeanBHome HomeB = null;

  private BeanB RefB = null;

  private static final String txEBeanRequiresNew = "java:comp/env/ejb/TxRequiresNew";

  private static final String lookupBeanB = "java:comp/env/ejb/BeanB";

  private static final String lookupBeanC = "java:comp/env/ejb/BeanC";

  // Table Name variables
  private String tName1 = null;

  // The requiredEJB methods
  public void ejbCreate() throws CreateException {
    TestUtil.logTrace("ejbCreate");
  }

  public void ejbCreate(Properties p) throws CreateException {
    TestUtil.logTrace("ejbCreate w/Properties");

    try {
      initLogging(p);
      TestUtil.logTrace("Call to initLogging DONE");
    } catch (Exception e) {
      TestUtil.printStackTrace(e);
      TestUtil.logTrace("Exception from initLogging - BeanA");
    }

    try {
      TestUtil.logMsg("Getting Naming Context");
      jctx = new TSNamingContext();

      // TxEBean - BeanD
      TestUtil
          .logMsg("Getting the EJB Home interface for " + txEBeanRequiresNew);
      beanHome = (TxEBeanHome) jctx.lookup(txEBeanRequiresNew,
          TxEBeanHome.class);
      TestUtil.logMsg("Creating EJB TxEBean(RequiresNew) instance");
      RefTxEBean = (TxEBean) beanHome.create(tName1, 1, tName1 + "-1",
          (float) 1, testProps);

      // BeanC
      TestUtil.logMsg("Getting the EJB Home interface for " + lookupBeanC);
      HomeC = (BeanCHome) jctx.lookup(lookupBeanC, BeanCHome.class);
      TestUtil.logMsg("Creating EJB BeanC instance");
      RefC = (BeanC) HomeC.create(testProps);

      // BeanB
      TestUtil.logMsg("Getting the EJB Home interface for " + lookupBeanB);
      HomeB = (BeanBHome) jctx.lookup(lookupBeanB, BeanBHome.class);
      TestUtil.logMsg("Creating EJB BeanB instance");
      RefB = (BeanB) HomeB.create(testProps);

    } catch (Exception e) {
      TestUtil.logErr("Create exception", e);
    }
  }

  public void setSessionContext(SessionContext sc) {
    TestUtil.logTrace("setSessionContext");
    this.sctx = sc;
  }

  public void ejbRemove() {
    TestUtil.logTrace("ejbRemove");
  }

  public void ejbActivate() {
    TestUtil.logTrace("ejbActivate");
  }

  public void ejbPassivate() {
    TestUtil.logTrace("ejbPassivate");
  }

  // ===========================================================
  // BeanA interface (our business methods)

  public boolean test1() {
    TestUtil.logTrace("test1");
    TestUtil
        .logTrace("Commit a transaction involving a Tx Diamond - RequiresNew");

    boolean testResult, b1, b2, b3, b4;
    testResult = b1 = b2 = b3 = b4 = false;

    String expName = "BeanCBrand";
    String tempName1, tempName2;
    tempName1 = tempName2 = null;

    UserTransaction ut = null;

    try {
      TestUtil.logTrace("Get UserTransaction interface");
      ut = sctx.getUserTransaction();

      TestUtil.logTrace("BEGIN Tx");
      ut.begin();
      b1 = RefB.helloB();
      b2 = RefC.helloC();
      TestUtil.logTrace("COMMIT Tx");

      // Because of the nature of the Entity Bean D, RequiresNew, the change
      // already got committed in Beans B and C. This tx commit should do
      // nothing.
      ut.commit();

      // Verify the results
      tempName1 = RefTxEBean.getBrandName();
      TestUtil.logTrace("Instance Brand Name is " + tempName1);
      if (tempName1.equals(expName))
        b3 = true;

      tempName2 = RefTxEBean.getDbBrandName();
      TestUtil.logTrace("DB Brand Name is " + tempName2);
      if (tempName2.equals(expName))
        b4 = true;

      if (!b3) {
        TestUtil
            .logMsg("Brand Name instance value did not match expected value");
        TestUtil.logMsg("Expected: " + expName + ", Actual: " + tempName1);
      }
      if (!b4) {
        TestUtil.logMsg("Brand Name DB value did not match expected value");
        TestUtil.logMsg("Expected: " + expName + ", Actual: " + tempName2);
      }

      if (b1 && b2 && b3 && b4)
        testResult = true;
      return testResult;

    } catch (Exception e) {
      try {
        if (ut.getStatus() != Status.STATUS_NO_TRANSACTION)
          ut.rollback();
      } catch (SystemException se) {
        TestUtil.logErr("Exception checking transaction status", se);
      }

      TestUtil.logErr("Unexpected exception caught", e);
      throw new EJBException(e.getMessage());
    } finally {
      // cleanup the beans
      if (RefB != null)
        try {
          RefB.remove();
        } catch (Exception e) {
          TestUtil.logErr("Exception removing Bean B", e);
        }
      ;

      if (RefC != null)
        try {
          RefC.remove();
        } catch (Exception e) {
          TestUtil.logErr("Exception removing Bean C", e);
        }
      ;

      if (RefTxEBean != null)
        try {
          RefTxEBean.remove();
        } catch (Exception e) {
          TestUtil.logErr("Exception removing Bean TxEBean", e);
        }
    }
  }

  public boolean test2() {
    TestUtil.logTrace("test2");
    TestUtil.logTrace(
        "Rollback a transaction involving a Tx Diamond - RequiresNew");

    boolean testResult, b1, b2, b3, b4;
    testResult = b1 = b2 = b3 = b4 = false;

    String expName = "BeanCBrand";
    String tempName1, tempName2;
    tempName1 = tempName2 = null;

    UserTransaction ut = null;

    try {
      TestUtil.logTrace("Get UserTransaction interface");
      ut = sctx.getUserTransaction();

      TestUtil.logTrace("BEGIN Tx");
      ut.begin();
      b1 = RefB.helloB();
      b2 = RefC.helloC();
      TestUtil.logTrace("ROLLBACK Tx");

      // Because of the nature of the Entity Bean D, RequiresNew, the change
      // already got committed in Beans B and C. This tx rollback should do
      // nothing.
      ut.rollback();

      // Verify the results
      tempName1 = RefTxEBean.getBrandName();
      TestUtil.logTrace("Instance Brand Name is " + tempName1);
      if (tempName1.equals(expName))
        b3 = true;

      tempName2 = RefTxEBean.getDbBrandName();
      TestUtil.logTrace("DB Brand Name is " + tempName2);
      if (tempName2.equals(expName))
        b4 = true;

      if (!b3) {
        TestUtil
            .logMsg("Brand Name instance value did not match expected value");
        TestUtil.logMsg("Expected: " + expName + ", Actual: " + tempName1);
      }
      if (!b4) {
        TestUtil.logMsg("Brand Name DB value did not match expected value");
        TestUtil.logMsg("Expected: " + expName + ", Actual: " + tempName2);
      }

      if (b1 && b2 && b3 && b4)
        testResult = true;
      return testResult;

    } catch (Exception e) {
      try {
        if (ut.getStatus() != Status.STATUS_NO_TRANSACTION)
          ut.rollback();
      } catch (SystemException se) {
        TestUtil.logErr("Exception checking transaction status", se);
      }

      TestUtil.logErr("Unexpected exception caught", e);
      throw new EJBException(e.getMessage());
    } finally {
      // cleanup the beans
      if (RefB != null)
        try {
          RefB.remove();
        } catch (Exception e) {
          TestUtil.logErr("Exception removing Bean B", e);
        }
      ;

      if (RefC != null)
        try {
          RefC.remove();
        } catch (Exception e) {
          TestUtil.logErr("Exception removing Bean C", e);
        }
      ;

      if (RefTxEBean != null)
        try {
          RefTxEBean.remove();
        } catch (Exception e) {
          TestUtil.logErr("Exception removing Bean TxEBean", e);
        }
    }
  }

  private void initLogging(Properties p) {
    TestUtil.logTrace("initLogging");
    this.testProps = p;
    try {
      TestUtil.init(p);

      // Get the table names
      TestUtil.logTrace("Looking up the table names");
      this.tName1 = TestUtil
          .getTableName(TestUtil.getProperty("TxEBean_Delete"));
      TestUtil.logTrace("tName1: " + this.tName1);

    } catch (RemoteLoggingInitException e) {
      TestUtil.logErr("RemoteLoggingInitException", e);
      throw new EJBException(e.getMessage());
    }
  }

}
