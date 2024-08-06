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

package ee.jakarta.tck.persistence.core.entitytest.cascadeall.manyXone;



import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "BEJB_MX1_UNI_BTOB")
public class B implements java.io.Serializable {

	

	// ===========================================================
	// instance variables
	@Id
	protected String id;

	@Basic
	protected String name;

	@Basic
	protected int value;

	// ===========================================================
	// relationship fields

	@ManyToOne(targetEntity = ee.jakarta.tck.persistence.core.entitytest.cascadeall.manyXone.A.class, cascade = CascadeType.ALL, optional = true)
	@JoinColumn(name = "FK_FOR_AEJB_MX1_UNI_BTOB")
	protected A a1;

	// ===========================================================
	// constructors

	public B() {
		logTrace( "Entity B no arg constructor");
	}

	public B(String id, String name, int value) {
		this.id = id;
		this.name = name;
		this.value = value;
	}

	public B(String id, String name, int value, A a1) {
		this.id = id;
		this.name = name;
		this.value = value;
		this.a1 = a1;
	}

	// ==========================================================
	// Business Methods for Test Cases

	public A getA1() {
		return a1;
	}

	public void setA1(A a1) {
		this.a1 = a1;
	}

	public boolean isA() {
		logTrace( "isA");
		if (getA1() != null)
			logTrace( "Relationship set for A ...");
		else
			logTrace( "Relationship not set for A ...");
		return getA1() != null;
	}

	public A getA1Info() {
		logTrace( "getA1Info");
		if (isA()) {
			A a1 = getA1();
			return a1;
		} else
			return null;
	}

	public String getBId() {
		return id;
	}

	public String getBName() {
		return name;
	}

	public void setBName(String bName) {
		this.name = bName;
	}

	public int getBValue() {
		return value;
	}

}
