
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
        //simple test
         Map<Integer,Wire> arrete= new HashMap<Integer, Wire>();
         Map<Integer, Node_Base> noeuds = new HashMap<Integer, Node_Base>();
         Map<Integer,Probe_Start> debut = new HashMap<Integer, Probe_Start>();
         Map<Integer,Probe_End> fin = new HashMap<Integer, Probe_End>(); 
        Graph_Manager Graph = new Graph_Manager();
        Graph.load_new_module("testfile.txt");
        System.out.println("Chargement des map fait");
        arrete = Graph.getArrete();
        noeuds = Graph.getNoeud();
        debut = Graph.getDebut();
        fin = Graph.getFin();
        Graph_simplifie_objet simp = new Graph_simplifie_objet(arrete,noeuds,debut,fin);
        try {
         FileOutputStream FileOut =
         new FileOutputStream("simplifier.ser");
         ObjectOutputStream out = new ObjectOutputStream(FileOut);
         out.writeObject(simp);
         out.close();
         FileOut.close();
      } catch (IOException i) {
         i.printStackTrace();
      }

    }
    
}
