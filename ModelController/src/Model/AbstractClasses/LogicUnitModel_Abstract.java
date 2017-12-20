package Model.AbstractClasses;

import Model.Wire.WireModel;
import java.util.Vector;

public abstract class LogicUnitModel_Abstract extends LogicModel_Abstract{
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
    public LogicUnitModel_Abstract(boolean synchrone,int id, int type, String description, Vector<WireModel> wire_input, Vector<WireModel> wire_output) {
        super(synchrone,id, type, description,wire_input, wire_output);
    }
}
