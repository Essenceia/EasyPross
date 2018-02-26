package Model.Logic.Gate;

import Model.AbstractClasses.LogicGateModel_Abstract;
import Model.Wire.WireModel;
import java.util.Vector;

public class NOTModel extends LogicGateModel_Abstract {

    /**
     * 
     * @param id
     * @param wire_input
     * @param wire_output
     * @param syn 
     */
    public NOTModel(int id, Vector<WireModel> wire_input, Vector<WireModel> wire_output,boolean syn) {
        super(id,TYPE_LOGIC_NOT," NOT",wire_input,wire_output,syn);

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
        //put outcomming data into wire
        put_outputing_data();
    }


}
