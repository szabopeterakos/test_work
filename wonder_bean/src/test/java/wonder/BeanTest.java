package wonder;

import java.util.ArrayList;
import javax.ejb.EJB;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class BeanTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClasses(Store.class, WonderBean.class,
                        WondersRandomiser.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @EJB
    private Store store;

    @Inject
    private WonderBean wonderBean;
    @Inject
    private WondersRandomiser wondersRandomiser;

    @Before
    public void init() {
        store.setBox(new ArrayList<>());
    }

    @Test
    public void TestConnectionDeeper() {
        wonderBean.addWonder(new Wonder("one"));
        wonderBean.addWonder(new Wonder("two"));
        
        Assert.assertEquals(store.getWonderList().size(), 2);

        wondersRandomiser.WonderListFillWithRandomLong(store.getWonderList());

        Assert.assertNotNull(store.getWonderList().get(0).getValue());

    }

}
