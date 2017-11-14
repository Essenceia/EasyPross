package Controller;

import java.util.Vector;

public class LGate_AND extends LGate_Base {

    public LGate_AND(int id, Vector<Wire> wire_input, Vector<Wire> wire_output){
        super(id,wire_input,wire_output);
        description +=" ET ";
        type = 2;
    }

    @Override
    public void action() {
        Boolean b =false;
        get_incomming_data();
        // add all bits

        for (Boolean bool: data_in){
            b &= bool;
        }
        data_out=helper.set_all_to(b,data_out.length);
        put_outputing_data();
    }
}
