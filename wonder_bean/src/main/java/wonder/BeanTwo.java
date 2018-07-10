package wonder;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class BeanTwo {

    @EJB
    Store store;

    public void addShadow() {
        store.addToBox(23);
    }
}
