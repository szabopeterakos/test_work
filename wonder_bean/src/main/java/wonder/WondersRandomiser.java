package wonder;

import java.util.List;
import java.util.Random;

public class WondersRandomiser {

    public void WonderListFillWithRandomLong(List<Wonder> wonderList) {
        for (Wonder c : wonderList) {
            c.setValue(new Random().nextInt());
        }
    }

}
