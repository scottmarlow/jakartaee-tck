package com.sun.ts.tests.ejb30.bb.session.stateless.annotation.enventry;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.File;

@ExtendWith(ArquillianExtension.class)
public class ClientTest extends Client {

    private static final Class[] ear_lib_classes = {
    };
    private static final Class[] ejb_jar_classes = {
            com.sun.ts.tests.ejb30.bb.session.stateless.annotation.enventry.EnvEntryFieldBean.class,
            com.sun.ts.tests.ejb30.bb.session.stateless.annotation.enventry.EnvEntrySetterBean.class,
            com.sun.ts.tests.ejb30.bb.session.stateless.annotation.enventry.EnvEntryTypeBean.class,
            com.sun.ts.tests.ejb30.common.annotation.enventry.EnvEntryBeanBase.class,
            com.sun.ts.tests.ejb30.common.annotation.enventry.EnvEntryIF.class,
            com.sun.ts.tests.ejb30.common.annotation.enventry.Constants.class,
            com.sun.ts.tests.ejb30.common.helper.ServiceLocator.class,
            com.sun.ts.tests.ejb30.common.helper.TLogger.class,
            com.sun.ts.tests.ejb30.common.helper.TestFailedException.class
    };
    private static final Class[] app_client_classes = {
            com.sun.ts.lib.harness.EETest.Fault.class,
            com.sun.ts.lib.harness.EETest.SetupException.class,
            com.sun.ts.lib.harness.EETest.class,
            com.sun.ts.tests.ejb30.bb.session.stateless.annotation.enventry.Client.class,
            com.sun.ts.tests.ejb30.common.annotation.enventry.ClientBase.class,
            com.sun.ts.tests.ejb30.common.annotation.enventry.EnvEntryIF.class,
            com.sun.ts.tests.ejb30.common.helper.TLogger.class,
            com.sun.ts.tests.ejb30.common.helper.TestFailedException.class
    };
    /**
     * Create the test deployment
     * @return test ear
     */
    @Deployment(testable = true, name = "ejb3_bb_stateless_enventry.ear")
    public static EnterpriseArchive deployment() {
        String resourcePrefix = ClientTest.class.getPackageName().replace('.', '/');

        JavaArchive ejbJar = ShrinkWrap.create(JavaArchive.class, "ejb3_bb_stateless_enventry.jar")
                .addClasses(ejb_jar_classes)
                .addAsManifestResource("com/sun/ts/tests/ejb30/common/annotation/enventry/env-entries.properties",
                "env-entries.properties")
                .addAsManifestResource(resourcePrefix+"/ejb3_bb_stateless_enventry_ejb.jar.sun-ejb-jar.xml", "sun-ejb-jar.xml")
                .addAsManifestResource(resourcePrefix+"/ejb3_bb_stateless_enventry_ejb.xml", "ejb-jar.xml")
                ;
        JavaArchive appclientJar = ShrinkWrap.create(JavaArchive.class, "appclient.jar")
                .addClasses(app_client_classes)
                .addAsManifestResource(new StringAsset("Main-Class: " + Client.class.getName() + "\n"),
                        "MANIFEST.MF");
                ;

        EnterpriseArchive ear = ShrinkWrap.create(EnterpriseArchive.class, "appclient_ear.ear")
                .addAsModule(appclientJar)
                .addAsModule(ejbJar)
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
