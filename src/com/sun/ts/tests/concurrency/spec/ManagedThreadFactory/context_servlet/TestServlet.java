/*
 * Copyright (c) 2013, 2020 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.ts.tests.concurrency.spec.ManagedThreadFactory.context_servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.InitialContext;

import com.sun.ts.tests.concurrency.api.common.RunnableTask;
import com.sun.ts.tests.concurrency.api.common.Util;

import jakarta.enterprise.concurrent.ManagedThreadFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TestServlet extends HttpServlet {

  private static final String TEST_JNDI_EVN_ENTRY_VALUE = "hello";

  private static final String TEST_JNDI_EVN_ENTRY_JNDI_NAME = "java:comp/env/ManagedThreadFactory_test_string";

  private static final String TEST_CLASSLOADER_CLASS_NAME = "com.sun.ts.tests.concurrency.spec.ManagedThreadFactory.context_servlet.TestServlet";

  public void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    doPost(req, res);
  }

  public void doPost(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    PrintWriter out = null;

    try {
      res.setContentType("text/plain");
      out = res.getWriter();

      InitialContext context = new InitialContext();
      ManagedThreadFactory factory = (ManagedThreadFactory) context
          .lookup(Util.MANAGED_THREAD_FACTORY_SVC_JNDI_NAME);

      String opName = req.getParameter(Client.SERVLET_OP_ATTR_NAME);
      if (Client.SERVLET_OP_JNDICLASSLOADERPROPAGATIONTEST.equals(opName)) {
        CounterRunnableWithContext task = new CounterRunnableWithContext();
        Thread thread = factory.newThread(task);
        thread.start();
        Util.waitTillThreadFinish(thread);
        Util.assertEquals(1, task.getCount());
      } else {
      }

      out.println(Util.SERVLET_RETURN_SUCCESS);
    } catch (Exception e) {
      if (out != null) {
        out.println(Util.SERVLET_RETURN_FAIL);
        out.println(e);
      }
    } finally {
      if (null != out) {
        out.close();
      }
    }
  }

  public static class CounterRunnableWithContext extends RunnableTask {
    private volatile int count = 0;

    public int getCount() {
      return count;
    }

    public CounterRunnableWithContext() {
      super(TEST_JNDI_EVN_ENTRY_JNDI_NAME, TEST_JNDI_EVN_ENTRY_VALUE,
          TEST_CLASSLOADER_CLASS_NAME);
    }

    public void run() {
      super.run();
      count++;
    }
  }
}
