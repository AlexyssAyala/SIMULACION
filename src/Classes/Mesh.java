package Classes;

import java.util.ArrayList;
import Enums.*;
import static Enums.Sizes.*;

public class Mesh {
    float parameters[] = new float[6];
    int sizes[] = new int [4];
    int indices_dirich[];
    Node node_list[];
    Element element_list[];
    Condition dirichlet_list[];
    Condition neumann_list[];

    public Mesh(){
    };

    //Methods
    public void setParameters(float k, float Q){
        parameters[Parameters.THERMAL_CONDUCTIVITY.ordinal()] = k;
        parameters[Parameters.HEAT_SOURCE.ordinal()] = Q;
    }
    public void setSizes(int nnodes, int neltos, int ndirich, int nneu){
        sizes[NODES.ordinal()]=nnodes;
        sizes[ELEMENTS.ordinal()]=neltos;
        sizes[DIRICHLET.ordinal()]=ndirich;
        sizes[NEUMANN.ordinal()]=nneu;
    }
    public int getSize(int s){
        return sizes[s];
    }
    public float getParameter(int p){
        return parameters[p];
    }
    public void createData(){
        /*En este punto todos los arreglos que guardan objetos tienen elementos nulos. Entonces hay que llenarlas con nuevas instancias,
          Para eso se creo un metodo estatico en las clases Node, Element y Condition que generan un arreglo lleno de objetos el cual es
          el que trabajaremos
        */
        node_list = Node.createNodes(sizes[NODES.ordinal()]);
        element_list = Element.createElements(sizes[ELEMENTS.ordinal()]);
        indices_dirich = new int[sizes[DIRICHLET.ordinal()]];
        dirichlet_list = Condition.createConditions(sizes[DIRICHLET.ordinal()]);
        neumann_list = Condition.createConditions(sizes[NEUMANN.ordinal()]);

    }

    public int[] getDirichletIndices() {
        return indices_dirich;
    }

    public Node[] getNodes() {
        return node_list;
    }

    public Element[] getElements() {
        return element_list;
    }
    public Condition[] getDirichlet(){
        return dirichlet_list;
    }
    public Condition[] getNeumann(){
        return neumann_list;
    }
    public Node getNode(int i){
        return node_list[i];
    }
    public Element getElement(int i){
        return element_list[i];
    }
    public Condition getCondition(int i, Sizes type){
        if(type == Sizes.DIRICHLET) return dirichlet_list[i];
        else return neumann_list[i];
    }
}
