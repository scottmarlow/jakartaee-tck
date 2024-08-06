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

package ee.jakarta.tck.persistence.core.entitytest.remove.oneXmany;


import java.util.Collection;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "AEJB_1XM_BI_BTOB")
public class A implements java.io.Serializable {

	

	// ===========================================================
	// instance variables

	@Id
	protected String id;

	@Basic
	protected String name;

	@Basic
	protected int value;

	// ===========================================================
	// constructors

	public A() {
		logTrace( "Entity A no arg constructor");
	}

	public A(String id, String name, int value) {
		this.id = id;
		this.name = name;
		this.value = value;
	}

	public A(String id, String name, int value, Collection bCol) {
		this.id = id;
		this.name = name;
		this.value = value;
		this.bCol = bCol;
	}

	// ===========================================================
	// relationship fields

	@OneToMany(targetEntity = ee.jakarta.tck.persistence.core.entitytest.remove.oneXmany.B.class, mappedBy = "a1", cascade = CascadeType.REMOVE)
	protected Collection bCol = new java.util.ArrayList();

	// =======================================================================
	// Business methods for test cases

	public Collection getBCol() {
		logTrace( "getBCol");
		return bCol;
	}

	public void setBCol(Collection c) {
		this.bCol = c;
	}

	public String getAId() {
		return id;
	}

	public String getAName() {
		return name;
	}

	public int getAValue() {
		return value;
	}

}
