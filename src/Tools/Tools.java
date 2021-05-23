package Tools;
import Classes.Condition;
import Classes.Item;
import Enums.Lines;
import Enums.Modes;

import java.io.BufferedReader;
import java.io.IOException;

public class Tools {
    public static void obtenerDatos(BufferedReader file, int nlines, int n, Modes mode, Item[] itemList) throws IOException {
        String line = file.readLine();
        String[] values = null;
        if(nlines == Lines.DOUBLELINE.ordinal()) line = file.readLine();

        for (int i = 0; i < n; i++) {
            switch (mode){
                case INT_FLOAT:
                    int e0; float r0;
                    line = file.readLine();
                    values = line.split("\\d\\s+");
                    e0 = Integer.parseInt(values[0].trim()); r0 = Float.parseFloat(values[0].trim());
                    itemList[i].setValues(0,0,0,e0,0,0,r0 );
                    break;
                case INT_FLOAT_FLOAT:
                    int e; float r,rr;
                    values = line.split("\\d\\s+");
                    e = Integer.parseInt(values[0].trim());
                    r = Float.parseFloat(values[1].trim());
                    rr = Float.parseFloat(values[2].trim());
                    itemList[i].setValues(e,r,rr,0,0,0,0);
                    break;
                case INT_INT_INT_INT:
                    int e1,e2,e3,e4;
                    e1 = Integer.parseInt(values[0].trim());
                    e2 = Integer.parseInt(values[1].trim());
                    e3 = Integer.parseInt(values[2].trim());
                    e4 = Integer.parseInt(values[3].trim());
                    itemList[i].setValues(e1,0,0,e2,e3,e4,0);
                    break;
            }
        }
    }

    private static void correctConditions(int n, Condition[] list, int[] indices){
        for(int i=0; i<n; i++)
            indices[i] = list[i].getNode1();
        for(int i=0; i<n-1; i++){
            int pivot = list[i].getNode1();
            for(int j=i; j<n; j++)
                if(list[j].getNode1()>pivot)
                    list[j].setNode1(list[j].getNode1()-1);
        }
    }

}
