package wonder;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class BeanOne {

    @EJB
    Store store;

    public void addNumber(int n) {
        store.addToBox(n);
    }

}
