package ee.jakarta.tck.persistence.core.annotations.orderby;

import ee.jakarta.tck.persistence.core.annotations.orderby.Client3;
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
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import tck.arquillian.porting.lib.spi.TestArchiveProcessor;
import tck.arquillian.protocol.common.TargetVehicle;



@ExtendWith(ArquillianExtension.class)
@Tag("persistence")
@Tag("platform")
@Tag("tck-appclient")

@TestMethodOrder(MethodOrderer.MethodName.class)
public class Client3Stateless3Test extends ee.jakarta.tck.persistence.core.annotations.orderby.Client3 {
    static final String VEHICLE_ARCHIVE = "jpa_core_annotations_orderby_stateless3_vehicle";

        /**
        EE10 Deployment Descriptors:
        jpa_core_annotations_orderby: META-INF/persistence.xml
        jpa_core_annotations_orderby_appmanaged_vehicle_client: META-INF/application-client.xml
        jpa_core_annotations_orderby_appmanaged_vehicle_ejb: jar.sun-ejb-jar.xml
        jpa_core_annotations_orderby_appmanagedNoTx_vehicle_client: META-INF/application-client.xml
        jpa_core_annotations_orderby_appmanagedNoTx_vehicle_ejb: jar.sun-ejb-jar.xml
        jpa_core_annotations_orderby_pmservlet_vehicle_web: WEB-INF/web.xml
        jpa_core_annotations_orderby_puservlet_vehicle_web: WEB-INF/web.xml
        jpa_core_annotations_orderby_stateful3_vehicle_client: META-INF/application-client.xml
        jpa_core_annotations_orderby_stateful3_vehicle_ejb: jar.sun-ejb-jar.xml
        jpa_core_annotations_orderby_stateless3_vehicle_client: META-INF/application-client.xml
        jpa_core_annotations_orderby_stateless3_vehicle_ejb: jar.sun-ejb-jar.xml
        jpa_core_annotations_orderby_vehicles: 

        Found Descriptors:
        Client:

        /com/sun/ts/tests/common/vehicle/stateless3/stateless3_vehicle_client.xml
        Ejb:

        Ear:

        */
        @TargetsContainer("tck-appclient")
        @OverProtocol("appclient")
        @Deployment(name = VEHICLE_ARCHIVE, order = 2)
        public static EnterpriseArchive createDeploymentVehicle(@ArquillianResource TestArchiveProcessor archiveProcessor) {
        // Client
            // the jar with the correct archive name
            JavaArchive jpa_core_annotations_orderby_stateless3_vehicle_client = ShrinkWrap.create(JavaArchive.class, "jpa_core_annotations_orderby_vehicles_client.jar");
            // The class files
            jpa_core_annotations_orderby_stateless3_vehicle_client.addClasses(
            com.sun.ts.tests.common.vehicle.VehicleRunnerFactory.class,
            com.sun.ts.tests.common.vehicle.ejb3share.UseEntityManager.class,
            com.sun.ts.tests.common.vehicle.ejb3share.EJB3ShareIF.class,
            com.sun.ts.lib.harness.EETest.Fault.class,
            com.sun.ts.tests.common.vehicle.ejb3share.UseEntityManagerFactory.class,
            com.sun.ts.tests.common.vehicle.EmptyVehicleRunner.class,
            ee.jakarta.tck.persistence.common.PMClientBase.class,
            com.sun.ts.tests.common.vehicle.VehicleRunnable.class,
            com.sun.ts.tests.common.vehicle.ejb3share.UserTransactionWrapper.class,
            com.sun.ts.tests.common.vehicle.stateless3.Stateless3VehicleIF.class,
            com.sun.ts.lib.harness.EETest.class,
            com.sun.ts.tests.common.vehicle.stateless3.Stateless3VehicleRunner.class,
            com.sun.ts.lib.harness.ServiceEETest.class,
            com.sun.ts.tests.common.vehicle.ejb3share.EntityTransactionWrapper.class,
            com.sun.ts.lib.harness.EETest.SetupException.class,
            com.sun.ts.tests.common.vehicle.VehicleClient.class,
            com.sun.ts.tests.common.vehicle.ejb3share.NoopTransactionWrapper.class,
            Client3.class,
            Client3Stateless3Test.class
            );
            // The application-client.xml descriptor
            URL resURL = Client3.class.getResource("/com/sun/ts/tests/common/vehicle/stateless3/stateless3_vehicle_client.xml");
            if(resURL != null) {
              jpa_core_annotations_orderby_stateless3_vehicle_client.addAsManifestResource(resURL, "application-client.xml");
            }
            // The sun-application-client.xml file need to be added or should this be in in the vendor Arquillian extension?
            resURL = Client3.class.getResource("//com/sun/ts/tests/common/vehicle/stateless3/stateless3_vehicle_client.jar.sun-application-client.xml");
            if(resURL != null) {
              jpa_core_annotations_orderby_stateless3_vehicle_client.addAsManifestResource(resURL, "sun-application-client.xml");
            }
            jpa_core_annotations_orderby_stateless3_vehicle_client.addAsManifestResource(new StringAsset("Main-Class: " + Client3.class.getName() + "\n"), "MANIFEST.MF");
            // Call the archive processor
            archiveProcessor.processClientArchive(jpa_core_annotations_orderby_stateless3_vehicle_client, Client3.class, resURL);

        // Ejb 1
            // the jar with the correct archive name
            JavaArchive jpa_core_annotations_orderby_stateless3_vehicle_ejb = ShrinkWrap.create(JavaArchive.class, "jpa_core_annotations_orderby_stateless3_vehicle_ejb.jar");
            // The class files
            jpa_core_annotations_orderby_stateless3_vehicle_ejb.addClasses(
                com.sun.ts.tests.common.vehicle.ejb3share.EJB3ShareBaseBean.class,
                com.sun.ts.tests.common.vehicle.VehicleRunnerFactory.class,
                com.sun.ts.tests.common.vehicle.ejb3share.UseEntityManager.class,
                com.sun.ts.tests.common.vehicle.ejb3share.EJB3ShareIF.class,
                com.sun.ts.lib.harness.EETest.Fault.class,
                com.sun.ts.tests.common.vehicle.ejb3share.UseEntityManagerFactory.class,
                ee.jakarta.tck.persistence.common.PMClientBase.class,
                ee.jakarta.tck.persistence.core.annotations.orderby.Client3.class,
                com.sun.ts.tests.common.vehicle.VehicleRunnable.class,
                com.sun.ts.tests.common.vehicle.ejb3share.UserTransactionWrapper.class,
                com.sun.ts.tests.common.vehicle.stateless3.Stateless3VehicleBean.class,
                com.sun.ts.tests.common.vehicle.stateless3.Stateless3VehicleIF.class,
                com.sun.ts.lib.harness.EETest.class,
                com.sun.ts.lib.harness.ServiceEETest.class,
                com.sun.ts.tests.common.vehicle.ejb3share.EntityTransactionWrapper.class,
                com.sun.ts.lib.harness.EETest.SetupException.class,
                com.sun.ts.tests.common.vehicle.VehicleClient.class,
                com.sun.ts.tests.common.vehicle.ejb3share.NoopTransactionWrapper.class
            );
            // The ejb-jar.xml descriptor
            URL ejbResURL1 = Client3.class.getResource("//vehicle/stateless3/stateless3_vehicle_ejb.xml");
            if(ejbResURL1 != null) {
              jpa_core_annotations_orderby_stateless3_vehicle_ejb.addAsManifestResource(ejbResURL1, "ejb-jar.xml");
            }
            // The sun-ejb-jar.xml file
            ejbResURL1 = Client3.class.getResource("//vehicle/stateless3/stateless3_vehicle_ejb.jar.sun-ejb-jar.xml");
            if(ejbResURL1 != null) {
              jpa_core_annotations_orderby_stateless3_vehicle_ejb.addAsManifestResource(ejbResURL1, "sun-ejb-jar.xml");
            }
            // Call the archive processor
            archiveProcessor.processEjbArchive(jpa_core_annotations_orderby_stateless3_vehicle_ejb, Client3.class, ejbResURL1);


        // Par
            // the jar with the correct archive name
            JavaArchive jpa_core_annotations_orderby = ShrinkWrap.create(JavaArchive.class, "jpa_core_annotations_orderby.jar");
            // The class files
            jpa_core_annotations_orderby.addClasses(
                ee.jakarta.tck.persistence.core.annotations.orderby.A.class,
                ee.jakarta.tck.persistence.core.annotations.orderby.Department.class,
                ee.jakarta.tck.persistence.core.annotations.orderby.ZipCode2.class,
                ee.jakarta.tck.persistence.core.annotations.orderby.Customer.class,
                ee.jakarta.tck.persistence.core.annotations.orderby.A2.class,
                ee.jakarta.tck.persistence.core.annotations.orderby.Address.class,
                ee.jakarta.tck.persistence.core.annotations.orderby.Employee.class,
                ee.jakarta.tck.persistence.core.annotations.orderby.Address2.class,
                ee.jakarta.tck.persistence.core.annotations.orderby.Customer2.class,
                ee.jakarta.tck.persistence.core.annotations.orderby.Insurance.class,
                ee.jakarta.tck.persistence.core.annotations.orderby.ZipCode.class
            );
            // The persistence.xml descriptor
            URL parURL = Client3.class.getResource("persistence.xml");
            if(parURL != null) {
              jpa_core_annotations_orderby.addAsManifestResource(parURL, "persistence.xml");
            }
            // Add the Persistence mapping-file
            URL mappingURL = Client3.class.getResource("myMappingFile.xml");
            if(mappingURL != null) {
              jpa_core_annotations_orderby.addAsResource(mappingURL, "myMappingFile.xml");
            }
            mappingURL = Client3.class.getResource("myMappingFile1.xml");
            if(mappingURL != null) {
              jpa_core_annotations_orderby.addAsResource(mappingURL, "myMappingFile1.xml");
            }
            mappingURL = Client3.class.getResource("myMappingFile2.xml");
            if(mappingURL != null) {
              jpa_core_annotations_orderby.addAsResource(mappingURL, "myMappingFile2.xml");
            }
            // Call the archive processor
            archiveProcessor.processParArchive(jpa_core_annotations_orderby, Client3.class, parURL);
            parURL = Client3.class.getResource("orm.xml");
            if(parURL != null) {
              jpa_core_annotations_orderby.addAsManifestResource(parURL, "orm.xml");
            }

        // Ear
            EnterpriseArchive jpa_core_annotations_orderby_vehicles_ear = ShrinkWrap.create(EnterpriseArchive.class, "jpa_core_annotations_orderby_vehicles.ear");

            // Any libraries added to the ear

            // The component jars built by the package target
            jpa_core_annotations_orderby_vehicles_ear.addAsModule(jpa_core_annotations_orderby_stateless3_vehicle_ejb);
            jpa_core_annotations_orderby_vehicles_ear.addAsModule(jpa_core_annotations_orderby_stateless3_vehicle_client);

            jpa_core_annotations_orderby_vehicles_ear.addAsLibrary(jpa_core_annotations_orderby);



            // The application.xml descriptor
            URL earResURL = null;
            // The sun-application.xml descriptor
            earResURL = Client3.class.getResource("/.ear.sun-application.xml");
            if(earResURL != null) {
              jpa_core_annotations_orderby_vehicles_ear.addAsManifestResource(earResURL, "sun-application.xml");
            }
            // Call the archive processor
            archiveProcessor.processEarArchive(jpa_core_annotations_orderby_vehicles_ear, Client3.class, earResURL);
        return jpa_core_annotations_orderby_vehicles_ear;
        }

        @Test
        @Override
        @TargetVehicle("stateless3")
        public void propertyElementCollectionBasicType() throws java.lang.Exception {
            super.propertyElementCollectionBasicType();
        }

        @Test
        @Override
        @TargetVehicle("stateless3")
        public void fieldElementCollectionBasicType() throws java.lang.Exception {
            super.fieldElementCollectionBasicType();
        }

}
