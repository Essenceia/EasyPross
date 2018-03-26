package Controller;


import Interface.Object_Interface;
import Model.Abstract_Classes.Node_Model_Abstract;
import Model.Abstract_Classes.Object_Model_Abstract;
import Model.Abstract_Classes.Register_Model_Abstract;
import Model.Normal_Classes.Probe.Probe_End_Model;
import Model.Normal_Classes.Probe.Probe_Start_Model;
import Model.Normal_Classes.Wire.Wire_Model;

import java.io.File;
import java.util.*;

public class Graph_Manager_Controller implements Interface.Graph_Manager_Interface {


    private HashMap<Integer, Wire_Model> GArrettes;
    private HashMap<Integer, Node_Model_Abstract> GNoeuds;
    private HashMap<Integer, Probe_Start_Model> GDebut;
    private HashMap<Integer, Probe_End_Model> GFin;
    private XML_Manager_Controller XML_Graph;

    /**
     * Constructor
     */
    public Graph_Manager_Controller() {
        GArrettes = new HashMap<>();
        GNoeuds = new HashMap<>();
        GDebut = new HashMap<>();
        GFin = new HashMap<>();
    }

    //Helper to read graph
    //Bon voila une wildcard
    private void Print_info(HashMap<Integer, ? extends Object_Model_Abstract> map) {
        for (Map.Entry<Integer, ? extends Object_Model_Abstract> entry : map.entrySet()) {
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
        Helper_Controller.debugMessage3("Wires");
        Print_info(GArrettes);
        System.out.println("Probe debut");
        Print_info((HashMap<Integer, ? extends Object_Model_Abstract>) GDebut);
        System.out.println("Probe fin");
        Print_info((HashMap<Integer, ? extends Object_Model_Abstract>) GFin);
        Helper_Controller.debugMessage3("Nodes");
        Print_info((HashMap<Integer, ? extends Object_Model_Abstract>) GNoeuds);
        //Call action on all nodes
        for (Map.Entry<Integer, ? extends Node_Model_Abstract> entry : GNoeuds.entrySet()) {
            entry.getValue().action();
        }
    }

    /**
     * Override of creategraph from Graph_Manager_Interface
     */
    @Override
    public void createGraph(String path_to_file) { // Create graph
        load_new_module(path_to_file);
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
        XML_Graph = new XML_Manager_Controller();
        XML_Graph.openFile(path_to_file);
        XML_Graph.parseGraph(GArrettes, GDebut, GFin, GNoeuds);
    }

    //@Override
    public void save_module(String name_file) {
        XML_Graph.saveToXML(name_file, GArrettes, GDebut, GFin, GNoeuds);
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

    public Map<Integer, Wire_Model> getArrete() {
        return this.GArrettes;
    }

    public Map<Integer, Node_Model_Abstract> getNoeud() {
        return GNoeuds;
    }

    public HashMap<Integer, Probe_Start_Model> getDebut() {
        return GDebut;
    }

    public Map<Integer, Probe_End_Model> getFin() {
        return GFin;
    }

    /**
     * Search all probes & wires fot the value howms id
     * is of val id and get it's data
     *
     * @param id
     * @return data on item
     */
    public boolean[] GetDataOnId(int id) {
        boolean[] dataOnObject;
        //search probes
        if (this.GDebut.containsKey(id)) {
            dataOnObject = this.GDebut.get(id).getData();
        } else {
            if (this.GFin.containsKey(id)) {
                dataOnObject = this.GFin.get(id).getData();
            } else {
                if (this.GArrettes.containsKey(id)) {
                    dataOnObject = this.GArrettes.get(id).getData();
                } else {
                    //fill with default values
                    dataOnObject = new boolean[1];
                    Helper_Controller.errorMessage("Graph_Manager_Controller::GetDataOnId unknown id");
                    Helper_Controller.debugMessage4("Graph_Manager_Controller::GetDataOnId no data to be read at id " + id);
                }
            }
        }
        return dataOnObject;
    }

    /**
     * SetDataOnId
     * <p>
     * Write the data on the object with key=id
     *
     * @param id
     * @param data
     * @return
     */
    public boolean SetDataOnId(int id, boolean[] data) {
        boolean status = true;
        //search probes
        if (this.GDebut.containsKey(id)) {
            this.GDebut.get(id).setData(data);
        } else {
            if (this.GFin.containsKey(id)) {
                this.GFin.get(id).setData(data);
            } else {
                if (this.GArrettes.containsKey(id)) {
                    this.GArrettes.get(id).setData(data);
                } else {
                    status = false;
                    //fill with default values
                    Helper_Controller.errorMessage("Graph_Manager_Controller::SetDataOnId unknown id");
                    Helper_Controller.debugMessage4("Graph_Manager_Controller::SetDataOnId no data to be read at id " + id);
                }
            }
        }
        return status;
    }

    public boolean LoadDataOnNode(int Id, String path) {
        boolean retVal = false;
        File newData = new File(path);
        Register_Model_Abstract objectRegister;
        if (newData.exists() && newData.isDirectory()) {
            if (GNoeuds.containsKey(Id)) {
                objectRegister = (Register_Model_Abstract) GNoeuds.get(Id);
                objectRegister.transfertData(newData);
                //TODO add checks to verify the data
                objectRegister.reloadFileBuffer();
            }

        }return retVal;
    }
    public String GetFileDataOnNode(int Id) {
        String retVal = "";
        Register_Model_Abstract objectRegister;
            if (GNoeuds.containsKey(Id)) {
                objectRegister = (Register_Model_Abstract) GNoeuds.get(Id);
                retVal = objectRegister.getFilePath();
                retVal+= objectRegister.getFileName();
            }

        return retVal;}

    public void setDebut(HashMap<Integer, Probe_Start_Model> debut) {
        GDebut=debut;
    }

    public void resetGraphValues(){
        for(Object_Model_Abstract object: GNoeuds.values()){
            object.resetValues();
        }
        for (Object_Model_Abstract object : GArrettes.values()){
            object.resetValues();
        }
        for (Object_Model_Abstract object : GFin.values()){
            object.resetValues();
        }for (Object_Model_Abstract object : GDebut.values()){
            object.resetValues();
        }
    }
}
