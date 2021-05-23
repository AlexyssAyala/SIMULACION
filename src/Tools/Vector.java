package Tools;

import java.util.ArrayList;

//Tanto esta clase como la de Matrix, existen como alternativa a los typedef para evitar escrituras como
//ArrayList<ArrayList<float>>, volviendo el codigo mas legible
//Como aclaracion, se opto por arrayList en lugar de Vector ya que Vector es mas rapido y gasta menos memoria

public class Vector extends ArrayList<Float> {
    public Vector(){};

    //Constructor que servira para inicializar en 0 un vector recien instanciado
    public Vector(int size, float defaultElement){
        for (int i = 0; i < size; i++) {
            this.add(defaultElement);
        }
    }

    //Funcion que imprime el vector
    public void Show(){
        System.out.println(this);
    }
}
