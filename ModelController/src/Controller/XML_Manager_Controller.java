package Controller;
//todo add size on wire for save
import Model.Abstract_Classes.Global_Defines_Abstract;
import Model.Abstract_Classes.Logic_Model_Abstract;
import Model.Abstract_Classes.Node_Model_Abstract;
import Model.Abstract_Classes.Object_Model_Abstract;
import Model.Abstract_Classes.Probe_Model_Abstract;
import Model.Abstract_Classes.Register_Model_Abstract;
import Model.Normal_Classes.Logic.Gate.And_Model;
import Model.Normal_Classes.Logic.Gate.Not_Model;
import Model.Normal_Classes.Logic.Gate.Or_Model;
import Model.Normal_Classes.Logic.Unit.Alu_Model;
import Model.Normal_Classes.Logic.Unit.Decoder;
import Model.Normal_Classes.Logic.Unit.Demux_Model;
import Model.Normal_Classes.Logic.Unit.Mux_Model;
import Model.Normal_Classes.Probe.Probe_End_Model;
import Model.Normal_Classes.Probe.Probe_Start_Model;
import Model.Normal_Classes.Register.Data_Model;
import Model.Normal_Classes.Register.Pc_Model;
import Model.Normal_Classes.Register.Prog_Model;
import Model.Normal_Classes.Wire.Wire_Model;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import org.jdom2.Attribute;
import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.Element;

import javax.print.Doc;


public class XML_Manager_Controller {

    private org.jdom2.Document document;
    private org.jdom2.Element racine;
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

    /**
     *
     */
    public XML_Manager_Controller() {

        racine = new Element(LABEL_ROOT);

    }

    /**
     *
     * @param fileName
     */
    public void saveAndPrint(String fileName) {
        document = new Document(racine);
        display();
        save(fileName);
    }

    /**
     *
     */
    private void display() {
        try {
            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
            sortie.output(document, System.out);
        } catch (java.io.IOException e) {
            System.out.println(e);
        }
    }

    /**
     *
     * @param file
     */
    private void save(String file) {
        try {
            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
            sortie.output(document, new FileOutputStream(file));
        } catch (java.io.IOException e) {
        }
    }

    /**
     *
     * @param fileName
     */
    public void openFile(String fileName) {
        Helper_Controller.debugMessage0("Opening file XML");
        Helper_Controller.debugMessage0("Building sax");
        SAXBuilder sxb = new SAXBuilder();

        try {
            Helper_Controller.debugMessage0("file to be opened "+fileName);

            document = sxb.build(new File(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Helper_Controller.debugMessage0("Getting root ellement");
        racine = document.getRootElement();
        Helper_Controller.debugMessage0("openFile finished");
    }

    /**
     * @param GAretes
     * @param GSProbe
     * @param GEProbe
     * @param GNode
     */
    public void parseGraph(HashMap<Integer, Wire_Model> GAretes, HashMap<Integer, Probe_Start_Model> GSProbe, HashMap<Integer, Probe_End_Model> GEProbe, HashMap<Integer, Node_Model_Abstract> GNode) {
        this.node = racine.getChildren(LABEL_NODE);
        this.wire = racine.getChildren(LABEL_WIRE);
        this.probe = racine.getChildren(LABEL_PROBE);
        parseWire(GAretes);
        parseProbe(GSProbe, GEProbe, GAretes);
        parseNode(GNode, GAretes);
    }

    /**
     *
     * @param current
     * @param attributeName
     * @return
     */
    private Integer myGetAttribute(Element current, String attributeName) {
        Attribute attri = current.getAttribute(attributeName);
        Integer value = 0;
        try {
            value = attri.getIntValue();
        } catch (DataConversionException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     *
     * @param GAretes
     */
    private void parseWire(HashMap<Integer, Wire_Model> GAretes) {
        Iterator i = wire.iterator();
        Wire_Model newWire;
        Integer id, size;
        while (i.hasNext()) {
            Element current = (Element) i.next();
            id = myGetAttribute(current,ATTRIBUTE_ID);
            size = myGetAttribute(current, ATTRIBUTE_SIZE);
            newWire = new Wire_Model(id, size);
            newWire.setData(getIOValues(current, size));
            GAretes.put(id, newWire);
            System.out.println("Added to graph : wire - id : " + id.toString());
        }
    }

    /**
     *XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
     xmlOutputter.output(document, System.out);
     * @param GSProbe
     * @param GEProbe
     * @param GAretes
     */
    private void parseProbe(HashMap<Integer, Probe_Start_Model> GSProbe, HashMap<Integer, Probe_End_Model> GEProbe, HashMap<Integer, Wire_Model> GAretes) {
        Iterator i = probe.iterator();
        Integer id, type;
        Element current;
        List child_list;
        while (i.hasNext()) {
            current = (Element) i.next();
            id = myGetAttribute(current, ATTRIBUTE_ID);
            type = myGetAttribute(current, ATTRIBUTE_TYPE);
            child_list = current.getChildren(LABEL_WIRE);
            System.out.println("Creating probes of type " + type.toString() + " with id " + id.toString());
            switch (type) {
                case Global_Defines_Abstract.TYPE_PROBE_START:
                    Probe_Start_Model s_nv_probe = new Probe_Start_Model(id, listWires(child_list, GAretes));
                    GSProbe.put(id, s_nv_probe);
                    break;
                case Global_Defines_Abstract.TYPE_PROBE_END:
                    Probe_End_Model e_nv_probe = new Probe_End_Model(id, listWires(child_list, GAretes));
                    GEProbe.put(id, e_nv_probe);
                    break;
                default:
                    System.out.println("Error , unknown probe type : " + type.toString());
                    break;
            }
        }
    }

    /**
     *
     * @param GNode
     * @param GAretes
     */
    private void parseNode(HashMap<Integer, Node_Model_Abstract> GNode, HashMap<Integer, Wire_Model> GAretes) {
        Iterator i = node.iterator();
        Node_Model_Abstract newNode;
        Integer id, type, controlBitNum, blockSize, numBlock;
        String path, fname;
        Element current;
        List childList;
        Vector<Wire_Model> wireIn;
        Vector<Wire_Model> wireOut;
        while (i.hasNext()) {
            current = (Element) i.next();
            id = myGetAttribute(current, ATTRIBUTE_ID);
            type = myGetAttribute(current, ATTRIBUTE_TYPE);
            childList = current.getChildren(LABEL_WIRE_IN);
            wireIn = listWires(childList, GAretes);
            System.out.println("Child in number of items "+wireIn.size());
            childList = current.getChildren(LABEL_WIRE_OUT);
            wireOut = listWires(childList, GAretes);
            System.out.println("Child in number of items "+wireOut.size());

            newNode = null;
            switch (type) {
                case Global_Defines_Abstract.TYPE_LOGIC_ALU:
                    controlBitNum = myGetAttribute(current, ATTRIBUTE_CONTROL_BIT_SIZE);
                    newNode = new Alu_Model(wireIn, wireOut, id,"");
                    break;
                case Global_Defines_Abstract.TYPE_LOGIC_NOT:
                    newNode = new Not_Model(id, wireIn, wireOut);
                    break;
                case Global_Defines_Abstract.TYPE_LOGIC_AND:
                    newNode = new And_Model(id, wireIn, wireOut);
                    break;
                case Global_Defines_Abstract.TYPE_LOGIC_OR:
                    newNode = new Or_Model(id, wireIn, wireOut);
                    break;
                case Global_Defines_Abstract.TYPE_LOGIC_DEMUX:
                    controlBitNum = myGetAttribute(current, ATTRIBUTE_CONTROL_BIT_SIZE);
                    newNode = new Demux_Model(id,"", wireIn, wireOut);
                    break;
                case Global_Defines_Abstract.TYPE_LOGIC_MUX:
                    controlBitNum = myGetAttribute(current, ATTRIBUTE_CONTROL_BIT_SIZE);
                    newNode = new Mux_Model(id,"", wireIn, wireOut);
                    break;
                case Global_Defines_Abstract.TYPE_REG_DATA:
                    path = getTextAttributes(current, ATTRIBUTE_FILE_PATH);
                    fname = getTextAttributes(current, ATTRIBUTE_FILE_NAME);
                    blockSize = myGetAttribute(current, ATTRIBUTE_MEMORY_BLOCK_SIZE);
                    numBlock = myGetAttribute(current, ATTRIBUTE_NUMBER_MEMORY_BLOCKS);
                    newNode = new Data_Model(id, wireIn, wireOut, path, fname, blockSize, numBlock);
                    break;
                case Global_Defines_Abstract.TYPE_REG_PC:
                    path = getTextAttributes(current, ATTRIBUTE_FILE_PATH);
                    fname = getTextAttributes(current, ATTRIBUTE_FILE_NAME);
                    blockSize = myGetAttribute(current, ATTRIBUTE_MEMORY_BLOCK_SIZE);
                    Helper_Controller.debugMessage("Creating pc node");
                    newNode = new Pc_Model(id, wireIn, wireOut, path, fname, blockSize);
                    break;
                case Global_Defines_Abstract.TYPE_REG_TEXT:
                    path = getTextAttributes(current, ATTRIBUTE_FILE_PATH);
                    fname = getTextAttributes(current, ATTRIBUTE_FILE_NAME);
                    blockSize = myGetAttribute(current, ATTRIBUTE_MEMORY_BLOCK_SIZE);
                    numBlock = myGetAttribute(current, ATTRIBUTE_NUMBER_MEMORY_BLOCKS);
                    newNode = new Prog_Model(id, wireIn, wireOut, path, fname, blockSize,numBlock);
                    break;
                case Global_Defines_Abstract.TYPE_LOGIC_DECODER:
                    newNode = new Decoder(id,"" ,wireIn, wireOut);
                    break;
                default:
                    System.out.println("Error , unknown probe type : " + type.toString());
                    break;
            }
            if (newNode != null) {
                GNode.put(id, newNode);
            }
        }
    }

    /**
     *
     * @param wireChildren
     * @param GAretes
     * @return
     */
    private Vector<Wire_Model> listWires(List wireChildren, HashMap<Integer, Wire_Model> GAretes) {
        Iterator i = wireChildren.iterator();
        Element current;
        Integer wireId;
        Vector<Wire_Model> tempWire = new Vector<>();//temoray vector of connected wires
        while (i.hasNext()) {
            current = (Element) i.next();
            wireId = myGetAttribute(current, "id");
            if (!GAretes.containsKey(wireId)) {
                System.out.println("Erreur : pas de wire avec l'id referencer par le probel , id : "
                        + wireId.toString());
            } else {
                tempWire.add(GAretes.get(wireId));
            }
        }
        String str_io_values = "";
        System.out.println("Wire :");
        for (Wire_Model debug_wire : tempWire) {
            for (boolean b : debug_wire.getData()) {
                if (b == true) {
                    str_io_values += "1";
                } else {
                    str_io_values += "0";
                }
            }
            System.out.println("- id:" + debug_wire.getId() + " " + str_io_values);
        }
        return tempWire;
    }

    /**
     *
     * @param object
     * @param attributeName
     * @return
     */
    private String getTextAttributes(Element object, String attributeName) {
        Attribute attribute;
        String newString;
        attribute = object.getAttribute(attributeName);
        newString = attribute.getValue();
        System.out.println("getTextAttributes:: attribute:"+attributeName+" value:"+newString);
        return newString;
    }

    /**
     *
     * @param object
     * @param size
     * @return
     */
    private boolean[] getIOValues(Element object, Integer size) {
        boolean[] retVector = new boolean[size];
        Element nextElement;
        List io_list = object.getChildren("io");
        Iterator i = io_list.iterator();
        Boolean bVal;
        for (int index = 0; index < size; index++) {
            if (i.hasNext()) {
                nextElement = (Element) i.next();
                bVal = (nextElement.getValue().contains("1"));//set to true or false
                Helper_Controller.debugMessage("IO value found "+bVal);

            } else {
                bVal = false;
            }
            retVector[index] = bVal;
        }

        return retVector;
    }

    /**
     *
     * @param fileName
     * @param GAretes
     * @param GSProbe
     * @param GEProbe
     * @param GNode
     */
    public void saveToXML(String fileName, HashMap<Integer, Wire_Model> GAretes, HashMap<Integer, Probe_Start_Model> GSProbe, HashMap<Integer, Probe_End_Model> GEProbe, HashMap<Integer, Node_Model_Abstract> GNode) {
        System.out.println("Starting to save to xml");
        Element root = new Element(LABEL_ROOT);
        Document doc = new Document(root);
        saveWire(root, GAretes);
        saveProbe(root, GSProbe, GEProbe);
        saveNode(root, GNode);
        try {
            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
            sortie.output(doc, new FileOutputStream(fileName));
            Helper_Controller.debugMessage0("XML saved to path :"+fileName);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param root
     * @param GAretes
     */
    private void saveWire(Element root, HashMap<Integer, Wire_Model> GAretes) {
        Element tempElement;
        Wire_Model tempWire;
        Set entrySet = GAretes.entrySet();
        Map.Entry element;
        Iterator it = entrySet.iterator();
        while (it.hasNext()) {
            tempElement = new Element(LABEL_WIRE);
            element = (Map.Entry) it.next();
            tempWire = (Wire_Model) element.getValue();

            set_basic_attributes(tempWire, tempElement);
            add_io_ellements(tempWire, tempElement);
            tempElement.setAttribute(ATTRIBUTE_SIZE,String.valueOf(tempWire.getSizeBus()));
            root.addContent(tempElement);
        }
    }

    /**
     *
     * @param root
     * @param GSProbe
     * @param GEProbe
     */
    private void saveProbe(Element root, HashMap<Integer, Probe_Start_Model> GSProbe, HashMap<Integer, Probe_End_Model> GEProbe) {
        Element tempElement;
        Probe_Model_Abstract probe;
        Map.Entry ellem;
        Set sentrySet = GSProbe.entrySet();
        Set eentrySet = GEProbe.entrySet();
        Iterator sit = sentrySet.iterator();
        Iterator eit = eentrySet.iterator();
        while (sit.hasNext() || eit.hasNext()) {
            tempElement = new Element(LABEL_PROBE);
            if (sit.hasNext()) {
                ellem = (Map.Entry) sit.next();
                probe = (Probe_Model_Abstract) ellem.getValue();
            } else {
                ellem = (Map.Entry) eit.next();
                probe = (Probe_Model_Abstract) ellem.getValue();
            }
            set_basic_attributes(probe, tempElement);
            //set connected wires
            Helper_Controller.debugMessage("Listing Probe connected wire");
            connectedWire(LABEL_WIRE, probe.getWire(), tempElement);
            root.addContent(tempElement);
        }
    }

    /**
     *
     * @param root
     * @param GNode
     */
    private void saveNode(Element root, HashMap<Integer, Node_Model_Abstract> GNode) {
        Element tempElement, wireInElement, wireOutElement;
        Node_Model_Abstract node;
        Set entrySet = GNode.entrySet();
        Map.Entry ellem;
        Iterator it = entrySet.iterator();
        while (it.hasNext()) {
            tempElement = new Element(LABEL_NODE);
            ellem = (Map.Entry) it.next();
            node = (Node_Model_Abstract) ellem.getValue();
            set_basic_attributes((Object_Model_Abstract) node, tempElement);
            System.out.println("Listing wires in size "+node.getInput().size());
            connectedWire(LABEL_WIRE_IN, node.getInput(), tempElement);
            System.out.println("Listing wires out"+node.getOutput().size());
            connectedWire(LABEL_WIRE_OUT, node.getOutput(), tempElement);
            switch (node.getType()) {
                case Global_Defines_Abstract.TYPE_LOGIC_ALU:
                case Global_Defines_Abstract.TYPE_LOGIC_DEMUX:
                case Global_Defines_Abstract.TYPE_LOGIC_MUX:
                    Logic_Model_Abstract logic = (Logic_Model_Abstract) node;
                    break;
                case Global_Defines_Abstract.TYPE_REG_PC:
                case Global_Defines_Abstract.TYPE_REG_DATA:
                case Global_Defines_Abstract.TYPE_REG_TEXT:
                    Register_Model_Abstract register = (Register_Model_Abstract) node;
                    System.out.println("file path "+register.getFilePath());
                    System.out.println("file name "+register.getFileName());
                    tempElement.setAttribute(ATTRIBUTE_FILE_PATH, register.getFilePath());
                    tempElement.setAttribute(ATTRIBUTE_FILE_NAME, register.getFileName());
                    tempElement.setAttribute(ATTRIBUTE_MEMORY_BLOCK_SIZE, String.valueOf(register.getBlockCount()));
                    tempElement.setAttribute(ATTRIBUTE_NUMBER_MEMORY_BLOCKS, String.valueOf(register.getBlockSize()));
            }
            root.addContent(tempElement);
        }
    }

    /**
     *
     * @param lable
     * @param connected
     * @param motherelem
     */
    private void connectedWire(String lable, Vector<Wire_Model> connected, Element motherelem) {
        Element tmpelem;
        for (Wire_Model wire : connected) {
            tmpelem = new Element(lable);
            tmpelem.setAttribute(ATTRIBUTE_ID, String.valueOf(wire.getId()));
            //add content to mother node
            motherelem.addContent(tmpelem);
            /*debug
            Element testroot = new Element("debug");
            Document nvdoc = new Document(testroot);
            testroot.setContent(tmpelem);
            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            try {
                xmlOutputter.output(nvdoc, System.out);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("found connected wire["+wire.getId()+"]"+" type "+lable+" size "+wire.getSizeBus());
        */
        }
    }

    /**
     *
     * @param wire
     * @param motherelem
     */
    private void add_io_ellements(Wire_Model wire, Element motherelem) {

        boolean[] data = wire.getData();
        for (boolean b : data) {
            Element io_elem = new Element(LABEL_IO);
            if (b == true) {
                io_elem.setText("1");
            } else {
                io_elem.setText("0");
            }
            motherelem.addContent(io_elem);
        }
    }

    /**
     *
     * @param object
     * @param elem
     */
    private void set_basic_attributes(Object_Model_Abstract object, Element elem) {
        //set attributes id / type
        elem.setAttribute(ATTRIBUTE_ID, String.valueOf(object.getId()));
        elem.setAttribute(ATTRIBUTE_TYPE, String.valueOf(object.getType()));
    }
}
