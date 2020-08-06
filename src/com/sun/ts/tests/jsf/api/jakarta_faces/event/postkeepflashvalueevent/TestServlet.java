/*
 * Copyright (c) 2012, 2020 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.ts.tests.jsf.api.jakarta_faces.event.postkeepflashvalueevent;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.faces.application.Application;
import jakarta.faces.event.PostKeepFlashValueEvent;
import jakarta.faces.event.SystemEvent;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.sun.ts.tests.jsf.api.jakarta_faces.event.common.BaseSystemEventTestServlet;
import com.sun.ts.tests.jsf.common.util.JSFTestUtil;

public class TestServlet extends BaseSystemEventTestServlet {
  private static final String POST_KEY = "postkey";

  @Override
  protected SystemEvent createEvent(Object src) {
    return new PostKeepFlashValueEvent(POST_KEY);
  }

  // ------------------------------------------- PostKeepFlashValueEvent

  public void postKeepFlashValueEventGetKeyTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    Application app = getFacesContext().getApplication();

    if (app != null) {
      PostKeepFlashValueEvent pKeep = (PostKeepFlashValueEvent) createEvent(
          app);
      String result = pKeep.getKey();

      if (!POST_KEY.equals(result)) {
        pw.println(JSFTestUtil.FAIL + " Wrong key value!" + JSFTestUtil.NL
            + "Expected: " + POST_KEY + JSFTestUtil.NL + "Received: " + result);
      } else {
        pw.println(JSFTestUtil.PASS);
      }

    } else {
      pw.println(JSFTestUtil.FAIL + " Unexpected problem obtaining "
          + "Application instance.");
    }
  }

} // TestServlet
