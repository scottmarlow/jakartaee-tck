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

package com.sun.ts.tests.ejb30.tx.common.session.inheritance;

import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;

@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
abstract public class EBeanBase extends TxBeanBase {

  public EBeanBase() {
  }

  // All business methods are inherited from superclass, along with their
  // transaction attribute MANDATORY.
  // The transaction attribute at this class level (REQUIRES_NEW) must not
  // be used.

}
