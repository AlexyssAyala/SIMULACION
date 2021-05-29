package Classes;

//Clase abstracta la cual establece la mayoria de parametros utilizados por el sistema
public abstract class Item {

    protected int id;
    protected float x;
    protected float y;
    protected int node1;
    protected int node2;
    protected int node3;
    protected float value;

    //Setters y Getters
    public void setId(int identifier) {

        id = identifier;
    }

    public void setX(float x) {

        this.x = x;
    }

    public void setY(float y) {

        this.y = y;
    }

    public void setNode1(int node1) {

        this.node1 = node1;
    }

    public void setNode2(int node2) {

        this.node2 = node2;
    }

    public void setNode3(int node3) {

        this.node3 = node3;
    }

    public void setValue(float value) {

        this.value = value;
    }

    public int getId() {
        return id;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getNode1() {
        return node1;
    }

    public int getNode2() {
        return node2;
    }

    public int getNode3() {
        return node3;
    }

    public float getValue() {
        return value;
    }

    //Metodo abstracto el cual permite asignar los valores dependiendo de la clase donde sera llamada.
    public abstract void setValues(int a, float b, float c, int d, int e, int f, float g);
}
