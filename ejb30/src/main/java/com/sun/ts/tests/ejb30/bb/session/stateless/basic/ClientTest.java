package com.sun.ts.tests.ejb30.bb.session.stateless.basic;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.File;
import java.net.URL;

@ExtendWith(ArquillianExtension.class)
public class ClientTest extends Client {
    /**
     * The
     * @return
     */
    @Deployment(testable = true, name = "ejb3_bb_stateless_basic.ear")
    public static EnterpriseArchive deployment() {
        String resourcePrefix = ClientTest.class.getPackageName().replace('.', '/');

        JavaArchive ejb3_bb_stateless_basic_ejb = ShrinkWrap.create(JavaArchive.class, "ejb3_bb_stateless_basic_ejb.jar")
                .addClasses(com.sun.ts.tests.ejb30.bb.session.stateless.basic.RemoteCalculatorBean.class,
                        com.sun.ts.tests.ejb30.bb.session.stateless.basic.RemoteCalculatorBean2.class,
                        com.sun.ts.tests.ejb30.bb.session.stateless.basic.RemoteCalculatorBean3Super.class,
                        com.sun.ts.tests.ejb30.bb.session.stateless.basic.RemoteCalculatorBean3.class,
                        com.sun.ts.tests.ejb30.bb.session.stateless.basic.RemoteCalculatorBean4Super.class,
                        com.sun.ts.tests.ejb30.bb.session.stateless.basic.RemoteCalculatorBean4.class,
                        com.sun.ts.tests.ejb30.bb.session.stateless.basic.RemoteCalculatorBean5.class,
                        com.sun.ts.tests.ejb30.common.calc.BaseRemoteCalculator.class,
                        com.sun.ts.tests.ejb30.common.calc.CalculatorException.class,
                        com.sun.ts.tests.ejb30.common.calc.NoInterfaceRemoteCalculator.class,
                        com.sun.ts.tests.ejb30.common.helper.TLogger.class,
                        com.sun.ts.tests.ejb30.common.calc.RemoteCalculator.class
                )
                .addAsManifestResource(resourcePrefix+"/ejb3_bb_stateless_basic_ejb.xml", "ejb-jar.xml")
                .addAsManifestResource(resourcePrefix+"/ejb3_bb_stateless_basic_ejb.jar.sun-ejb-jar.xml", "sun-ejb-jar.xml")
                ;
        JavaArchive appclient = ShrinkWrap.create(JavaArchive.class, "appclient.jar")
                .addClasses(com.sun.ts.lib.harness.EETest.Fault.class,
                        com.sun.ts.lib.harness.EETest.SetupException.class,
                        com.sun.ts.lib.harness.EETest.class,
                        com.sun.ts.tests.ejb30.bb.session.stateless.basic.Client.class,
                        com.sun.ts.tests.ejb30.common.calc.CalculatorException.class,
                        com.sun.ts.tests.ejb30.common.calc.RemoteCalculator.class,
                        com.sun.ts.tests.ejb30.common.helper.TLogger.class,
                        com.sun.ts.tests.ejb30.common.helper.ServiceLocator.class)
                .addAsManifestResource(resourcePrefix+"/ejb3_bb_stateless_basic_client.xml", "application-client.xml")
                .addAsManifestResource(new StringAsset("Main-Class: " + com.sun.ts.tests.ejb30.bb.session.stateless.basic.Client.class.getName() + "\n"),
                "MANIFEST.MF");
                ;

        EnterpriseArchive ear = ShrinkWrap.create(EnterpriseArchive.class, "appclient_ear.ear")
                .addAsModule(appclient)
                .addAsModule(ejb3_bb_stateless_basic_ejb)
                ;

        File archiveOnDisk = new File("target" + File.separator + "appclient_ear.ear");
        if (archiveOnDisk.exists()) {
            archiveOnDisk.delete();
        }
        final ZipExporter exporter = ear.as(ZipExporter.class);
        exporter.exportTo(archiveOnDisk);
        String archivePath = archiveOnDisk.getAbsolutePath();
        System.out.printf("archivePath: %s\n", archivePath);

        return ear;
    }
}
