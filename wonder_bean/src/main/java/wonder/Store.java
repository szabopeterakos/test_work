package wonder;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Singleton;

@Singleton
public class Store {

    private List<Integer> box = new ArrayList<>();
    private List<Wonder> wonderList = new ArrayList<>();

    public void addToBox(int number) {
        box.add(number);
    }

    public List<Integer> getBox() {
        return new ArrayList<>(box);
    }

    public void setBox(List<Integer> box) {
        this.box = box;
    }

    public List<Wonder> getWonderList() {
        return wonderList;
    }

    public void setWonderList(List<Wonder> wonderList) {
        this.wonderList = wonderList;
    }

    public void addWonder(Wonder wonder) {
        this.wonderList.add(wonder);
    }
}
