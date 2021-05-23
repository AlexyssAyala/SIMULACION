package Classes;

import java.util.ArrayList;
import Enums.*;
import com.sun.glass.ui.Size;

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
        sizes[Sizes.NODES.ordinal()]=nnodes;
        sizes[Sizes.ELEMENTS.ordinal()]=neltos;
        sizes[Sizes.DIRICHLET.ordinal()]=ndirich;
        sizes[Sizes.NEUMANN.ordinal()]=nneu;
    }
    public int getSize(int s){
        return sizes[s];
    }
    public float getParameter(int p){
        return parameters[p];
    }
    public void createData(){
        node_list = new Node[Sizes.NODES.ordinal()];
        element_list = new Element[Sizes.ELEMENTS.ordinal()];
        indices_dirich = new int[Sizes.DIRICHLET.ordinal()];
        dirichlet_list = new Condition[Sizes.DIRICHLET.ordinal()];
        neumann_list = new Condition[Sizes.NEUMANN.ordinal()];
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
