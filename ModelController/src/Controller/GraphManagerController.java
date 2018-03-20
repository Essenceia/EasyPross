package Controller;

import Model.AbstractClasses.NodeModel_Abstract;
import Model.AbstractClasses.ObjectModel_Abstract;
import Model.AbstractClasses.ProbeModel_Abstract;
import Model.Probe.*;

import java.util.*;

public class GraphManagerController implements Interface.GraphManagerInterface {


    private HashMap<Integer, Model.Wire.Wire_Model> GArrettes;
    private HashMap<Integer, NodeModel_Abstract> GNoeuds;
    private HashMap<Integer, ProbeStartModel> GDebut;
    private HashMap<Integer, ProbeEndModel> GFin;
    private XMLManager XML_Graph;

    /**
     * Constructor
     */
    public GraphManagerController() {
        GArrettes = new HashMap<>();
        GNoeuds = new HashMap<>();
        GDebut = new HashMap<>();
        GFin = new HashMap<>();
    }

    //Helper to read graph
    //Bon voila une wildcard
    private void Print_info(HashMap<Integer, ? extends ObjectModel_Abstract> map) {
        for (Map.Entry<Integer, ? extends ObjectModel_Abstract> entry : map.entrySet()) {
            System.out.println("Id  = " + entry.getKey() + ", Description = " + entry.getValue().getDescription());
        }

    }

    /// Override methods \\\
    /// Delete execption thrown! \\\

    /**
     * Override of tick from Graph_Manager_Interface
     * Call action() of every Node stored
     */
    @Override
    public void tick() {

        //Debug read all nodes
        System.out.println("Read of our graph objects : ");
        System.out.println("Wires");
        Print_info(GArrettes);
        System.out.println("Probe debut");
        Print_info((HashMap<Integer, ? extends ObjectModel_Abstract>) GDebut);
        System.out.println("Probe fin");
        Print_info((HashMap<Integer, ? extends ObjectModel_Abstract>) GFin);
        System.out.println("Nodes");
        Print_info((HashMap<Integer, ? extends ObjectModel_Abstract>) GNoeuds);
        //Call action on all nodes
        for (Map.Entry<Integer, ? extends NodeModel_Abstract> entry : GNoeuds.entrySet()) {
            entry.getValue().action();
        }
    }

    /**
     * Override of documentManager from Graph_Manager_Interface
     */
    @Override
    public void documentManager() {// create DocumentManager instance -> To call in class constructor ? 
        // DocumentManager is a singleton
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Override of creategraph from Graph_Manager_Interface
     */
    @Override
    public void createGraph() { // Create graph
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Override of callAPI from Graph_Manager_Interface
     */
    @Override
    public void callAPI() { // Communication with API
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public void load_new_module(String path_to_file) {
        XML_Graph = new XMLManager();
        XML_Graph.open_file(path_to_file);
        XML_Graph.parse_graph(GArrettes, GDebut, GFin, GNoeuds);
    }

    //@Override
    public void save_module(String name_file) {
        XML_Graph.save_to_xml(name_file, GArrettes, GDebut, GFin, GNoeuds);
    }

    private void readGNoeuds() {
        GNoeuds.entrySet().forEach((mapentry) -> {
            System.out.println(mapentry.getValue().getDescription());
        });
    }

    private void readProbs() {
        System.out.println("Prob Start :");
        GDebut.entrySet().forEach((mapentry) -> {
            System.out.println(mapentry.getValue().getDescription());
        });
        System.out.println("Prob End :");
        GFin.entrySet().forEach((mapentry) -> {
            System.out.println(mapentry.getValue().getDescription());
        });
    }

    public Map<Integer, Model.Wire.Wire_Model> getArrete() {
        return GArrettes;
    }

    public Map<Integer, NodeModel_Abstract> getNoeud() {
        return GNoeuds;
    }

    public Map<Integer, ProbeStartModel> getDebut() {
        return GDebut;
    }

    public Map<Integer, ProbeEndModel> getFin() {
        return GFin;
    }

}
