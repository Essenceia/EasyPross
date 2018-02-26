package Controller;

import Model.AbstractClasses.NodeModel_Abstract;
import Model.GlobalDefines;
import Model.Logic.Gate.NOTModel;
import Model.Logic.Unit.ALUModel;
import Model.Probe.ProbeEndModel;
import Model.Probe.ProbeStartModel;
import Model.Wire.WireModel;
import org.jdom2.Attribute;
import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.Element;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class XMLManager {

    private org.jdom2.Document document;
    public Element racine;
    private List wire;
    private List node;
    private List probe;

    protected static final String LABEL_ROOT = "shemas";

    public XMLManager(){
        racine = new Element(LABEL_ROOT);
    }

    public void save_and_print(String file_name){
        document = new Document(racine);
        affiche();
        enregistre(file_name);
    }
    private void affiche()
    {
        try
        {
            //On utilise ici un affichage classique avec getPrettyFormat()
            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
            sortie.output(document, System.out);
        }
        catch (java.io.IOException e){
            System.out.println(e);
        }
    }

    private void enregistre(String fichier)
    {
        try
        {
            //On utilise ici un affichage classique avec getPrettyFormat()
            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
            //Remarquez qu'il suffit simplement de créer une instance de FileOutputStream
            //avec en argument le nom du fichier pour effectuer la sérialisation.
            sortie.output(document, new FileOutputStream(fichier));
        }
        catch (java.io.IOException e){}
    }

    public void open_file(String file_name){
        SAXBuilder sxb = new SAXBuilder();
        try
        {
            //read file
            document = sxb.build(new File(file_name));
        }
        catch(Exception e){}

        //get base ellement of our xml tree
        racine = document.getRootElement();

    }

    public void parse_graph(HashMap<Integer, WireModel> GArrettes,HashMap<Integer, ProbeStartModel> GSProbe,HashMap<Integer,ProbeEndModel> GEProbe,HashMap<Integer, NodeModel_Abstract> GNode){
        //On crée une List contenant tous les noeuds "etudiant" de l'Element racine
        this.node = racine.getChildren("node");
        this.wire = racine.getChildren("wire");
        this.probe = racine.getChildren("probe");

        //must parse wire first next objects depend on it
        parse_wire(GArrettes);
        parse_probe(GSProbe,GEProbe,GArrettes);
        parse_node(GNode,GArrettes);


    }
    private Integer get_attribute(Element curent,String attr_name) {
        Attribute attri = curent.getAttribute(attr_name);
        Integer value = 0;
        try{
            value = attri.getIntValue();
        }catch (DataConversionException e) {
            e.printStackTrace();
        }
        return value;
    }
    private void parse_wire(HashMap<Integer, WireModel> GArrettes){
        //On crée un Iterator sur notre liste
        Iterator i = wire.iterator();
        WireModel nv_wire;
        Integer id, size;
        while(i.hasNext())
        {
            //Read all wires
            Element courant = (Element)i.next();
            id = get_attribute(courant,"id");
            size = get_attribute(courant, "size");
            nv_wire = new WireModel(id, size);
            //set values
            nv_wire.setData(get_IO_values(courant,size));
            //add to hash map
            GArrettes.put(id,nv_wire);
            //print the currently added ellement
            System.out.println("Added to graph : wire - id : "+id.toString());
        }
    }
    private void parse_probe(HashMap<Integer, ProbeStartModel> GSProbe,HashMap<Integer,ProbeEndModel> GEProbe,HashMap<Integer, WireModel> GArrettes) {
        //On crée un Iterator sur notre liste
        Iterator i = probe.iterator();
        Integer id, type;
        Element courant;
        List child_list;
        while (i.hasNext()) {
            //Read all probes
            courant = (Element) i.next();
            id = get_attribute(courant, "id");
            type = get_attribute(courant, "type");
            //get the id of connected wire

            //get the list of children
            child_list = courant.getChildren("wire");

            //select the approporeat probe type
            switch (type) {
                case GlobalDefines.TYPE_PROBE_START:
                    ProbeStartModel s_nv_probe = new ProbeStartModel(id, list_wires(child_list, GArrettes));
                    GSProbe.put(id,s_nv_probe);
                    break;
                case GlobalDefines.TYPE_PROBE_END:
                    ProbeEndModel  e_nv_probe = new ProbeEndModel(id, list_wires(child_list, GArrettes));
                    GEProbe.put(id,e_nv_probe);
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
        Integer id, type;
        //Integer for ALU
        Integer sync, alu_bit_num;
        String object_description;
        Element courant;
        List child_list;
        Vector<WireModel> in_wire;
        Vector<WireModel> out_wire;
        while (i.hasNext()) {
            //Read all probes
            courant = (Element) i.next();
            id = get_attribute(courant, "id");
            type = get_attribute(courant, "type");
            //get the id of connected wire
            //get the list of children
            child_list = courant.getChildren("wire_in");
            in_wire = list_wires(child_list, GArrettes);
            child_list = courant.getChildren("wire_out");
            out_wire = list_wires(child_list, GArrettes);

            //select the approporeat node type
            switch (type) {
                case GlobalDefines.TYPE_LOGIC_ALU:
                    alu_bit_num = get_attribute(courant, "alubitnum");
                    object_description = get_desc(courant);
                    ALUModel nv_probe = new ALUModel(true, in_wire, out_wire, id,
                            type,object_description , alu_bit_num);
                    GNode.put(id,nv_probe);
                    break;
                case GlobalDefines.TYPE_LOGIC_NOT:
                    NOTModel nv_node = new NOTModel(id,in_wire, out_wire,true);
                    break;
                //Todo : add other types of nodes
                default:
                    System.out.println("Error , unknown probe type : " + type.toString());
                    break;
            }

        }
    }
    //get a vector of all the connected wires
    private Vector<WireModel> list_wires(List wire_children,HashMap<Integer, WireModel> GArrettes ){
        Iterator i = wire_children.iterator();
        Element curent;
        Integer w_id;
        Vector<WireModel> tmpvw = new Vector<>();//temoray vector of connected wires
        while (i.hasNext()){
            curent = (Element)i.next();
            w_id = get_attribute(curent, "id");
            //verify if the id exists in the map
            if(!GArrettes.containsKey(w_id)){
                System.out.println("Erreur : pas de wire avec l'id referencer par le probel , id : "+
                        w_id.toString());
            }else {
                tmpvw.add( GArrettes.get(w_id));
            }
        }
        return tmpvw;
    }

    //get text block attribute attached to object if any
    private String get_desc(Element object){
        Attribute attr;
        String nv_string;
        attr = object.getAttribute("description");
        nv_string = attr.toString();
        return nv_string;
    }

    //Read values of io to set default values of bools
    private boolean[] get_IO_values(Element object, Integer size){
        boolean[] ret_vector = new boolean[size];
        Element next_elem;
        List io_list= object.getChildren("io");
        Iterator i = io_list.iterator();
        Boolean bval;
        //iterator over all io ellements
        for(int index = 0 ; index < size ; index++){
            if(i.hasNext()){
                next_elem = (Element)i.next();
                /*
                0 - false
                1 - true
                 */
                bval = ( next_elem.getValue() == "1");//set to true or false
            }else{
                bval = false;
            }
            ret_vector[index]=bval;
        }
        return ret_vector;
    }

}
