package com.sun.ts.tests.ejb30.bb.session.stateless.basic;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.extension.ExtendWith;

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
        ClassLoader loader = ClientTest.class.getClassLoader();
        System.out.printf("CS: %s\n", ClientTest.class.getProtectionDomain().getCodeSource());
        System.out.printf("#1: %s\n", ClientTest.class.getResource("ejb3_bb_stateless_basic_ejb.xml"));
        System.out.printf("#1.1: %s\n", ClientTest.class.getResource(resourcePrefix+"/ejb3_bb_stateless_basic_ejb.xml"));
        System.out.printf("#2: %s\n", loader.getResource("ejb3_bb_stateless_basic_ejb.xml"));
        System.out.printf("#2.1: %s\n", loader.getResource(resourcePrefix+"/ejb3_bb_stateless_basic_ejb.xml"));
        JavaArchive ejb3_bb_stateless_basic_ejb = ShrinkWrap.create(JavaArchive.class, "ejb3_bb_stateless_basic_ejb")
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
        JavaArchive ejb3_bb_stateless_basic_client = ShrinkWrap.create(JavaArchive.class, "ejb3_bb_stateless_basic_client")
                .addClasses(com.sun.ts.lib.harness.EETest.Fault.class,
                        com.sun.ts.lib.harness.EETest.SetupException.class,
                        com.sun.ts.lib.harness.EETest.class,
                        com.sun.ts.tests.ejb30.bb.session.stateless.basic.Client.class,
                        com.sun.ts.tests.ejb30.common.calc.CalculatorException.class,
                        com.sun.ts.tests.ejb30.common.calc.RemoteCalculator.class,
                        com.sun.ts.tests.ejb30.common.helper.TLogger.class,
                        com.sun.ts.tests.ejb30.common.helper.ServiceLocator.class)
                .addAsManifestResource(resourcePrefix+"/ejb3_bb_stateless_basic_client.xml", "application-client.xml")
                ;

        EnterpriseArchive ejb3_bb_stateless_basic = ShrinkWrap.create(EnterpriseArchive.class, "ejb3_bb_stateless_basic")
                .addAsModule(ejb3_bb_stateless_basic_client)
                .addAsModule(ejb3_bb_stateless_basic_ejb)
                ;
        return ejb3_bb_stateless_basic;
    }
}
