package Controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import Model.Register;
import Model.Wire;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

import Model.Graph;
import Model.Node;

public class XMLtoGraph {
    private Graph root;
    private Element xmlroot;
    private HashMap<Integer, Wire> graph_wire;

    public XMLtoGraph(String file) {
        //Create a SAXBuilder to use when reading xml file
        SAXBuilder builder = new SAXBuilder();
        // Create an instance of the xml file
        File xmlFile = new File(file + ".xml");
        System.out.println(xmlFile.toString());
        try {
            //Use XML Java Builder to create elements and attributes
            Document document = (Document) builder.build(xmlFile);
            // Get the first element of the xml file
            xmlroot = document.getRootElement();
            // get all the children elements of the xml file
            List<Element> list = xmlroot.getChildren();
            //Create the first element of the graph
            this.root = new Graph();
            //create all children elements of the graph
            this.goThroughGraph(list);
            //Display the graph
            System.out.println("begin of display of the graph");
            System.out.println(this.root.toString());
            System.out.println("end of display of the graph");

        } catch (IOException io) {
            System.out.println(io.getMessage());
        } catch (JDOMException jdomex) {
            System.out.println(jdomex.getMessage());
        }
    }

    public Graph getRoot() {
        return root;
    }


    /**
     * method to add the elements to the graph
     *
     * @param listElement - list of xml ellements
     * @return
     */
    public Graph goThroughGraph(List<Element> listElement) {

        List xnode;
        List xwire;


        //load all wires
        xnode = this.xmlroot.getChildren("node");
        xwire = this.xmlroot.getChildren("wire");


        parseWire(xwire);
        parseNode(xnode);

        //creat buffers on graph
        this.root.initBufferedContent();

        return this.root;
    }


    private void parseWire(List xwire) {
        Iterator i = xwire.iterator();
        Wire newWire;
        while (i.hasNext()) {

            Element current = (Element) i.next();
            newWire = new Wire(current.getAttributes());
            newWire.addValue(getIOValues(current));
            this.root.addWire(newWire);
        }
    }

    /**
     * I am ashamed of this function ...
     * @param xwire
     */
    private void parseNode(List xwire) {
        graph_wire = this.root.getWireMap();
        Iterator i = xwire.iterator();
        Node newNode;
        Register newRegister;
        Vector<Integer> connectedWires;
        int type;
        while (i.hasNext()) {

            Element current = (Element) i.next();
            //i.remove();
            try {
                type = current.getAttribute("type").getIntValue();

            } catch (DataConversionException e) {
                e.printStackTrace();
                type = 0;
            }
            if (type >= 4 && type <= 6) {
                //this is a register
                newRegister = new Register(current.getAttributes());
                //attache wires
                connectedWires = listConnected(current, "wire_in");
                for (Integer id : connectedWires) {
                    if (graph_wire.containsKey(id)) {
                        newRegister.add(graph_wire.get(id));
                    }

                }
                connectedWires = listConnected(current, "wire_out");
                for (Integer id : connectedWires) {
                    if (graph_wire.containsKey(id)) {
                        newRegister.add(graph_wire.get(id));
                    }

                }
                this.root.addReg(newRegister);
            } else {
                //normal node
                newNode = new Node(current.getAttributes());
                //attache wires
                connectedWires = listConnected(current, "wire_in");
                for (Integer id : connectedWires) {
                    if (graph_wire.containsKey(id)) {
                        newNode.add(graph_wire.get(id));
                    }

                }
                connectedWires = listConnected(current, "wire_out");
                for (Integer id : connectedWires) {
                    if (graph_wire.containsKey(id)) {
                        newNode.add(graph_wire.get(id));
                    }

                }
                this.root.addNode(newNode);
            }

        }
    }

    private String getIOValues(Element object) {
        String retVector = "";
        Element nextElement;
        List io_list = object.getChildren("io");
        Iterator i = io_list.iterator();
        while (i.hasNext()) {
            nextElement = (Element) i.next();
            if(nextElement.getValue().contains("1"))retVector+="1";
            else retVector+="0";
        }
        return retVector;
    }

    private Vector<Integer> listConnected(Element object, String tag) {
        Element nextElement;
        Vector<Integer> idList = new Vector<>();
        Integer id;
        List list = object.getChildren(tag);
        Iterator i = list.iterator();
        while (i.hasNext()) {
            nextElement = (Element) i.next();
            try {
                id = nextElement.getAttribute("id").getIntValue();// id of connected wire
                idList.add(id);
                if(graph_wire.containsKey(id)){
                    graph_wire.get(id).updateAttributes(nextElement.getAttributes());
                }else{
                    System.err.println("Missing wire id "+id);
                }
            } catch (DataConversionException e) {
                e.printStackTrace();
            }
        }
        return idList;
    }


}