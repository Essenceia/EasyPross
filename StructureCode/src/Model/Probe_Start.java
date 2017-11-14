package Model;

import java.util.Arrays;
import java.util.Vector;

public class Probe_Start extends Node_Probe_Base implements Interface.Probe_Start{
    public Probe_Start(int next_id, Vector<Wire> wire_out) {
        super(next_id,wire_out);
        this.type =4;
        description+="Start Size of Data "+this.getBitSize().toString();
        reset_values();
    }

    @Override
    public void reset_values() {
        super.reset_values();
        Arrays.fill(data,false);
        helper.put_buffer_data_in_wire(this.wire,data);
    }

    @Override
    public void putData(Boolean[] new_data) {
        //tranfert new data to probe
        if(new_data.length == data.length){
            data = new_data;
        }else{
            System.out.println("Erreur tailles incompatibles");
        }

    }
}
