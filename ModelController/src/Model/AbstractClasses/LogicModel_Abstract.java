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
     *
     * @param synchrone
     * @param data_in
     * @param data_out
     * @param id
     * @param type
     * @param description public LogicModel_Abstract(boolean synchrone,
     * boolean[] data_in, boolean[] data_out, int id, int type, String
     * description) { super(data_in, data_out, id, type, description);
     * this.synchrone = synchrone; }
     */

    /**
     * Constructor
     *
     * @param synchrone
     * @param id
     * @param type
     * @param description
     * @param wire_input
     * @param wire_output
     */
    public LogicModel_Abstract(boolean synchrone, int id, int type, String description, Vector<WireModel> wire_input, Vector<WireModel> wire_output) {
        super(id, type, description, wire_input, wire_output);
        this.synchrone = synchrone;
        description += " Logic "; // Ajout de la description Logic
        //check logic nodes can only have 1 bit as output
        if (data_out.length > 1) {
            System.out.println("Error in value of logic wire, cannot be > 1 bit");
        }
    }
}
