package Model.AbstractClasses;

import Model.Wire.WireModel;
import java.util.Vector;

public abstract class LogicModel_Abstract extends NodeModel_Abstract {
    /**
     * Attributes : synchrone
     */
    protected boolean synchrone;
    /**
     * Constructor
     * @param synchrone
     * @param lock
     * @param data_in
     * @param data_out
     * @param id
     * @param type
     * @param description 
     */
    public LogicModel_Abstract(boolean synchrone, String lock, boolean[] data_in, boolean[] data_out, int id, int type, String description) {
        super(lock, data_in, data_out, id, type, description);
        this.synchrone = synchrone;
    }  
    public LogicModel_Abstract(int id, Vector<WireModel> wire_input, Vector<WireModel> wire_output) {
        super(id, wire_input, wire_output);
        description += " Logic ";
        //check logic nodes can only have 1 bit as output
        if (data_out.length > 1) {
            System.out.println("Error in value of logic wire, cannot be > 1 bit");
        }
    }
}
