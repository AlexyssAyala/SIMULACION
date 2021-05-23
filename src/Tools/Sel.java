package Tools;

import Classes.*;

import java.util.ArrayList;

public class Sel {
    private Sel(){};

    public static void showKs(ArrayList<Matrix> Ks){
        for (int i = 0; i < Ks.size() ; i++) {
            System.out.println("K del elemento"+(i+1));
            Ks.get(i).Show();
            System.out.println("**********************************");
        }
    }

    public static void showbs(ArrayList<Vector> bs){
        for (int i = 0; i < bs.size() ; i++) {
            System.out.println("b del elemento"+(i+1));
            bs.get(i).Show();
            System.out.println("**********************************");
        }
    }

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
        s = (a+b+c)/2;

        A = (float) Math.sqrt(s*(s-a)*(s-b)*(s-c));

        return A;
    }

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

    private static void calculateB(Matrix B){
        B.get(0).set(0, -1f);
        B.get(0).set(1, 1f);
        B.get(0).set(2, 0f);
        B.get(1).set(0, -1f);
        B.get(1).set(1, 0f);
        B.get(1).set(2, 1f);
    }

}
