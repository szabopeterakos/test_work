package mock;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.extension.byteman.api.BMRule;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.MavenResolverSystem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import javax.inject.Inject;

import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class MyBeanTest {

    @Inject
    MyBean myAlternativebean;

    @Inject
    MyBeanImpl2 mySpecializedBean;

    @Inject
    MyBeanImpl realBean;

    @Inject
    MyBeanImpl realMockedBean;

    @Before
    public void setupMock() {
        realMockedBean = Mockito.mock(MyBeanImpl.class);//to use @Mock see auto discover extension: https://github.com/arquillian/arquillian-showcase/blob/master/extensions/autodiscover
        Mockito.when(realMockedBean.someSlowOperation()).thenReturn(true);
        Mockito.when(realMockedBean.isAlive()).thenReturn(true);
    }

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackages(true, MyBeanImpl.class.getPackage())
                .deleteClass(MyBeanTest.class)
               // .deleteClass(MyBeanSpecialization.class) uncomment to see second test fail
                .addAsWebInfResource("test-beans.xml", "beans.xml");
        MavenResolverSystem resolver = Maven.resolver();
        war.addAsLibraries(resolver.loadPomFromFile("pom.xml").resolve("org.mockito:mockito-all:1.10.8").withTransitivity().asSingleFile());
        System.out.println(war.toString(true));
        return war;
    }

    @Test
    @InSequence(1)
    public void shouldRunFastWithAlternativeBean() {
        long start = System.currentTimeMillis();
        assertTrue(myAlternativebean.someSlowOperation());
        assertTrue(myAlternativebean.isAlive());
        assertTrue((System.currentTimeMillis() - start) < 10000);
    }

    @Test
    @InSequence(2)
    public void shouldRunFastWithSpecializedBean() {
        long start = System.currentTimeMillis();
        assertTrue(mySpecializedBean.someSlowOperation());
        assertTrue(mySpecializedBean.isAlive());
        assertTrue((System.currentTimeMillis() - start) < 10000);
    }

    @Test
    @InSequence(3)
    public void shouldRunFastWithMockedBean() {
        long start = System.currentTimeMillis();
        assertTrue(realMockedBean.someSlowOperation());
        assertTrue(realMockedBean.isAlive());
        assertTrue((System.currentTimeMillis() - start) < 10000);
    }

    @Test
    @InSequence(4)
    @BMRule(
            name = "fake method call", targetClass = "MyBeanImpl", targetMethod = "someSlowOperation",
            action = "return true;")
    public void shouldRunFastWithBytecodeManipulatedBean() {
        long start = System.currentTimeMillis();
        assertTrue(realMockedBean.someSlowOperation());
        assertTrue(realMockedBean.isAlive());
        assertTrue((System.currentTimeMillis() - start) < 10000);
    }

    @Test
    @InSequence(5)
    public void shouldRunSlowWithRealBean() {
        long start = System.currentTimeMillis();
        assertTrue(realBean.someSlowOperation());
        long executionTime = (System.currentTimeMillis() - start);
        assertTrue(executionTime >= 10000);
    }

}
