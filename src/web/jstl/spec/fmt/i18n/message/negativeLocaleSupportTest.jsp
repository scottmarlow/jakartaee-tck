<%--

    Copyright (c) 2004, 2020 Oracle and/or its affiliates. All rights reserved.

    This program and the accompanying materials are made available under the
    terms of the Eclipse Public License v. 2.0, which is available at
    http://www.eclipse.org/legal/epl-2.0.

    This Source Code may also be made available under the following Secondary
    Licenses when the conditions for such availability set forth in the
    Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
    version 2 with the GNU Classpath Exception, which is available at
    https://www.gnu.org/software/classpath/license.html.

    SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0

--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tck" uri="http://java.sun.com/jstltck/jstltck-util" %>
<%@ page import="jakarta.servlet.jsp.jstl.fmt.LocaleSupport" %>

<tck:test testName="localSupportTest">
  <c:set var="base" value="com.sun.ts.tests.jstl.common.resources.InvalidResources"/>
  <fmt:setBundle basename="com.sun.ts.tests.jstl.common.resources.Resources"/>
  
  <!-- nkey does not exist in com.sun.ts.tests.jstl.common.resources.Resources -->
  (pageContext,key):
  <%= LocaleSupport.getLocalizedMessage(pageContext, "nkey") %>
  
  <!-- base is invalid -->
  (pageContext,key,basename):
  <%= LocaleSupport.getLocalizedMessage(pageContext, "mkey", 
            (String) pageContext.getAttribute("base", PageContext.PAGE_SCOPE)) %>
  
  <!-- nkey does not exist in com.sun.ts.tests.jstl.common.resources.Resources -->
  (pageContext,key,args):
  <%= LocaleSupport.getLocalizedMessage(pageContext, "nkey", 
            new Object[] {"Monday", "Tuesday"} ) %>
  
  <!-- base is invalid -->
  (pageContext,key,args,basename):
  <%= LocaleSupport.getLocalizedMessage(pageContext, "pkey", 
            new Object[] {"Monday", "Tuesday"},
            (String) pageContext.getAttribute("base", PageContext.PAGE_SCOPE)) %>
</tck:test>
