package Controller;

import Model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public final class Graph_Manager implements Interface.Graph_Manager{
    protected Map<Integer,Wire> GArrettes;
    protected Map<Integer, Node_Base> GNoeuds;
    protected Map<Integer,Probe_Start> GDebut;
    protected Map<Integer,Probe_End> GFin;
    protected Graph_simplifie_objet GSimplified;
    public Graph_Manager() {
        GArrettes = new HashMap<Integer, Wire>();
        GNoeuds = new HashMap<Integer, Node_Base>();
        GDebut = new HashMap<Integer, Probe_Start>();
        GFin = new HashMap<Integer, Probe_End>();
    }
    @Override
    public void load_new_module(String path_to_file) {
        Document_Manager.open(path_to_file);
        create_all_wires();
        while (Document_Manager.hasNextLine()){
            this.read_line(Document_Manager.getNextLine());
        }
        readGNoeuds();

    }

    @Override
    public void init_simplified_graph(String file) {
        GSimplified = new Graph_simplifie_objet(GArrettes,GNoeuds,GDebut,GFin);
        GSimplified.serialise_to_file(file);
    }

    private void create_all_wires(){
        while (Document_Manager.hasNextLine()){
            String read = Document_Manager.getNextLine();
            System.out.println("Line read "+read);
            String[] line = read.split(",");
            System.out.println("Result of split ");
            for (int i = 0; i < line.length; i++) {
                System.out.println("Index "+Integer.toString(i)+" : "+line[i]);
                
            }
            if(Integer.valueOf(line[1]) == 1){
                System.out.println("We have found a wire");
                //we have wire, create
                GArrettes.put(Integer.valueOf(line[0]),
                        this.createWire(line));
            }
        }
        //debug read all wires
        for(Map.Entry<Integer,Wire> mapentry : GArrettes.entrySet()){
            System.out.println(mapentry.getValue().getDescription());
        }
        //reset document line to zero
        Document_Manager.to_begining();
    }
   private void read_line(String toread) {
        Integer type;
        if(toread.length() > 1) {
            System.out.println("Parsing line :"+toread);
            String[] splitByComa = toread.split(",");
            //id=Integer.valueOf(splitByComa[0]);
            type = Integer.valueOf(splitByComa[1]);
            switch (type){
                case 1: System.out.println("Wire already created");
                        
                    break;
                case 2 :case  3:createLGate(splitByComa,type);
                    break;
                case 4:case 5:createProb(splitByComa,type);
                    break;
                default:System.out.println("Error : unknown type "+splitByComa[1]);
            }
        }else {
            System.out.println("Error line to short , line:"+toread);
        }
        }

    private Wire createWire(String[] parsedLine) {
        return new Model.Wire(Integer.valueOf(parsedLine[0])
                ,Integer.valueOf(parsedLine[2]));
    }
    private Vector<Wire> fillById(String[] list){
        Vector<Wire> nvV= new Vector<>();
        Integer tmpId;
        if(list.length ==0){
            System.out.println("Error list of wire lenght is 0 , list is :"+list);
        }else{
            for (String wId:list){
                tmpId = Integer.valueOf(wId);
                if(GArrettes.containsKey(tmpId)){
                    nvV.add(GArrettes.get(tmpId));
                }else{
                    System.out.println("Error no Wire with ID "+wId+" in object "+list[0]);
                }
            }
        }
        return nvV;

    }
    private void createLGate(String[] line,Integer type){
        Integer id = Integer.valueOf(line[0]);
        System.out.println("Incomming wire id list "+line[2]);
        String[] icommingWireID = line[2].split("\\.");
        System.out.println("Outcomming wire id list "+line[3]);
        String[] outcommingWireID = line[3].split("\\.");
        Vector<Wire> in_wire;
        Vector<Wire> out_wire;
        in_wire = fillById(icommingWireID);
        out_wire = fillById(outcommingWireID);
        switch (type){
            case 2:GNoeuds.put(id,new LGate_AND(id,in_wire,out_wire));
            break;
            case 3:GNoeuds.put(id,new LGate_OR(id,in_wire,out_wire));
            break;
        }
    }
    private void createProb(String[] line,Integer type){
        Integer id = Integer.valueOf(line[0]);
        System.out.println("Connected wire id list "+line[2]);
        String[] conectedWireID = line[2].split("\\.");
        Vector<Wire> wire;
        wire = fillById(conectedWireID);
        switch (type){
            case 4:GDebut.put(id,new Probe_Start(id,wire));
                break;
            case 5:GFin.put(id,new Probe_End(id,wire));
                break;
        }
    }

    private void readGNoeuds(){
        for(Map.Entry<Integer,Node_Base> mapentry : GNoeuds.entrySet()){
            System.out.println(mapentry.getValue().getDescription());
        }
    }
    private void readProbs(){
        System.out.println("Prob Start :");
        for(Map.Entry<Integer,Probe_Start> mapentry : GDebut.entrySet()){
            System.out.println(mapentry.getValue().getDescription());
        }
        System.out.println("Prob End :");
        for(Map.Entry<Integer,Probe_Start> mapentry : GDebut.entrySet()){
            System.out.println(mapentry.getValue().getDescription());
        }
    }
    
    public Map<Integer,Wire> getArrete()
    {
        return GArrettes;
    }
    public Map<Integer,Node_Base> getNoeud()
    {
        return GNoeuds;
    }
    public Map<Integer,Probe_Start> getDebut()
    {
        return GDebut;
    }
    public Map<Integer,Probe_End> getFin()
    {
        return GFin;
    }
}
