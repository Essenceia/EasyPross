package easypross.Model.AbstractClasses;

import easypross.Controller.HelperController;
import easypross.Interface.NodeInterface;
import easypross.Model.Wire.WireModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public abstract class NodeModel_Abstract extends ObjectModel_Abstract implements NodeInterface{
    /**
     * Attributes : lock (mutex/semaphore), data_in[], data_out[], input & output (List<WireModel>)
     */
    protected String lock;
    protected boolean[] data_in;
    protected boolean[] data_out;
    protected Vector<WireModel> input;
    protected Vector<WireModel> output;
    protected int data_in_size;
    protected int data_out_size;
    /**
     * Constructor Complete
     * @param lock
     * @param data_in
     * @param data_out
     * @param id
     * @param type
     * @param description 
     */
    public NodeModel_Abstract(String lock, boolean[] data_in, boolean[] data_out, int id, int type, String description) {
        super(id, type, description);
        this.lock = lock;
        this.data_in = data_in;
        this.data_out = data_out;
    }
    public NodeModel_Abstract(int id, Vector<WireModel> wire_input, Vector<WireModel> wire_output){
        super(id);
        //descriptif
        description += "Noeud ";
        //taille des bus de donne entrant et sortant
        data_in_size = HelperController.count_wire_size(wire_input);
        data_out_size = HelperController.count_wire_size(wire_output);
        data_in = new boolean[data_in_size];
        data_out = new  boolean[data_out_size];
        //donnes des bus ! l'ordre sera conserver
        input = wire_input;
        output = wire_output;


    }

    /**
     * getter Lock
     * @return 
     */
    ///////LOCK MUST BE A SEMAPHORE\\\\\\\\\\\\\
    public String getLock() {
        return lock;
    }
    /**
     * setter Lock
     * @param lock 
     */
    public void setLock(String lock) {
        this.lock = lock;
    }
    /**
     * getter Data_In
     * @return 
     */
    public boolean[] getData_in() {
        return data_in;
    }
    /**
     * setter Data_In
     * @param data_in 
     */
    public void setData_in(boolean[] data_in) {
        this.data_in = data_in;
    }
    /**
     * getter Data_Out
     * @return 
     */
    public boolean[] getData_out() {
        return data_out;
    }
    /**
     * setter Data_Out
     * @param data_out 
     */
    public void setData_out(boolean[] data_out) {
        this.data_out = data_out;
    }
    /**
     * getter InputList
     * @return 
     */
    public Vector<WireModel> getInput() {
        return input;
    }
    /**
     * setter InputList
     * @param input 
     */
    public void setInput(Vector<WireModel> input) {
        this.input = input;
    }
    /**
     * getter OutputList
     * @return 
     */
    public Vector<WireModel> getOutput() {
        return output;
    }
    /**
     * setter OutputList
     * @param output 
     */
    public void setOutput(Vector<WireModel> output) {
        this.output = output;
    }
    //@Override
    public void get_incomming_data() {
        //recuperer tout les donne vendant et la placer dans les data_in
        data_in= HelperController.put_wire_data_in_buffer(input,data_in);
        data_out= HelperController.put_wire_data_in_buffer(output,data_out);

    }

    //@Override
    public void put_outputing_data() {
        //mettre les donnes dans les wire en sortie
        HelperController.put_wire_data_in_buffer(output,data_out);
    }
}
