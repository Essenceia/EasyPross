package Controller;

import Model.AbstractClasses.*;
import Model.GlobalDefines;
import Model.Logic.Gate.ANDModel;
import Model.Logic.Gate.NOTModel;
import Model.Logic.Gate.ORModel;
import Model.Logic.Unit.ALUModel;
import Model.Logic.Unit.DEMUXModel;
import Model.Logic.Unit.MUXModel;
import Model.Probe.ProbeEndModel;
import Model.Probe.ProbeStartModel;
import Model.Register.DATAModel;
import Model.Register.PCModel;
import Model.Register.TEXTModel;
import Model.Wire.WireModel;
import org.jdom2.Attribute;
import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.Element;

import javax.print.Doc;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

public class XMLManager {

    private org.jdom2.Document document;
    private Element racine;
    private List wire;
    private List node;
    private List probe;

    protected static final String LABEL_ROOT = "graph";
    protected static final String LABEL_NODE = "node";
    protected static final String LABEL_WIRE = "wire";
    protected static final String LABEL_WIRE_IN = "wire_in";
    protected static final String LABEL_WIRE_OUT = "wire_out";
    protected static final String LABEL_IO = "io";
    protected static final String LABEL_PROBE = "probe";
    protected static final String ATTRIBUTE_ID = "id";
    protected static final String ATTRIBUTE_TYPE = "type";
    protected static final String ATTRIBUTE_SIZE = "size";
    protected static final String ATTRIBUTE_FILE_NAME = "file_name";
    protected static final String ATTRIBUTE_FILE_PATH = "path";
    protected static final String ATTRIBUTE_CONTROL_BIT_SIZE = "control_bits";
    protected static final String ATTRIBUTE_MEMORY_BLOCK_SIZE = "memory_block_size";
    protected static final String ATTRIBUTE_NUMBER_MEMORY_BLOCKS = "memory_number_block";

    public XMLManager() {
        racine = new Element(LABEL_ROOT);
    }

    public void save_and_print(String file_name) {
        document = new Document(racine);
        affiche();
        enregistre(file_name);
    }

    private void affiche() {
        try {
            //On utilise ici un affichage classique avec getPrettyFormat()
            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
            sortie.output(document, System.out);
        } catch (java.io.IOException e) {
            System.out.println(e);
        }
    }

    private void enregistre(String fichier) {
        try {
            //On utilise ici un affichage classique avec getPrettyFormat()
            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
            //Remarquez qu'il suffit simplement de créer une instance de FileOutputStream
            //avec en argument le nom du fichier pour effectuer la sérialisation.
            sortie.output(document, new FileOutputStream(fichier));
        } catch (java.io.IOException e) {
        }
    }

    public void open_file(String file_name) {
        SAXBuilder sxb = new SAXBuilder();
        try {
            //read file
            document = sxb.build(new File(file_name));
        } catch (Exception e) {
        }

        //get base ellement of our xml tree
        racine = document.getRootElement();

    }

    public void parse_graph(HashMap<Integer, WireModel> GArrettes, HashMap<Integer,
            ProbeStartModel> GSProbe, HashMap<Integer, ProbeEndModel> GEProbe, HashMap<Integer,
            NodeModel_Abstract> GNode) {
        //On crée une List contenant tous les noeuds "etudiant" de l'Element racine
        this.node = racine.getChildren(LABEL_NODE);
        this.wire = racine.getChildren(LABEL_WIRE);
        this.probe = racine.getChildren(LABEL_PROBE);

        //must parse wire first next objects depend on it
        parse_wire(GArrettes);
        parse_probe(GSProbe, GEProbe, GArrettes);
        parse_node(GNode, GArrettes);


    }

    private Integer get_attribute(Element curent, String attr_name) {
        Attribute attri = curent.getAttribute(attr_name);
        Integer value = 0;
        try {
            value = attri.getIntValue();
        } catch (DataConversionException e) {
            e.printStackTrace();
        }
        return value;
    }

    private void parse_wire(HashMap<Integer, WireModel> GArrettes) {
        //On crée un Iterator sur notre liste
        Iterator i = wire.iterator();
        WireModel nv_wire;
        Integer id, size;
        while (i.hasNext()) {
            //Read all wires
            Element courant = (Element) i.next();
            id = get_attribute(courant, ATTRIBUTE_ID);
            size = get_attribute(courant, ATTRIBUTE_SIZE);
            nv_wire = new WireModel(id, size);
            //set values
            nv_wire.setData(get_IO_values(courant, size));
            //add to hash map
            GArrettes.put(id, nv_wire);
            //print the currently added ellement
            System.out.println("Added to graph : wire - id : " + id.toString());
        }
    }

    private void parse_probe(HashMap<Integer, ProbeStartModel> GSProbe, HashMap<Integer, ProbeEndModel> GEProbe, HashMap<Integer, WireModel> GArrettes) {
        //On crée un Iterator sur notre liste
        Iterator i = probe.iterator();
        Integer id, type;
        Element courant;
        List child_list;
        while (i.hasNext()) {
            //Read all probes
            courant = (Element) i.next();
            id = get_attribute(courant, ATTRIBUTE_ID);
            type = get_attribute(courant, ATTRIBUTE_TYPE);
            //get the id of connected wire

            //get the list of children
            child_list = courant.getChildren(LABEL_WIRE);

            //select the approporeat probe type
            System.out.println("Creating probes of type " + type.toString() + " with id " + id.toString());
            switch (type) {
                case GlobalDefines.TYPE_PROBE_START:
                    ProbeStartModel s_nv_probe = new ProbeStartModel(id, list_wires(child_list, GArrettes));
                    GSProbe.put(id, s_nv_probe);
                    break;
                case GlobalDefines.TYPE_PROBE_END:
                    ProbeEndModel e_nv_probe = new ProbeEndModel(id, list_wires(child_list, GArrettes));
                    GEProbe.put(id, e_nv_probe);
                    break;
                default:
                    System.out.println("Error , unknown probe type : " + type.toString());
                    break;
            }

        }
    }

    private void parse_node(HashMap<Integer, NodeModel_Abstract> GNode, HashMap<Integer, WireModel> GArrettes) {
        //On crée un Iterator sur notre liste
        Iterator i = node.iterator();
        NodeModel_Abstract nv_node;
        Integer id, type;
        //Integer for ALU
        Integer control_bit_num;
        //Values for memory
        String path, fname;
        Integer block_size, num_block;
        Element courant;
        List child_list;
        Vector<WireModel> in_wire;
        Vector<WireModel> out_wire;
        while (i.hasNext()) {
            //Read all probes
            courant = (Element) i.next();
            id = get_attribute(courant, ATTRIBUTE_ID);
            type = get_attribute(courant, ATTRIBUTE_TYPE);
            //get the id of connected wire
            //get the list of children
            child_list = courant.getChildren(LABEL_WIRE_IN);
            in_wire = list_wires(child_list, GArrettes);
            child_list = courant.getChildren(LABEL_WIRE_OUT);
            out_wire = list_wires(child_list, GArrettes);

            nv_node = null;

            //select the approporeat node type
            switch (type) {
                case GlobalDefines.TYPE_LOGIC_ALU:
                    control_bit_num = get_attribute(courant, ATTRIBUTE_CONTROL_BIT_SIZE);
                    nv_node = new ALUModel(true, in_wire, out_wire, id, control_bit_num);
                    break;
                case GlobalDefines.TYPE_LOGIC_NOT:
                    nv_node = new NOTModel(id, in_wire, out_wire, true);
                    break;
                case GlobalDefines.TYPE_LOGIC_AND:
                    nv_node = new ANDModel(id, in_wire, out_wire, true);
                    break;
                case GlobalDefines.TYPE_LOGIC_OR:
                    nv_node = new ORModel(id, in_wire, out_wire, true);
                    break;
                case GlobalDefines.TYPE_LOGIC_DEMUX:
                    control_bit_num = get_attribute(courant, ATTRIBUTE_CONTROL_BIT_SIZE);
                    nv_node = new DEMUXModel(true, id, in_wire, out_wire, control_bit_num);
                    break;
                case GlobalDefines.TYPE_LOGIC_MUX:
                    control_bit_num = get_attribute(courant, ATTRIBUTE_CONTROL_BIT_SIZE);
                    nv_node = new MUXModel(true, id, type, in_wire, out_wire, control_bit_num);
                    break;
                case GlobalDefines.TYPE_REG_DATA:
                    path = get_text_attr(courant, ATTRIBUTE_FILE_PATH);
                    fname = get_text_attr(courant, ATTRIBUTE_FILE_NAME);
                    block_size = get_attribute(courant, ATTRIBUTE_MEMORY_BLOCK_SIZE);
                    num_block = get_attribute(courant, ATTRIBUTE_NUMBER_MEMORY_BLOCKS);
                    nv_node = new DATAModel(id, in_wire, out_wire, path, fname, block_size, num_block);
                    break;
                case GlobalDefines.TYPE_REG_PC:

                    path = get_text_attr(courant, ATTRIBUTE_FILE_PATH);
                    fname = get_text_attr(courant, ATTRIBUTE_FILE_NAME);
                    block_size = get_attribute(courant, ATTRIBUTE_MEMORY_BLOCK_SIZE);
                    //only have 1 block by default
                    nv_node = new PCModel(id, in_wire, out_wire, path, fname, block_size);
                    break;
                case GlobalDefines.TYPE_REG_TEXT:
                    path = get_text_attr(courant, ATTRIBUTE_FILE_PATH);
                    fname = get_text_attr(courant, ATTRIBUTE_FILE_NAME);
                    block_size = get_attribute(courant, ATTRIBUTE_MEMORY_BLOCK_SIZE);
                    num_block = get_attribute(courant, ATTRIBUTE_NUMBER_MEMORY_BLOCKS);
                    nv_node = new TEXTModel(id, in_wire, out_wire, path, fname, block_size, num_block);
                    break;
                //Todo : add other types of nodes
                default:
                    System.out.println("Error , unknown probe type : " + type.toString());
                    break;
            }
            if (nv_node != null) GNode.put(id, nv_node);

        }
    }

    //get a vector of all the connected wires
    private Vector<WireModel> list_wires(List wire_children, HashMap<Integer, WireModel> GArrettes) {
        Iterator i = wire_children.iterator();
        Element curent;
        Integer w_id;
        Vector<WireModel> tmpvw = new Vector<>();//temoray vector of connected wires
        while (i.hasNext()) {
            curent = (Element) i.next();
            w_id = get_attribute(curent, "id");
            //verify if the id exists in the map
            if (!GArrettes.containsKey(w_id)) {
                System.out.println("Erreur : pas de wire avec l'id referencer par le probel , id : " +
                        w_id.toString());
            } else {
                tmpvw.add(GArrettes.get(w_id));
            }
        }
        //debug read values of wire found
        String str_io_values = "";
        System.out.println("Wire :");
        for (WireModel debug_wire : tmpvw) {
            for (boolean b : debug_wire.getData()) {
                if (b == true) str_io_values += "1";
                else str_io_values += "0";
            }
            System.out.println("- id:" + debug_wire.getId() + " " + str_io_values);
        }
        return tmpvw;
    }

    //get text block attribute attached to object if any
    private String get_text_attr(Element object, String attri_name) {
        Attribute attr;
        String nv_string;
        attr = object.getAttribute(attri_name);
        nv_string = attr.toString();
        return nv_string;
    }

    //Read values of io to set default values of bools
    private boolean[] get_IO_values(Element object, Integer size) {
        boolean[] ret_vector = new boolean[size];
        Element next_elem;
        List io_list = object.getChildren("io");
        Iterator i = io_list.iterator();
        Boolean bval;
        //iterator over all io ellements
        for (int index = 0; index < size; index++) {
            if (i.hasNext()) {
                next_elem = (Element) i.next();
                /*
                0 - false
                1 - true
                 */
                bval = (next_elem.getValue() == "1");//set to true or false
            } else {
                bval = false;
            }
            ret_vector[index] = bval;
        }
        return ret_vector;
    }

    //write back values
    public void save_to_xml(String file_name, HashMap<Integer, WireModel> GArrettes, HashMap<Integer,
            ProbeStartModel> GSProbe, HashMap<Integer, ProbeEndModel> GEProbe, HashMap<Integer,
            NodeModel_Abstract> GNode) {
        //reconstruct xml from current graph
        //build document and root
        Element root = new Element(LABEL_ROOT);
        Document doc = new Document(root);
        save_wire(root, GArrettes);
        save_probe(root, GSProbe, GEProbe);
        save_node(root, GNode);
        try
        {
            //On utilise ici un affichage classique avec getPrettyFormat()
            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
            //Remarquez qu'il suffit simplement de créer une instance de FileOutputStream
            //avec en argument le nom du fichier pour effectuer la sérialisation.
            sortie.output(document, new FileOutputStream(file_name));
        }
        catch (java.io.IOException e){}


    }

    private void save_wire(Element root, HashMap<Integer, WireModel> GArrettes) {
        Element tmpelem;
        WireModel tmpwire;
        // Getting a Set of Key-value pairs
        Set entrySet = GArrettes.entrySet();
        Map.Entry ellem;
        // Obtaining an iterator for the entry set
        Iterator it = entrySet.iterator();
        while (it.hasNext()) {
            tmpelem = new Element(LABEL_WIRE);
            ellem =  (Map.Entry)it.next();
            tmpwire = (WireModel)ellem.getValue();
            set_basic_attributes( tmpwire, tmpelem);
            //set io values in wire
            add_io_ellements(tmpwire, tmpelem);
            root.addContent(tmpelem);
        }
    }

    private void save_probe(Element root, HashMap<Integer,
            ProbeStartModel> GSProbe, HashMap<Integer, ProbeEndModel> GEProbe) {
        Element tmpelem;
        ProbeModel_Abstract probe;
        Map.Entry ellem;
        // Getting a Set of Key-value pairs
        Set sentrySet = GSProbe.entrySet();
        Set eentrySet = GEProbe.entrySet();
        // Obtaining an iterator for the entry set
        Iterator sit = sentrySet.iterator();
        Iterator eit = eentrySet.iterator();
        while (sit.hasNext() || eit.hasNext()) {
            tmpelem = new Element(LABEL_PROBE);
            if (sit.hasNext()) {
                ellem = (Map.Entry) sit.next();
                probe = (ProbeModel_Abstract) ellem.getValue();
            }
           else {
                ellem = (Map.Entry) eit.next();
                probe = (ProbeModel_Abstract) ellem.getValue();
            }
            set_basic_attributes( probe, tmpelem);
            //set connected wires
            connected_wire(LABEL_WIRE, probe.getWire(), tmpelem);
            root.addContent(tmpelem);
        }
    }

    private void save_node(Element root, HashMap<Integer, NodeModel_Abstract> GNode) {
        Element tmpelem;
        NodeModel_Abstract node;
        // Getting a Set of Key-value pairs
        Set entrySet = GNode.entrySet();
        Map.Entry ellem;
        // Obtaining an iterator for the entry set
        Iterator it = entrySet.iterator();
        while (it.hasNext()) {
            tmpelem = new Element(LABEL_NODE);
            ellem = (Map.Entry)it.next();
            node = (NodeModel_Abstract) ellem.getValue();
            set_basic_attributes((ObjectModel_Abstract) node, tmpelem);
            //set connected wires
            connected_wire(LABEL_WIRE_IN, node.getInput(), tmpelem);
            connected_wire(LABEL_WIRE_OUT, node.getOutput(), tmpelem);
            //add specific node value parameters
            switch (node.getType()) {

                //Control bit size
                case GlobalDefines.TYPE_LOGIC_ALU:
                case GlobalDefines.TYPE_LOGIC_DEMUX:
                case GlobalDefines.TYPE_LOGIC_MUX:
                    LogicModel_Abstract logic = (LogicModel_Abstract) node;
                    tmpelem.setAttribute(ATTRIBUTE_CONTROL_BIT_SIZE, String.valueOf(logic.get_control_bit()));
                    break;
                    //attributes of the memory objects : file name, file path, memory block size, number of blocks in memory
                case GlobalDefines.TYPE_REG_PC:
                case GlobalDefines.TYPE_REG_DATA:
                case GlobalDefines.TYPE_REG_TEXT:
                    RegisterModel_Abstract register = (RegisterModel_Abstract)node;
                    tmpelem.setAttribute(ATTRIBUTE_FILE_PATH,register.getFilePath());
                    tmpelem.setAttribute(ATTRIBUTE_FILE_NAME,register.getFileName());
                    tmpelem.setAttribute(ATTRIBUTE_MEMORY_BLOCK_SIZE,String.valueOf(register.getBlockCount()));
                    tmpelem.setAttribute(ATTRIBUTE_NUMBER_MEMORY_BLOCKS,String.valueOf(register.getBlockSize()));

            }
            root.addContent(tmpelem);
        }
    }

    private void connected_wire(String lable, Vector<WireModel> connected, Element motherelem) {
        Element tmpelem;
        for (WireModel wire : connected) {
            tmpelem = new Element(lable);
            tmpelem.setAttribute(ATTRIBUTE_ID, String.valueOf(wire.getId()));
            motherelem.setContent(tmpelem);
        }
    }

    private void add_io_ellements(WireModel wire, Element motherelem) {
        boolean[] data = wire.getData();
        for (boolean b : data) {
            Element io_elem = new Element(LABEL_IO);
            if (b) io_elem.setText("1");
            else io_elem.setText("0");
            motherelem.addContent(io_elem);
        }
    }

    private void set_basic_attributes(ObjectModel_Abstract object, Element elem) {
        //set attributes id / type
        elem.setAttribute(ATTRIBUTE_ID, String.valueOf(object.getId()));
        elem.setAttribute(ATTRIBUTE_TYPE, String.valueOf(object.getType()));
    }

}
