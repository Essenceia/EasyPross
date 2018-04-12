package Model;

import Controller.Data_Tuple;
import Controller.Helper_Data_Handler;

import java.util.*;

public class Graph {


    private HashMap<Integer, Wire> wires;
    private HashMap<Integer, Node> nodes;
    private HashMap<Integer, Register> reg;

    private HashMap<Integer,ArrayList<String>> history;
    private HashMap<String,Integer> regName;
    private String idList;
    private Integer time;

    public Graph() {


        wires = new HashMap<>();
        nodes = new HashMap<>();
        reg = new HashMap<>();

        history = new HashMap<Integer,ArrayList<String>>();
        regName = new HashMap<String , Integer>();
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
        time = 0;
        Wire tmp;
        /*this.history = new ArrayList<>();
        for (int j = 0; j < numWire; j++) {
            this.history.add(new ArrayList<>());
        }
        this.history.ensureCapacity(numWire);*/
        System.err.println("Is empty " + this.history.isEmpty());
        Iterator iw = this.wires.values().iterator();
        while (iw.hasNext()) {
            tmp = (Wire) iw.next();
            ArrayList<String> newData = tmp.getValue();
            if (!regName.containsKey(tmp.getName())) {
                this.regName.put(tmp.getName(), tmp.getId());
            }

            if (!history.containsKey(tmp.getId())) {
                this.history.put(tmp.getId(), newData);
            }


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
                if(history.containsKey(data.getId())) {
                    if(history.get(data.getId()).size()<this.time+1) {
                        System.out.println("Writing to wirehisto id"+data.getId()+" time "+time+" size "+history.get(data.getId()).size());
                        history.get(data.getId()).add(nvString);
                    }
                }else{
                    System.err.println("Error unknown id "+data.getId());
                }
                i++;

            }else{
                System.err.println("Key "+data.getId()+" not contained ");
            }
        }
    }

    public HashMap<String, Integer> getRegName() {
        return regName;
    }


    public Integer getTime() {
        return time;
    }

    public HashMap<Integer, ArrayList<String>> getHistory() {
        return history;
    }

    public String getIdList() {
        return idList;
    }
    public void setTimePlus1(){
        time++;
    }
}
