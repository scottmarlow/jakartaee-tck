/*
 * Copyright (c) 2008, 2023 Oracle and/or its affiliates. All rights reserved.
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

package ee.jakarta.tck.persistence.core.annotations.basic;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Client1 extends Client {



	public JavaArchive createDeployment() throws Exception {
		String pkgNameWithoutSuffix = Client.class.getPackageName();
		String pkgName = Client.class.getPackageName() + ".";
		String[] classes = { pkgName + "A" };
		return createDeploymentJar("jpa_core_annotations_basic1.jar", pkgNameWithoutSuffix, classes);

	}

	public Client1() {
	}

	@BeforeEach
	public void setup() throws Exception {
		logTrace( "setup");
		try {
			super.setup();
			createDeployment();
			removeTestData();
		} catch (Exception e) {
			throw new Exception("Setup failed:", e);

		}
	}

	/*
	 * @testName: persistBasicTest1
	 * 
	 * @assertion_ids: PERSISTENCE:SPEC:613; PERSISTENCE:SPEC:614;
	 * PERSISTENCE:SPEC:671; PERSISTENCE:SPEC:675; PERSISTENCE:SPEC:1069;
	 * PERSISTENCE:SPEC:1072; PERSISTENCE:SPEC:1073; PERSISTENCE:SPEC:1074;
	 * PERSISTENCE:SPEC:1075; PERSISTENCE:SPEC:1076; PERSISTENCE:SPEC:1077;
	 * PERSISTENCE:SPEC:1078; PERSISTENCE:SPEC:1080; PERSISTENCE:SPEC:1081;
	 * PERSISTENCE:SPEC:1082; PERSISTENCE:JAVADOC:308; PERSISTENCE:SPEC:1990;
	 *
	 * 
	 * @test_Strategy: A new entity bean instance has no persistent identity and is
	 * not yet associated to a persistent context.
	 *
	 * The contains method (used to determine whether an entity instance is in the
	 * managed state in the current persistence context) returns false:
	 *
	 * If the instance is new and the persist method has not been on the entity.
	 *
	 * Instantiate an entity and verify the contains method returns false.
	 */
	@Test
	public void persistBasicTest1() throws Exception {

		logTrace( "Begin persistBasicTest1");

		boolean pass = false;
		A aRef;
		try {

			final Integer integer = 1234;
			final short basicShort = 12;
			final Short basicBigShort = basicShort;
			final float basicFloat = 12.3f;
			final Float basicBigFloat = basicFloat;
			final long basicLong = 1234l;
			final Long basicBigLong = basicLong;
			final double basicDouble = 1234.5;
			final Double basicBigDouble = basicDouble;
			final char[] charArray = { 'a', 'b', 'c' };
			final Character[] bigCharacterArray = { 'a', 'b', 'c' };
			final byte[] byteArray = "abc".getBytes();
			final Byte[] bigByteArray = { (byte) 111, (byte) 101, (byte) 100 };
			final BigInteger bigInteger = new BigInteger("12345");
			final BigDecimal bigDecimal = new BigDecimal(bigInteger);
			final Date date = new Date();
			final long timeInSeconds = date.getTime();
			final Time time = new Time(timeInSeconds);
			final Timestamp timeStamp = new Timestamp(timeInSeconds);
			final Calendar calendar = Calendar.getInstance();

			aRef = new A("1", "a1", 1, integer, basicShort, basicBigShort, basicFloat, basicBigFloat, basicLong,
					basicBigLong, basicDouble, basicBigDouble, 'a', charArray, bigCharacterArray, byteArray,
					bigByteArray, bigInteger, bigDecimal, date, time, timeStamp, calendar);

			getEntityTransaction().begin();
			if (!getInstanceStatus(aRef)) {
				pass = true;
			}
			getEntityTransaction().commit();

		} catch (Exception e) {
			logErr( "Unexpected exception occurred", e);
		} finally {
			try {
				if (getEntityTransaction().isActive()) {
					getEntityTransaction().rollback();
				}
			} catch (Exception re) {
				logErr( "Unexpected Exception in rollback:", re);
			}
		}

		if (!pass) {
			throw new Exception("persistBasicTest1 failed");
		}

	}

	/*
	 * @testName: persistBasicTest2
	 * 
	 * @assertion_ids: PERSISTENCE:SPEC:613; PERSISTENCE:SPEC:615;
	 * PERSISTENCE:SPEC:619; PERSISTENCE:SPEC:667; PERSISTENCE:SPEC:669;
	 * PERSISTENCE:SPEC:1069; PERSISTENCE:SPEC:1073; PERSISTENCE:SPEC:1074;
	 * PERSISTENCE:SPEC:1075; PERSISTENCE:SPEC:1076; PERSISTENCE:SPEC:1077;
	 * PERSISTENCE:SPEC:1078; PERSISTENCE:SPEC:1080; PERSISTENCE:SPEC:1081;
	 * PERSISTENCE:SPEC:1082; PERSISTENCE:SPEC:1072;
	 * 
	 * @test_Strategy: The new entity bean instance becomes both managed and
	 * persistent by invoking the persist method on it. The semantics of the persist
	 * operation as applied to entity X is as follows:
	 *
	 * If X is a new entity, it becomes managed.
	 *
	 * Invoke persist on the new entity. Find the entity instance and ensure it is
	 * managed by calling contains() verifying it returns true.
	 */
	@Test
	public void persistBasicTest2() throws Exception {

		logTrace( "Begin persistBasicTest2");

		boolean pass = false;
		A aRef;

		try {
			logTrace( "new A");

			final Integer integer = 1234;
			final short basicShort = 12;
			final Short basicBigShort = basicShort;
			final float basicFloat = 12.3f;
			final Float basicBigFloat = basicFloat;
			final long basicLong = 1234l;
			final Long basicBigLong = basicLong;
			final double basicDouble = 1234.5;
			final Double basicBigDouble = basicDouble;
			final char[] charArray = { 'a', 'b', 'c' };
			final Character[] bigCharacterArray = { 'a', 'b', 'c' };
			final byte[] byteArray = "abc".getBytes();
			final Byte[] bigByteArray = { (byte) 111, (byte) 101, (byte) 100 };
			final BigInteger bigInteger = new BigInteger("12345");
			final BigDecimal bigDecimal = new BigDecimal(bigInteger);
			final Date date = new Date();
			final long timeInSeconds = date.getTime();
			final Time time = new Time(timeInSeconds);
			final Timestamp timeStamp = new Timestamp(timeInSeconds);
			final Calendar calendar = Calendar.getInstance();

			aRef = new A("2", "a2", 2, integer, basicShort, basicBigShort, basicFloat, basicBigFloat, basicLong,
					basicBigLong, basicDouble, basicBigDouble, 'a', charArray, bigCharacterArray, byteArray,
					bigByteArray, bigInteger, bigDecimal, date, time, timeStamp, calendar);

			createA(aRef);

			A newA = findA("2");

			if (newA != null) {
				logTrace( "A IS NOT NULL");
			}

			getEntityTransaction().begin();
			pass = getInstanceStatus(findA("2"));
			getEntityTransaction().commit();

		} catch (Exception e) {
			logErr( "Unexpected exception occurred", e);
		} finally {
			try {
				if (getEntityTransaction().isActive()) {
					getEntityTransaction().rollback();
				}
			} catch (Exception re) {
				logErr( "Unexpected Exception in rollback:", re);
			}
		}

		if (!pass) {
			throw new Exception("persistBasicTest2 failed");
		}

	}

	/*
	 * @testName: persistBasicTest3
	 * 
	 * @assertion_ids: PERSISTENCE:SPEC:613; PERSISTENCE:SPEC:618;
	 * PERSISTENCE:SPEC:620; PERSISTENCE:SPEC:1069; PERSISTENCE:SPEC:1073;
	 * PERSISTENCE:SPEC:1074; PERSISTENCE:SPEC:1075; PERSISTENCE:SPEC:1076;
	 * PERSISTENCE:SPEC:1077; PERSISTENCE:SPEC:1078; PERSISTENCE:SPEC:1080;
	 * PERSISTENCE:SPEC:1081; PERSISTENCE:SPEC:1082; PERSISTENCE:SPEC:1072;
	 * 
	 * @test_Strategy: The new entity bean instance becomes both managed and
	 * persistent by invoking the persist method on it. The semantics of the persist
	 * operation as applied to entity X is as follows:
	 *
	 * The entity X will be entered into the database at or before transaction
	 * commit or as a result of the flush operation.
	 *
	 * Create a new entity instance, invoke flush(), then attempt to access the
	 * entity by find and invoking a query on it.
	 *
	 */
	@Test
	public void persistBasicTest3() throws Exception {

		logTrace( "Begin persistBasicTest3");

		String aName = null;
		boolean pass = false;
		Object result;
		A a1;

		try {
			getEntityTransaction().begin();

			final Integer integer = 1234;
			final short basicShort = 12;
			final Short basicBigShort = basicShort;
			final float basicFloat = 12.3f;
			final Float basicBigFloat = basicFloat;
			final long basicLong = 1234l;
			final Long basicBigLong = basicLong;
			final double basicDouble = 1234.5;
			final Double basicBigDouble = basicDouble;
			final char[] charArray = { 'a', 'b', 'c' };
			final Character[] bigCharacterArray = { 'a', 'b', 'c' };
			final byte[] byteArray = "abc".getBytes();
			final Byte[] bigByteArray = { (byte) 111, (byte) 101, (byte) 100 };
			final BigInteger bigInteger = new BigInteger("12345");
			final BigDecimal bigDecimal = new BigDecimal(bigInteger);
			final Date date = new Date();
			final long timeInSeconds = date.getTime();
			final Time time = new Time(timeInSeconds);
			final Timestamp timeStamp = new Timestamp(timeInSeconds);
			final Calendar calendar = Calendar.getInstance();

			a1 = new A("3", "a3", 3, integer, basicShort, basicBigShort, basicFloat, basicBigFloat, basicLong,
					basicBigLong, basicDouble, basicBigDouble, 'a', charArray, bigCharacterArray, byteArray,
					bigByteArray, bigInteger, bigDecimal, date, time, timeStamp, calendar);

			logTrace( "Persist Instance");
			getEntityManager().persist(a1);
			getEntityManager().flush();

			logTrace( "find By Name");
			result = (A) findByName("a3");

			logTrace( "Check to see that the entities are identical");
			if (result == a1) {
				pass = true;
			}
			getEntityTransaction().commit();
		} catch (Exception e) {
			logErr( "Unexpected exception occurred", e);
		} finally {
			try {
				if (getEntityTransaction().isActive()) {
					getEntityTransaction().rollback();
				}
			} catch (Exception fe) {
				logErr( "Unexpected exception rolling back transaction", fe);
			}
		}

		if (!pass) {
			throw new Exception("persistBasicTest3 failed");
		}

	}

	/*
	 * @testName: persistBasicTest4
	 * 
	 * @assertion_ids: PERSISTENCE:SPEC:613; PERSISTENCE:SPEC:618;
	 * PERSISTENCE:SPEC:621; PERSISTENCE:SPEC:1069; PERSISTENCE:SPEC:1073;
	 * PERSISTENCE:SPEC:1074; PERSISTENCE:SPEC:1075; PERSISTENCE:SPEC:1076;
	 * PERSISTENCE:SPEC:1077; PERSISTENCE:SPEC:1078; PERSISTENCE:SPEC:1080;
	 * PERSISTENCE:SPEC:1081; PERSISTENCE:SPEC:1082; PERSISTENCE:SPEC:1072;
	 * 
	 * @test_Strategy: The new entity bean instance becomes both managed and
	 * persistent by invoking the persist method on it. The semantics of the persist
	 * operation as applied to entity X is as follows:
	 *
	 * If X is preexisting managed entity, it is ignored by the persist operation.
	 *
	 * Invoke persist on an already managed instance. Ensure no exception is thrown
	 * and that the entity is still persisted and managed.
	 *
	 */
	@Test
	public void persistBasicTest4() throws Exception {

		logTrace( "Begin persistBasicTest4");

		boolean pass = false;
		A aRef;
		try {

			logTrace( "Persist Instance");

			final Integer integer = 1234;
			final short basicShort = 12;
			final Short basicBigShort = basicShort;
			final float basicFloat = 12.3f;
			final Float basicBigFloat = basicFloat;
			final long basicLong = 1234l;
			final Long basicBigLong = basicLong;
			final double basicDouble = 1234.5;
			final Double basicBigDouble = basicDouble;
			final char[] charArray = { 'a', 'b', 'c' };
			final Character[] bigCharacterArray = { 'a', 'b', 'c' };
			final byte[] byteArray = "abc".getBytes();
			final Byte[] bigByteArray = { (byte) 111, (byte) 101, (byte) 100 };
			final BigInteger bigInteger = new BigInteger("12345");
			final BigDecimal bigDecimal = new BigDecimal(bigInteger);
			final Date date = new Date();
			final long timeInSeconds = date.getTime();
			final Time time = new Time(timeInSeconds);
			final Timestamp timeStamp = new Timestamp(timeInSeconds);
			final Calendar calendar = Calendar.getInstance();

			aRef = new A("4", "a4", 4, integer, basicShort, basicBigShort, basicFloat, basicBigFloat, basicLong,
					basicBigLong, basicDouble, basicBigDouble, 'a', charArray, bigCharacterArray, byteArray,
					bigByteArray, bigInteger, bigDecimal, date, time, timeStamp, calendar);

			createA(aRef);

			getEntityTransaction().begin();
			logTrace( "get Instance Status ");
			if (getInstanceStatus(findA("4"))) {
				try {
					logTrace( "entity is managed, try to persist again ");
					A newA = findA("4");
					getEntityManager().persist(newA);
					logTrace( "Persist ignored on an already persisted entity as expected");
					pass = true;
				} catch (Exception ee) {
					logErr(
							"Unexpected exception trying to persist an" + " already persisted entity", ee);
					pass = false;
				}

			} else {
				logTrace( "Instance is not managed. Test Fails.");
				pass = false;
			}

			getEntityTransaction().commit();
		} catch (Exception e) {
			logErr( "Unexpected exception occurred", e);
		} finally {
			try {
				if (getEntityTransaction().isActive()) {
					getEntityTransaction().rollback();
				}
			} catch (Exception re) {
				logErr( "Unexpected exception rolling back transaction", re);
			}
		}

		if (!pass) {
			throw new Exception("persistBasicTest4 failed");
		}
	}

	/*
	 * @testName: persistBasicTest5
	 * 
	 * @assertion_ids: PERSISTENCE:SPEC:613; PERSISTENCE:SPEC:618;
	 * PERSISTENCE:SPEC:641; PERSISTENCE:SPEC:642; PERSISTENCE:SPEC:1069;
	 * PERSISTENCE:SPEC:1072; PERSISTENCE:SPEC:1073; PERSISTENCE:SPEC:1074;
	 * PERSISTENCE:SPEC:1075; PERSISTENCE:SPEC:1076; PERSISTENCE:SPEC:1077;
	 * PERSISTENCE:SPEC:1078; PERSISTENCE:SPEC:1080; PERSISTENCE:SPEC:1081;
	 * PERSISTENCE:SPEC:1082; PERSISTENCE:JAVADOC:8
	 * 
	 * @test_Strategy: The flush method can be used for force synchronization. The
	 * semantics of the flush operation applied to an entity X is as follows:
	 *
	 * If X is a managed entity, it is synchronized to the database.
	 *
	 * Execute flush on a managed entity and ensure the database reflects the
	 * change.
	 *
	 */
	@Test
	public void persistBasicTest5() throws Exception {

		logTrace( "Begin persistBasicTest5");
		A a2;
		A aRef;

		boolean pass = true;
		try {
			final Integer integer = 1234;
			final short basicShort = 12;
			final Short basicBigShort = basicShort;
			final float basicFloat = 12.3f;
			final Float basicBigFloat = basicFloat;
			final long basicLong = 1234l;
			final Long basicBigLong = basicLong;
			final double basicDouble = 1234.5;
			final Double basicBigDouble = basicDouble;
			final char[] charArray = { 'a', 'b', 'c' };
			final Character[] bigCharacterArray = { 'a', 'b', 'c' };
			final byte[] byteArray = "abc".getBytes();
			final Byte[] bigByteArray = { (byte) 111, (byte) 101, (byte) 100 };
			final BigInteger bigInteger = new BigInteger("12345");
			final BigDecimal bigDecimal = new BigDecimal(bigInteger);
			final Date date = new Date();
			final long timeInSeconds = date.getTime();
			final Time time = new Time(timeInSeconds);
			final Timestamp timeStamp = new Timestamp(timeInSeconds);
			final Calendar calendar = Calendar.getInstance();

			aRef = new A("5", null, 5, integer, basicShort, basicBigShort, basicFloat, basicBigFloat, basicLong,
					basicBigLong, basicDouble, basicBigDouble, 'a', charArray, bigCharacterArray, byteArray,
					bigByteArray, bigInteger, bigDecimal, date, time, timeStamp, calendar);

			logTrace( "Persist Instance");
			createA(aRef);

			getEntityTransaction().begin();
			logTrace( "get Instance Status ");
			if (getInstanceStatus(findA("5"))) {
				try {
					logTrace( "entity is managed, try to change name and flush ");
					a2 = findA("5");
					if (a2.getName() == null) {
						logTrace( "Received Name as null");
					} else {
						logErr( "Expected null name, actual:" + a2.getName());
						pass = false;
					}
					a2.setName("a2");
					getEntityManager().flush();
					if (a2.getName().equals("a2")) {
						logTrace( "sync to database successful");
					}
				} catch (Exception ee) {
					logErr( "Unexpected exception trying to flush a" + "persisted entity", ee);
					pass = false;
				}

			} else {
				logTrace( "Instance is not already persisted. Test Fails.");
				pass = false;
			}
			getEntityTransaction().commit();

		} catch (Exception e) {
			logErr( "Unexpected exception occurred", e);
		} finally {
			try {
				if (getEntityTransaction().isActive()) {
					getEntityTransaction().rollback();
				}
			} catch (Exception re) {
				logErr( "Unexpected exception rolling back transaction", re);
			}

		}

		if (!pass) {
			throw new Exception("persistBasicTest5 failed");
		}
	}

	/*
	 * Business Methods for Test Cases
	 */
	private void createA(final A a) {
		logTrace( "Entered createA method");
		getEntityTransaction().begin();
		getEntityManager().persist(a);
		// WORKAROUND
		getEntityManager().flush();
		getEntityTransaction().commit();
	}

	private A findA(final String id) {
		logTrace( "Entered findA method");
		return getEntityManager().find(A.class, id);
	}

	private Object findByName(final String name) {
		logTrace( "Entered findByName method");
		return getEntityManager().createQuery("select a from A a where a.name = :name").setParameter("name", name)
				.getSingleResult();
	}

	private boolean getInstanceStatus(final Object o) {
		logTrace( "Entered getInstanceStatus method");
		return getEntityManager().contains(o);
	}

}
