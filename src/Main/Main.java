package Main;

import Classes.Mesh;
import Tools.Matrix;
import Tools.Vector;
import java.util.ArrayList;
import static Enums.Sizes.*;
import static Tools.Math_Tools.*;
import static Tools.Sel.*;
import static Tools.Tools.*;

public class Main {
    public static void main(String[] args) {
        String filename = args[0];
        ArrayList<Matrix> localKs = new ArrayList<Matrix>();
        ArrayList<Vector> localbs = new ArrayList<Vector>();
        Matrix K = new Matrix();
        Vector b = new Vector();
        Vector T = new Vector();

        System.out.println("IMPLEMENTACION DEL METODO DE LOS ELEMENTOS FINITOS");
        System.out.println("\t- TRANSFERENCIA DE CALOR\n"+"\t- 2 DIMENSIONES");
        System.out.println("\t- FUNCIONES DE FORMA LINEALES\n"+"\t- PESOS DE GALERKIN");
        System.out.println("\t- MALLATRIANGULAR IRREGULAR");
        System.out.println("*************************************************************************");

        Mesh m = new Mesh();
        leerMallayCondiciones(m, filename);
        System.out.println("Datos obtenidos correctamente\n***********************");

        crearSistemasLocales(m, localKs, localbs);
        System.out.println("*******************************");

        zeroes(K, m.getSize(NODES.ordinal()));
        zeroes(b, m.getSize(NODES.ordinal()));
        ensamblaje(m, localKs, localbs, K, b);

        System.out.println("*******************************");

        applyNeumann(m, b);
        System.out.println("*******************************");

        applyDirichlet(m,K,b);
        System.out.println("*******************************");

        zeroes(T, b.size());
        calculate(K,b,T);

        writeResults(m,T,filename);
    }
}
