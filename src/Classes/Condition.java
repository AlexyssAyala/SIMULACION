package Classes;

public class Condition extends Item{
    public Condition() {
    }

    //Metodo que nos ayudara a crear las listas de condiciones
    public static Condition[] createConditions(int n){
        Condition[] list = new Condition[n];
        for (int i = 0; i < n; i++) {
            list[i] = new Condition();
        }
        return list;
    }

    @Override
    public void setValues(int a, float b, float c, int d, int e, int f, float g) {
        node1 = d;
        value = g;
    }
}
