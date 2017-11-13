package easypross.Model;

import java.util.ArrayList;
import java.util.List;

public class NodeModel extends ObjectModel {
    protected String lock;
    protected boolean[] data_in;
    protected boolean[] data_out;
    protected List<WireModel> input = new ArrayList<WireModel> ();
    protected List<WireModel> output = new ArrayList<WireModel> ();
    /**
     * Constructor
     * @param id
     * @param type
     * @param description 
     */
    public NodeModel(int id, int type, String description) {
        super(id, type, description);
    }
    /**
     * Constructor Complete
     * @param lock
     * @param data_in
     * @param data_out
     * @param id
     * @param type
     * @param description 
     */
    public NodeModel(String lock, boolean[] data_in, boolean[] data_out, int id, int type, String description) {
        super(id, type, description);
        this.lock = lock;
        this.data_in = data_in;
        this.data_out = data_out;
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
    public List<WireModel> getInput() {
        return input;
    }
    /**
     * setter InputList
     * @param input 
     */
    public void setInput(List<WireModel> input) {
        this.input = input;
    }
    /**
     * getter OutputList
     * @return 
     */
    public List<WireModel> getOutput() {
        return output;
    }
    /**
     * setter OutputList
     * @param output 
     */
    public void setOutput(List<WireModel> output) {
        this.output = output;
    }
    

}
