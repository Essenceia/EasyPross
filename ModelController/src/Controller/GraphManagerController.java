package Controller;

import Model.AbstractClasses.NodeModel_Abstract;
import Model.Logic.Gate.ANDModel;
import Model.Logic.Gate.ORModel;
import Model.Probe.*;
import Model.Wire.WireModel;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class GraphManagerController implements Interface.GraphManagerInterface {

    protected Map<Integer, WireModel> GArrettes;
    protected Map<Integer, NodeModel_Abstract> GNoeuds;
    protected Map<Integer, ProbeStartModel> GDebut;
    protected Map<Integer, ProbeEndModel> GFin;
    protected SimplifiedGraphObjectController GSimplified;

    /**
     * Constructor
     */
    public GraphManagerController() {
        //this.documentManager(); -> Possible or not ?
        GArrettes = new HashMap<Integer, WireModel>();
        GNoeuds = new HashMap<Integer, NodeModel_Abstract>();
        GDebut = new HashMap<Integer, ProbeStartModel>();
        GFin = new HashMap<Integer, ProbeEndModel>();
    }

    /// Override methods \\\
    /// Delete execption thrown! \\\
    /**
     * Override of tick from GraphManagerInterface
     */
    @Override
    public void tick() { // Call action() of every Node stored
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Override of documentManager from GraphManagerInterface
     */
    @Override
    public void documentManager() {// create DocumentManager instance -> To call in class constructor ? 
        // DocumentManager is a singleton
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Override of creategraph from GraphManagerInterface
     */
    @Override
    public void createGraph() { // Create graph
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Override of callAPI from GraphManagerInterface
     */
    @Override
    public void callAPI() { // Communication with API
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public void load_new_module(String path_to_file) {
        DocumentManagerController.open(path_to_file);
        create_all_wires();
        while (DocumentManagerController.hasNextLine()) {
            this.read_line(DocumentManagerController.readLine());
        }
        readGNoeuds();

    }

    //@Override
    public void init_simplified_graph(String file) {
        GSimplified = new SimplifiedGraphObjectController(GArrettes, GNoeuds, GDebut, GFin);
        GSimplified.serialise_to_file(file);
    }

    private void create_all_wires() {
        while (DocumentManagerController.hasNextLine()) {
            String read = DocumentManagerController.readLine();
            System.out.println("Line read " + read);
            String[] line = read.split(",");
            System.out.println("Result of split ");
            for (int i = 0; i < line.length; i++) {
                System.out.println("Index " + Integer.toString(i) + " : " + line[i]);

            }
            if (Integer.valueOf(line[1]) == 1) {
                System.out.println("We have found a wire");
                //we have wire, create
                GArrettes.put(Integer.valueOf(line[0]),
                        this.createWire(line));
            }
        }
        //debug read all wires
        GArrettes.entrySet().forEach((mapentry) -> {
            System.out.println(mapentry.getValue().getDescription());
        });
        //reset document line to zero
        DocumentManagerController.to_begining();
    }

    private void read_line(String toread) {
        Integer type;
        if (toread.length() > 1) {
            System.out.println("Parsing line :" + toread);
            String[] splitByComa = toread.split(",");
            //id=Integer.valueOf(splitByComa[0]);
            type = Integer.valueOf(splitByComa[1]);
            switch (type) {
                case 1:
                    System.out.println("Wire already created");

                    break;
                case 2:
                case 3:
                    createLGate(splitByComa, type);
                    break;
                case 4:
                case 5:
                    createProb(splitByComa, type);
                    break;
                default:
                    System.out.println("Error : unknown type " + splitByComa[1]);
            }
        } else {
            System.out.println("Error line to short , line:" + toread);
        }
    }

    private WireModel createWire(String[] parsedLine) {
        return new WireModel(Integer.valueOf(parsedLine[0]),
                 Integer.valueOf(parsedLine[2]));
    }

    private Vector<WireModel> fillById(String[] list) {
        Vector<WireModel> nvV = new Vector<>();
        Integer tmpId;
        if (list.length == 0) {
            System.out.println("Error list of wire lenght is 0 , list is :" + Arrays.toString(list));
        } else {
            for (String wId : list) {
                tmpId = Integer.valueOf(wId);
                if (GArrettes.containsKey(tmpId)) {
                    nvV.add(GArrettes.get(tmpId));
                } else {
                    System.out.println("Error no Wire with ID " + wId + " in object " + list[0]);
                }
            }
        }
        return nvV;

    }

    private void createLGate(String[] line, Integer type) {
        Integer id = Integer.valueOf(line[0]);
        System.out.println("Incomming wire id list " + line[2]);
        String[] icommingWireID = line[2].split("\\.");
        System.out.println("Outcomming wire id list " + line[3]);
        String[] outcommingWireID = line[3].split("\\.");
        Vector<WireModel> in_wire;
        Vector<WireModel> out_wire;
        boolean synchrone = false;
        String description = "Logic Gate:";

        in_wire = fillById(icommingWireID);
        out_wire = fillById(outcommingWireID);
        switch (type) {
            case 2:
                GNoeuds.put(id, new ANDModel(synchrone, id, type, description, in_wire, out_wire));
                break;
            case 3:
                GNoeuds.put(id, new ORModel(synchrone, id, type, description, in_wire, out_wire));
                break;
        }
    }

    private void createProb(String[] line, Integer type) {
        Integer id = Integer.valueOf(line[0]);
        System.out.println("Connected wire id list " + line[2]);
        String[] conectedWireID = line[2].split("\\.");
        Vector<WireModel> wire;
        wire = fillById(conectedWireID);
        switch (type) {
            case 4:
                GDebut.put(id, new ProbeStartModel(id, wire));
                break;
            case 5:
                GFin.put(id, new ProbeEndModel(id, wire));
                break;
        }
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

    public Map<Integer, WireModel> getArrete() {
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
