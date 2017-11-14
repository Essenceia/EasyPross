package Controller;

import java.util.Vector;

public abstract class LGate_Base extends Node_Base implements Interface.LGate_Base {
    protected Boolean synchrone;

    public LGate_Base(int id, Vector<Wire> wire_input, Vector<Wire> wire_output) {
        super(id, wire_input, wire_output);
        description += " Logic ";
        //check logic nodes can only have 1 bit as output
        if (data_out.length > 1) {
            System.out.println("Error in value of logic wire, cannot be > 1 bit");
        }
    }

    @Override
    public Boolean getSynchrone() {
        return synchrone;
    }

}
