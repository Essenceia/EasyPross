package Model;

import Controller.Data_Tuple;

import java.util.*;

public class Graph {


    private HashMap<Integer, Wire> wires;
    private HashMap<Integer, Node> nodes;
    private HashMap<Integer, Register> reg;

    private ArrayList<ArrayList<String>> history;
    private ArrayList<String> regName;
    private String idList;
    private Integer time;

    public Graph() {


        wires = new HashMap<>();
        nodes = new HashMap<>();
        reg = new HashMap<>();

        //history = new ArrayList<ArrayList<String>>();
        regName = new ArrayList<>();
        idList = "";
        time = 0;

    }

    public void addReg(Register g) {
        if (!this.reg.containsKey(g.getId())) {
            this.reg.put(g.getId(), g);
        } else {
            System.out.println("Duplicate ID on reg id " + g.getId());
        }
    }

    public void addWire(Wire g) {
        if (!this.wires.containsKey(g.getId())) {

            this.wires.put(g.getId(), g);

            idList += g.getId().toString();
            if (this.wires.size() != 1) idList += ",";

            System.out.println("Wire :: added to graph :  : " + g.toString());

        }
    }

    public void addNode(Node g) {
        if (!this.nodes.containsKey(g.getId())) {
            this.nodes.put(g.getId(), g);
        } else {
            System.out.println("Duplicate ID on node id " + g.getId());
        }
    }

    @Override
    public String toString() {
        String s = "Wire ::\n";
        for (Wire w : this.wires.values()) {
            s += w.toString()+"\n";
        }
        s+="\nRegister :: \n";
        for (Register w : this.reg.values()) {
            s += w.toString()+"\n";
        }
        s+= "Node :: \n";
        for (Node n : this.nodes.values()) {
            s += n.toString()+"\n";
        }
        return s;
    }

    public Iterator getRegisterNodesID() {
        return wires.keySet().iterator();
    }

    public ArrayList<String> getRegisterData(int id) {
        if (this.wires.containsKey(id)) {
            return this.wires.get(id).getValue();
        } else {
            return null;
        }
    }

    public Iterator<Node> getNonRegisterNodes() {

        return this.nodes.values().iterator();
    }

    public Iterator<Register> getDataRegisterNodes() {

        return this.reg.values().iterator();
    }

    public HashMap<Integer, Wire> getWireMap() {
        return wires;
    }

    public Iterator<Wire> getWire() {
        return wires.values().iterator();
    }

    public void initBufferedContent() {

        Integer numWire = this.wires.size();
        int i = 0;
        Wire tmp;
        this.history = new ArrayList<>();
        for (int j = 0; j < numWire; j++) {
            this.history.add(new ArrayList<>());
        }
        this.history.ensureCapacity(numWire);
        Iterator iw = this.wires.values().iterator();
        while (iw.hasNext()) {
            tmp = (Wire) iw.next();

            this.history.get(i).add(tmp.getLast());
            this.regName.add(tmp.getName());
            i++;
        }


    }

    /**
     * Add new wire ellements to buffer
     */
    public void tickUpdateBuffer(Vector<Data_Tuple> newData) {
        int i = 0;
        String nvString = "";

        for (Data_Tuple data : newData) {

            if (this.wires.containsKey(data.getId())) {

                nvString = data.getStringValuesWithoutDot();
                this.wires.get(data.getId()).addValue(nvString);
                history.get(i).add(nvString);
                i++;

            }else{
                System.err.println("Key "+data.getId()+" not contained ");
            }
        }
    }

    public ArrayList<String> getRegName() {
        return regName;
    }

    public ArrayList<ArrayList<String>> getHistory() {
        return history;
    }

    public String getIdList() {
        return idList;
    }
}
