package Main;

import Tools.Math_Tools;
import Tools.Matrix;
import Tools.Vector;

public class Main {
    public static void main(String[] args) {
        Matrix m = new Matrix(5,5,2.5645f), n = new Matrix();
        System.out.println(m.size());
        System.out.println(n);
        Math_Tools.copyMatrix(m,n);
        n.Show();
        Vector v = new Vector(4,3.4f);
        v.Show();
    }
}
