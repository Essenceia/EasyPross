package easypross.Model.Logic.Gate;

import easypross.Model.AbstractClasses.LogicGateModel_Abstract;
import easypross.Model.Wire.WireModel;
import java.util.Vector;

public class ORModel extends LogicGateModel_Abstract {
    /**
     * Constructor
     * @param lock
     * @param data_in
     * @param data_out
     * @param id
     * @param type
     * @param description 
     */
    public ORModel(int id, Vector<WireModel> wire_input, Vector<WireModel> wire_output){
        super(id,wire_input,wire_output);
        description +=" OU ";
        type = 3;
    }
    //Override of interface methods of NodeInterface
    /**
     * Override action from NodeInterface
     */
    @Override
    public void action(){
        
    }
}
