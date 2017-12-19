package Model.Logic.Gate;

import Model.AbstractClasses.LogicGateModel_Abstract;
import Model.Wire.WireModel;
import java.util.Vector;

public class NOTModel extends LogicGateModel_Abstract {

    /**
     * Constructor
     *
     * @param id
     * @param wire_input
     * @param wire_output
     */
    public NOTModel(int id, Vector<WireModel> wire_input, Vector<WireModel> wire_output) {
        super(id, wire_input, wire_output);
        description += " OU ";
        type = 3;
    }

    /**
     * Override action from NodeInterface
     */
    @Override
    public void action() {
        int i = 0;
        get_incomming_data();
        // add all bits

        for (boolean bool : data_in) {
            data_out[i] = !bool;
            i++;
        }
    }
}
