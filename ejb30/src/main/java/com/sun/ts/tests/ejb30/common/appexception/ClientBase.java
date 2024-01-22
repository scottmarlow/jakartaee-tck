/*
 * Copyright (c) 2007, 2024 Oracle and/or its affiliates. All rights reserved.
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
 * $Id$
 */
package com.sun.ts.tests.ejb30.common.appexception;

import java.util.Properties;

import com.sun.ts.lib.harness.EETest;
import com.sun.ts.tests.ejb30.common.helper.TLogger;
import com.sun.ts.tests.ejb30.common.helper.TestFailedException;

import jakarta.ejb.EJB;
import org.junit.jupiter.api.Test;

abstract public class ClientBase extends ClientBaseAtOnlyTests {
  @EJB(description = "It should map to <ejb-ref>/<description> xml element.")
  private static AppExceptionIF bean;

  @EJB
  private static RollbackIF rollbackBean;

  protected Properties props;

  /*
   * @class.setup_props:
   */
  public void setup(String[] args, Properties p) {
    props = p;
  }

  public void cleanup() {
  }
  //////////////////////////////////////////////////////////////////////
  // Tests whose name does NOT begin with "at" are for /annotated/
  // directory. It means these exceptions are NOT annotated with
  // @ApplicationException; they are configured as application exceptions
  //////////////////////////////////////////////////////////////////////

  /*
   * testName: checkedAppExceptionTest
   * 
   * @test_Strategy:
   *
   */
  @Test
  public void checkedAppExceptionTest() throws TestFailedException {
    try {
      bean.checkedAppException();
      throw new TestFailedException(
          "Did not get expected exception: CheckedAppException");
    } catch (CheckedAppException e) {
      TLogger.log("Got expected exception: " + e);
    }
  }

  /*
   * testName: checkedAppExceptionTest2
   * 
   * @test_Strategy:
   *
   */
  @Test
  public void checkedAppExceptionTest2() throws TestFailedException {
    try {
      rollbackBean.checkedAppException();
    } catch (CheckedAppException e) {
      throw new TestFailedException("Unexpected exception:", e);
    }
  }

  /*
   * testName: checkedAppExceptionTestLocal
   * 
   * @test_Strategy:
   *
   */
  @Test
  public void checkedAppExceptionTestLocal() throws TestFailedException {
    try {
      rollbackBean.checkedAppExceptionLocal();
    } catch (CheckedAppException e) {
      throw new TestFailedException("Unexpected exception:", e);
    }
  }

  /*
   * testName: uncheckedAppExceptionTest
   * 
   * @test_Strategy:
   *
   */
  @Test
  public void uncheckedAppExceptionTest() throws TestFailedException {
    try {
      bean.uncheckedAppException();
      throw new TestFailedException(
          "Did not get expected exception: UncheckedAppException");
    } catch (UncheckedAppException e) {
      TLogger.log("Got expected exception: " + e);
    }
  }

  /*
   * testName: uncheckedAppExceptionTest2
   * 
   * @test_Strategy:
   *
   */
  @Test
  public void uncheckedAppExceptionTest2() throws TestFailedException {
    try {
      rollbackBean.uncheckedAppException();
    } catch (UncheckedAppException e) {
      throw new TestFailedException("Unexpected exception:", e);
    }
  }

  /*
   * testName: uncheckedAppExceptionTestLocal
   * 
   * @test_Strategy:
   *
   */
  @Test
  public void uncheckedAppExceptionTestLocal() throws TestFailedException {
    try {
      rollbackBean.uncheckedAppExceptionLocal();
    } catch (UncheckedAppException e) {
      throw new TestFailedException("Unexpected exception:", e);
    }
  }

  /*
   * testName: checkedRollbackAppExceptionTest
   * 
   * @test_Strategy:
   *
   */
  @Test
  public void checkedRollbackAppExceptionTest() throws TestFailedException {
    try {
      rollbackBean.checkedRollbackAppException();
    } catch (CheckedRollbackAppException e) {
      throw new TestFailedException("Unexpected exception:", e);
    }
  }

  /*
   * testName: checkedRollbackAppExceptionTestLocal
   * 
   * @test_Strategy:
   *
   */
  @Test
  public void checkedRollbackAppExceptionTestLocal()
      throws TestFailedException {
    try {
      rollbackBean.checkedRollbackAppExceptionLocal();
    } catch (CheckedRollbackAppException e) {
      throw new TestFailedException("Unexpected exception:", e);
    }
  }

  /*
   * testName: uncheckedRollbackAppExceptionTest
   * 
   * @test_Strategy:
   *
   */
  @Test
  public void uncheckedRollbackAppExceptionTest() throws TestFailedException {
    try {
      rollbackBean.uncheckedRollbackAppException();
    } catch (UncheckedRollbackAppException e) {
      throw new TestFailedException("Unexpected exception:", e);
    }
  }

  /*
   * testName: uncheckedRollbackAppExceptionTestLocal
   * 
   * @test_Strategy:
   *
   */
  @Test
  public void uncheckedRollbackAppExceptionTestLocal()
      throws TestFailedException {
    try {
      rollbackBean.uncheckedRollbackAppExceptionLocal();
    } catch (UncheckedRollbackAppException e) {
      throw new TestFailedException("Unexpected exception:", e);
    }
  }

}
