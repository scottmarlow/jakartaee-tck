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
 * $Id$
 */

package com.sun.ts.tests.common.vehicle.appmanagedNoTx;

import java.util.Properties;

import com.sun.ts.lib.harness.Status;
import com.sun.ts.lib.util.TestUtil;
import com.sun.ts.tests.common.vehicle.VehicleRunnable;
import jakarta.ejb.EJB;

public class AppManagedNoTxVehicleRunner implements VehicleRunnable {

  @EJB(name = "AppManagedNoTxVehicleBean")
  static AppManagedNoTxVehicleIF bean;
  static {
    if (bean == null) {
      throw new IllegalStateException("AppManagedNoTxVehicleRunner could not inject the @EJB AppManagedNoTxVehicleBean");
    }
  }

  public Status run(String[] args, Properties props) {
    Status sTestStatus = null;
    try {

        TestUtil.logTrace(
            "application-managed resource-local runner looked up vehicle: "
                + bean);
      sTestStatus = (bean.runTest(args, props)).toStatus();
    } catch (Exception e) {
      TestUtil.logErr("Test failed.", e);
      sTestStatus = Status.failed(
          "Test run in application-managed resource-local vehicle failed.");
    }
    return sTestStatus;
  }
}
