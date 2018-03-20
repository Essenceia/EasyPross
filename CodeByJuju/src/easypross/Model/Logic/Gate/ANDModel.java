package easypross.Model.Logic.Gate;

import easypross.Controller.HelperController;
import easypross.Model.AbstractClasses.LogicGateModel_Abstract;
import easypross.Model.Wire.WireModel;
import java.util.Vector;

public class ANDModel extends LogicGateModel_Abstract {
    /**
     * Constructor
     * @param lock
     * @param data_in
     * @param data_out
     * @param id
     * @param type
     * @param description 
     */
    public ANDModel(int id, Vector<WireModel> wire_input, Vector<WireModel> wire_output) {
        super(id,wire_input,wire_output);
        description +=" ET ";
        type = 2;
    }
    //Override of interface methods of Node_Interface
    /**
     * Override action from Node_Interface
     */
    @Override
    public void action() {
        boolean b =false;
        get_incomming_data();
        // add all bits

        for (boolean bool: data_in){
            b &= bool;
        }
        data_out=HelperController.set_all_to(b,data_out.length);
        put_outputing_data();
    }
}
