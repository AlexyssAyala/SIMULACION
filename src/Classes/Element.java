package Classes;

public class Element extends Item{
    public Element() {
    }

    //Metodo que nos ayudara a crear las listas de elementos
    public static Element[] createElements(int n){
        Element[] list = new Element[n];
        for (int i = 0; i < n; i++) {
            list[i] = new Element();
        }
        return list;
    }

    @Override
    public void setValues(int a, float b, float c, int d, int e, int f, float g) {
        id = a;
        node1 = d;
        node2 = e;
        node3 = f;
    }
}
