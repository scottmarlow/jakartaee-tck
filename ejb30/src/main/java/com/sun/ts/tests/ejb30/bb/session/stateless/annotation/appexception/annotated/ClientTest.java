package com.sun.ts.tests.ejb30.bb.session.stateless.annotation.appexception.annotated;

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
    /**
     * The
     * @return
     */
    @Deployment(testable = true, name = "ejb3_stateless_appexception_annotated.ear")
    public static EnterpriseArchive deployment() {
        String resourcePrefix = com.sun.ts.tests.ejb30.bb.session.stateless.annotation.appexception.annotated.ClientTest.class.getPackageName().replace('.', '/');
        JavaArchive earLib = ShrinkWrap.create(JavaArchive.class, "earlib.jar")
                .addClasses(
                        com.sun.ts.tests.ejb30.common.appexception.UncheckedAppException.class,
                        com.sun.ts.tests.ejb30.common.appexception.UncheckedRollbackAppException.class,
                        com.sun.ts.tests.ejb30.common.appexception.AtCheckedAppException.class,
                        com.sun.ts.tests.ejb30.common.appexception.AtCheckedRollbackAppException.class,
                        com.sun.ts.tests.ejb30.common.appexception.AtUncheckedAppException.class,
                        com.sun.ts.tests.ejb30.common.appexception.AtUncheckedRollbackAppException.class,
                        com.sun.ts.tests.ejb30.common.appexception.CheckedAppException.class,
                        com.sun.ts.tests.ejb30.common.appexception.CheckedRollbackAppException.class,
                        com.sun.ts.tests.ejb30.common.helper.TestFailedException.class,
                        com.sun.ts.tests.ejb30.common.helper.TLogger.class
                );
        JavaArchive ejb3_stateless_appexception_annotated = ShrinkWrap.create(JavaArchive.class, "ejb3_stateless_appexception_annotated.jar")
                .addClasses(
                        com.sun.ts.tests.ejb30.bb.session.stateless.annotation.appexception.annotated.AppExceptionBean.class,
                        com.sun.ts.tests.ejb30.bb.session.stateless.annotation.appexception.annotated.RollbackBean.class,
                        com.sun.ts.tests.ejb30.common.appexception.AppExceptionBeanBase.class,
                        com.sun.ts.tests.ejb30.common.appexception.RollbackBeanBase.class,
                        com.sun.ts.tests.ejb30.common.appexception.AppExceptionLocalIF.class,
                        com.sun.ts.tests.ejb30.common.appexception.AppExceptionIF.class,
                        com.sun.ts.tests.ejb30.common.appexception.CommonIF.class,
                        com.sun.ts.tests.ejb30.common.appexception.RollbackIF.class
                )
                .addAsManifestResource(resourcePrefix+"/ejb3_stateless_appexception_annotated_ejb.jar.sun-ejb-jar.xml", "sun-ejb-jar.xml")
                ;
        JavaArchive appclientJar = ShrinkWrap.create(JavaArchive.class, "appclient.jar")
                .addClasses(
                        com.sun.ts.lib.harness.EETest.Fault.class,
                        com.sun.ts.lib.harness.EETest.SetupException.class,
                        com.sun.ts.lib.harness.EETest.class,
                        com.sun.ts.tests.ejb30.bb.session.stateless.annotation.appexception.annotated.Client.class,
                        com.sun.ts.tests.ejb30.common.appexception.ClientBase.class,
                        com.sun.ts.tests.ejb30.common.appexception.AppExceptionIF.class,
                        com.sun.ts.tests.ejb30.common.appexception.CommonIF.class,
                        com.sun.ts.tests.ejb30.common.appexception.RollbackIF.class
                )
                .addAsManifestResource(new StringAsset("Main-Class: " + Client.class.getName() + "\n"),
                        "MANIFEST.MF");
                ;

        EnterpriseArchive ear = ShrinkWrap.create(EnterpriseArchive.class, "appclient_ear.ear")
                .addAsModule(appclientJar)
                .addAsModule(ejb3_stateless_appexception_annotated)
                .addAsLibrary(earLib)
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
