package Model.Logic.Gate;

import Controller.HelperController;
import Model.AbstractClasses.LogicGateModel_Abstract;
import Model.Wire.WireModel;
import java.util.Vector;

public class ORModel extends LogicGateModel_Abstract {

    /**
     * Constructeur
     *
     * @param synchrone
     * @param id
     * @param type
     * @param description
     * @param wire_input
     * @param wire_output
     */
    public ORModel(boolean synchrone, int id, int type, String description, Vector<WireModel> wire_input, Vector<WireModel> wire_output) {
        super(synchrone, id, type, description, wire_input, wire_output);
        description += " OU ";
        type = 3;
    }

    //Override of interface methods of NodeInterface
    /**
     * Override action from NodeInterface
     */
    @Override
    public void action() {
        boolean b = false;
        get_incomming_data();
        // add all bits

        for (boolean bool : data_in) {
            b |= bool;
        }
        data_out = HelperController.set_all_to(b, data_out.length);
        put_outputing_data();
    }
}
