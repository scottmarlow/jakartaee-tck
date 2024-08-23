package com.sun.ts.tests.ejb30.timer.schedule.expire;

import com.sun.ts.tests.ejb30.timer.schedule.expire.Client;
import java.net.URL;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.container.test.api.OverProtocol;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.MavenResolvedArtifact;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import tck.arquillian.porting.lib.spi.TestArchiveProcessor;
import tck.arquillian.protocol.common.TargetVehicle;



@ExtendWith(ArquillianExtension.class)
@Tag("ejb")
@Tag("ejb30")
@Tag("platform")
@Tag("tck-javatest")

@TestMethodOrder(MethodOrderer.MethodName.class)
public class ClientEjbliteservletTest extends com.sun.ts.tests.ejb30.timer.schedule.expire.Client {
    static final String VEHICLE_ARCHIVE = "ejb30_timer_schedule_expire_ejbliteservlet_vehicle";

        /**
        EE10 Deployment Descriptors:
        ejb30_timer_schedule_expire_ejbliteservlet_vehicle_web: WEB-INF/web.xml

        Found Descriptors:
        War:

        /com/sun/ts/tests/common/vehicle/ejbliteservlet/ejbliteservlet_vehicle_web.xml
        */
        @TargetsContainer("tck-javatest")
        @OverProtocol("javatest")
        @Deployment(name = VEHICLE_ARCHIVE, order = 2)
        public static WebArchive createDeploymentVehicle(@ArquillianResource TestArchiveProcessor archiveProcessor) {

        // War
            // the war with the correct archive name
            WebArchive ejb30_timer_schedule_expire_ejbliteservlet_vehicle_web = ShrinkWrap.create(WebArchive.class, "ejb30_timer_schedule_expire_ejbliteservlet_vehicle_web.war");
            // The class files
            ejb30_timer_schedule_expire_ejbliteservlet_vehicle_web.addClasses(
            com.sun.ts.lib.harness.EETest.Fault.class,
            com.sun.ts.tests.ejb30.timer.common.JsfClientBase.class,
            com.sun.ts.tests.ejb30.timer.common.TimerBeanBase.class,
            com.sun.ts.tests.common.vehicle.VehicleRunnable.class,
            com.sun.ts.tests.ejb30.common.helper.Helper.class,
            com.sun.ts.tests.ejb30.common.lite.EJBLiteClientBase.class,
            com.sun.ts.tests.ejb30.timer.common.ClientBase.class,
            com.sun.ts.tests.ejb30.timer.common.TimerBeanBaseWithoutTimeOutMethod.class,
            com.sun.ts.tests.ejb30.timer.schedule.expire.EJBLiteServletVehicle.class,
            com.sun.ts.lib.harness.EETest.class,
            com.sun.ts.lib.harness.ServiceEETest.class,
            com.sun.ts.tests.common.vehicle.VehicleClient.class,
            com.sun.ts.tests.ejb30.common.lite.NumberIF.class,
            com.sun.ts.tests.ejb30.timer.schedule.expire.Client.class,
            com.sun.ts.tests.common.vehicle.ejbliteshare.EJBLiteClientIF.class,
            com.sun.ts.tests.common.vehicle.VehicleRunnerFactory.class,
            com.sun.ts.tests.ejb30.timer.common.TimerInfo.class,
            com.sun.ts.tests.ejb30.timer.schedule.expire.HttpServletDelegate.class,
            com.sun.ts.tests.ejb30.timer.schedule.expire.ScheduleBean.class,
            com.sun.ts.tests.common.vehicle.ejbliteshare.ReasonableStatus.class,
            com.sun.ts.tests.ejb30.timer.common.TimeoutStatusBean.class,
            com.sun.ts.tests.ejb30.timer.common.ScheduleValues.class,
            com.sun.ts.tests.ejb30.common.lite.NumberEnum.class,
            com.sun.ts.tests.ejb30.common.lite.EJBLiteJsfClientBase.class,
            com.sun.ts.tests.ejb30.timer.common.ScheduleAttributeType.class,
            com.sun.ts.tests.ejb30.timer.common.TimerUtil.class,
            com.sun.ts.lib.harness.EETest.SetupException.class
            );
            // commons lang jar
            String[] activeMavenProfiles = {"staging"};
            MavenResolvedArtifact resolvedArtifact = Maven.resolver().loadPomFromFile("pom.xml", activeMavenProfiles)
                    .resolve("org.apache.commons:commons-lang3:3.9")
                    .withTransitivity()
                    .asSingleResolvedArtifact();
            ejb30_timer_schedule_expire_ejbliteservlet_vehicle_web.addAsLibraries(resolvedArtifact.asFile());

            // The web.xml descriptor
            URL warResURL = Client.class.getResource("ejbliteservlet_vehicle_web.xml");
            if(warResURL != null) {
              ejb30_timer_schedule_expire_ejbliteservlet_vehicle_web.addAsWebInfResource(warResURL, "web.xml");
            }
            // The sun-web.xml descriptor
            warResURL = Client.class.getResource("/ejbliteservlet_vehicle_web.war.sun-web.xml");
            if(warResURL != null) {
              ejb30_timer_schedule_expire_ejbliteservlet_vehicle_web.addAsWebInfResource(warResURL, "sun-web.xml");
            }

            // Any libraries added to the war

            // Web content
            warResURL = Client.class.getResource("/com/sun/ts/tests/common/vehicle/ejbliteservlet/ejbliteservlet_vehicle_web.xml");
            if(warResURL != null) {
              ejb30_timer_schedule_expire_ejbliteservlet_vehicle_web.addAsWebResource(warResURL, "/WEB-INF/ejbliteservlet_vehicle_web.xml");
            }

           // Call the archive processor
           archiveProcessor.processWebArchive(ejb30_timer_schedule_expire_ejbliteservlet_vehicle_web, Client.class, warResURL);

        return ejb30_timer_schedule_expire_ejbliteservlet_vehicle_web;
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void startNeverExpires() {
            super.startNeverExpires();
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void endNeverExpires() {
            super.endNeverExpires();
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void endBeforeActualValues() {
            super.endBeforeActualValues();
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void startBeforeActualValues() {
            super.startBeforeActualValues();
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void startInTheFuture() {
            super.startInTheFuture();
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void startAfterActualValues() {
            super.startAfterActualValues();
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void startAfterActualValues2() {
            super.startAfterActualValues2();
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void allDefaults() {
            super.allDefaults();
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void timerAccessInTimeoutMethod() {
            super.timerAccessInTimeoutMethod();
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void cancelInTimeoutMethod() {
            super.cancelInTimeoutMethod();
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void leapYears() {
            super.leapYears();
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void dayOfMonth() {
            super.dayOfMonth();
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void dayOfMonthNegative() {
            super.dayOfMonthNegative();
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void dayOfMonthNthDayFeb() {
            super.dayOfMonthNthDayFeb();
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void dayOfMonthNthDayJan() {
            super.dayOfMonthNthDayJan();
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void dayOfWeekAll() {
            super.dayOfWeekAll();
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void dayOfWeekSunday() {
            super.dayOfWeekSunday();
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void dayOfWeekSunday0To7() {
            super.dayOfWeekSunday0To7();
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void dayOfWeekSunday2() {
            super.dayOfWeekSunday2();
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void dayOfWeekNow() {
            super.dayOfWeekNow();
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void dayOfWeekList() {
            super.dayOfWeekList();
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void dayOfWeekListComplex() {
            super.dayOfWeekListComplex();
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void dayOfWeekListOverlap() {
            super.dayOfWeekListOverlap();
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void dayOfWeekRange() {
            super.dayOfWeekRange();
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void monthList() {
            super.monthList();
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void monthRange() {
            super.monthRange();
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void monthListComplex() {
            super.monthListComplex();
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void monthListOverlap() {
            super.monthListOverlap();
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void monthAll() {
            super.monthAll();
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void yearList() {
            super.yearList();
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void yearRange() {
            super.yearRange();
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void yearListComplex() {
            super.yearListComplex();
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void yearListOverlap() {
            super.yearListOverlap();
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void incrementSecond1() {
            super.incrementSecond1();
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void incrementSecond2() {
            super.incrementSecond2();
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void incrementSecond3() {
            super.incrementSecond3();
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void incrementMinute1() {
            super.incrementMinute1();
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void incrementMinute2() {
            super.incrementMinute2();
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void incrementHour1() {
            super.incrementHour1();
        }

        @Test
        @Override
        @TargetVehicle("ejbliteservlet")
        public void incrementHour2() {
            super.incrementHour2();
        }


}