package wonder;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class WonderBean {

    @EJB
    Store store;

    public void addWonder(Wonder wonder) {
        store.addWonder(wonder);
    }

}
