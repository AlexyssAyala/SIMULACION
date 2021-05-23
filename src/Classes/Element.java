package Classes;

public class Element extends Item{
    public Element() {
    }

    @Override
    public void setValues(int a, float b, float c, int d, int e, int f, float g) {
        id = a;
        node1 = d;
        node2 = e;
        node3 = f;
    }
}
