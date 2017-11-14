package Controller;

import java.util.Vector;

public class Probe_End extends Node_Probe_Base {
    public Probe_End(int next_id, Vector<Wire> link_wire) {
        super(next_id,link_wire);
        this.type = 5;
        description+="End Size of Data "+this.getBitSize().toString();
        reset_values();
    }

    @Override
    public void reset_values() {
        super.reset_values();
        //get data from wire
       helper.put_wire_data_in_buffer(this.wire,this.data);
    }

    @Override
    public Boolean[] getData() {
        helper.put_wire_data_in_buffer(this.wire,this.data);
        return this.data;
    }
}
