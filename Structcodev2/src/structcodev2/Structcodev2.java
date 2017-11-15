
package structcodev2;

import Controller.Graph_Manager;
import Controller.Graph_simplifie_objet;
import Model.Node_Base;
import Model.Probe_End;
import Model.Probe_Start;
import Model.Wire;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Structcodev2 {

    public static void main(String[] args) {
        Graph_Manager Graph = new Graph_Manager();
        Graph.load_new_module("testfile.txt");
        System.out.println("Chargement des map fait");
        Graph.init_simplified_graph("serialtest.txt");
}

    }
    

