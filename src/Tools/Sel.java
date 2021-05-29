package Tools;

import Classes.*;
import Enums.Parameters;
import Enums.Sizes;

import java.util.ArrayList;
import static Tools.Math_Tools.*;

public class Sel {
    private Sel(){};

    //Muestra las Ks
    public static void showKs(ArrayList<Matrix> Ks){
        for (int i = 0; i < Ks.size() ; i++) {
            System.out.println("K del elemento"+(i+1));
            Ks.get(i).Show();
            System.out.println("**********************************");
        }
    }

    //Muestra ls bs
    public static void showbs(ArrayList<Vector> bs){
        for (int i = 0; i < bs.size() ; i++) {
            System.out.println("b del elemento"+(i+1));
            bs.get(i).Show();
            System.out.println("**********************************");
        }
    }

    //Crea el elemento local
    public static float calculateLocalD(int i, Mesh m){
        float D,a,b,c,d;

        Element e = m.getElement(i);

        Node n1 = m.getNode(e.getNode1()-1);
        Node n2 = m.getNode(e.getNode2()-1);
        Node n3 = m.getNode(e.getNode3()-1);

        a=n2.getX()-n1.getX();
        b=n2.getY()-n1.getY();
        c=n3.getX()-n1.getX();
        d=n3.getY()-n1.getY();

        D = a*d - b*c;

        return D;
    }

    //Calcula la magnitud de un vector.
    public static float calculateMagnitude(float v1, float v2){
        return (float) Math.sqrt(Math.pow(v1,2)+Math.pow(v2,2));
    }

    public static float calculateLocalArea(int i, Mesh m){
        //Formula de Herón
        float A,s,a,b,c;
        Element e = m.getElement(i);
        Node n1 = m.getNode(e.getNode1()-1);
        Node n2 = m.getNode(e.getNode2()-1);
        Node n3 = m.getNode(e.getNode3()-1);

        a = calculateMagnitude(n2.getX()-n1.getX(),n2.getY()-n1.getY());
        b = calculateMagnitude(n3.getX()-n2.getX(),n3.getY()-n2.getY());
        c = calculateMagnitude(n3.getX()-n1.getX(),n3.getY()-n1.getY());
        s = (a+b+c)/2.0f;

        A = (float) Math.sqrt(s*(s-a)*(s-b)*(s-c));

        return A;
    }

    //Calcula la la matriz local A
    public static void calculateLocalA(int i,Matrix A, Mesh m){
        Element e = m.getElement(i);
        Node n1 = m.getNode(e.getNode1()-1);
        Node n2 = m.getNode(e.getNode2()-1);
        Node n3 = m.getNode(e.getNode3()-1);
        A.get(0).set(0,n3.getY()-n1.getY());
        A.get(0).set(1, n1.getY()-n2.getY());
        A.get(1).set(0,n1.getX()-n3.getX());
        A.get(1).set(1, n2.getX()-n1.getX());
    }

    //Calcula y llena la matriz B
    private static void calculateB(Matrix B){
        B.get(0).set(0, -1f);
        B.get(0).set(1, 1f);
        B.get(0).set(2, 0f);
        B.get(1).set(0, -1f);
        B.get(1).set(1, 0f);
        B.get(1).set(2, 1f);
    }

    //Metodo que cera una matriz local K y lo alamcena en m
    private static Matrix createLocalK(int element,Mesh m){
        // K = (k*Ae/D^2)Bt*At*A*B := K_3x3
        float D,Ae,k = m.getParameter(Parameters.THERMAL_CONDUCTIVITY.ordinal());
        Matrix K=new Matrix(),A=new Matrix(),B=new Matrix(),Bt=new Matrix(),At=new Matrix();

        D = calculateLocalD(element,m);
        Ae = calculateLocalArea(element,m);

        zeroes(A,2);
        zeroes(B,2,3);
        calculateLocalA(element,A,m);
        calculateB(B);
        transpose(A,At);
        transpose(B,Bt);

        productRealMatrix(k*Ae/(D*D),productMatrixMatrix(Bt,productMatrixMatrix(At,productMatrixMatrix(A,B,2,2,3),2,2,3),3,2,3),K);

        return K;
    }

    public static float calculateLocalJ(int i, Mesh m){
        float J,a,b,c,d;
        Element e = m.getElement(i);
        Node n1 = m.getNode(e.getNode1()-1);
        Node n2 = m.getNode(e.getNode2()-1);
        Node n3 = m.getNode(e.getNode3()-1);

        a=n2.getX()-n1.getX();
        b=n3.getX()-n1.getX();
        c=n2.getY()-n1.getY();
        d=n3.getY()-n1.getY();

        J = a * d - b * c;

        return J;
    }

    //Metodo que crea el elemento local b y lo almacena en m
    public static Vector createLocalb(int element,Mesh m){
        Vector b = new Vector();

        float Q = m.getParameter(Parameters.HEAT_SOURCE.ordinal()), J, b_i;
        J = calculateLocalJ(element,m);

        b_i = Q * J / 6.0f;
        b.add(b_i);
        b.add(b_i);
        b.add(b_i);

        return b;
    }

    //Esta funcion crea los sitemas locales (K y b) y almacena los datos en sus respectivas listas
    public static void crearSistemasLocales(Mesh m, ArrayList<Matrix> localKs, ArrayList<Vector> localbs){
        for(int i = 0; i<m.getSize(Sizes.ELEMENTS.ordinal()); i++){
            localKs.add(createLocalK(i,m));
            localbs.add(createLocalb(i,m));
        }
    }

    //Esta funcion realiza el ensamblaje de la matriz K global, recibe el elemento actual, la matriz K local
    //y la matriz K global en la cual se realizara el ensamblaje
    public static void assemblyK(Element e, Matrix localK, Matrix K){
        int index1 = e.getNode1() - 1;
        int index2 = e.getNode2() - 1;
        int index3 = e.getNode3() - 1;

        K.get(index1).set(index1, K.get(index1).get(index1) + localK.get(0).get(0));
        K.get(index1).set(index2, K.get(index1).get(index2) + localK.get(0).get(1));
        K.get(index1).set(index3, K.get(index1).get(index3) + localK.get(0).get(2));
        K.get(index2).set(index1, K.get(index2).get(index1) + localK.get(1).get(0));
        K.get(index2).set(index2, K.get(index2).get(index2) + localK.get(1).get(1));
        K.get(index2).set(index3, K.get(index2).get(index3) + localK.get(1).get(2));
        K.get(index3).set(index1, K.get(index3).get(index1) + localK.get(2).get(0));
        K.get(index3).set(index2, K.get(index3).get(index2) + localK.get(2).get(1));
        K.get(index3).set(index3, K.get(index3).get(index3) + localK.get(2).get(2));
    }

    //Esta funcion realiza el ensamblaje del arreglo b global, recibe el elemento actual, el arreglo b local
    //y el arreglo b glocal en el cual se realizara el ensablaje
    public static void assemblyb(Element e, Vector localb, Vector b){
        int index1 = e.getNode1() - 1;
        int index2 = e.getNode2() - 1;
        int index3 = e.getNode3() - 1;

        b.set(index1, b.get(index1) + localb.get(0));
        b.set(index2, b.get(index2) + localb.get(1));
        b.set(index3, b.get(index3) + localb.get(2));
    }

    //Se realiza el ensamblaje de los sistemas locales K y B utilizando las funciones assemblyK y assemblyb
    public static void ensamblaje(Mesh m, ArrayList<Matrix> localKs, ArrayList<Vector> localbs, Matrix K,Vector b){
        for(int i=0; i<m.getSize(Sizes.ELEMENTS.ordinal()); i++){
            Element e = m.getElement(i);
            assemblyK(e,localKs.get(i),K);
            assemblyb(e,localbs.get(i),b);
        }
    }

    //Funcion que aplica la condicion de neumann al vector b
    public static void applyNeumann(Mesh m,Vector b){
        for(int i=0;i <m.getSize(Sizes.NEUMANN.ordinal()); i++){
            Condition c = m.getCondition(i,Sizes.NEUMANN);
            b.set(c.getNode1()-1, b.get(c.getNode1()-1) + c.getValue());
        }
    }

    //Funcion que aplica la condicion de dirichlet al sistema de ecuaciones
    public static void applyDirichlet(Mesh m,Matrix K,Vector b){
        for(int i=0; i<m.getSize(Sizes.DIRICHLET.ordinal()); i++){

            Condition c = m.getCondition(i,Sizes.DIRICHLET);
            int index = c.getNode1()-1;

            K.remove(index);
            b.remove(index);

            for(int row=0; row < K.size(); row++){
                float cell = K.get(row).get(index);
                K.get(row).remove(index);
                b.set(row, b.get(row) + (-1*c.getValue()) * cell);
            }
        }
    }

    //Funcion que calcula el resultado del SEL
    public static void calculate(Matrix K, Vector b, Vector T){
        System.out.println("Iniciando calculo de respuesta...");
        Matrix Kinv = new Matrix();
        System.out.println("Calculo de inversa...");
        inverseMatrix(K, Kinv);
        System.out.println("Calculo de respuesta...");
        productMatrixVector(Kinv, b, T);
    }

}
