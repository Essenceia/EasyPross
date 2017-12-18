package easypross.Model.Probe;

import easypross.Controller.HelperController;
import easypross.Model.AbstractClasses.ProbeModel_Abstract;
import easypross.Model.Wire.WireModel;
import java.util.Arrays;
import java.util.Vector;

public class ProbeStartModel extends ProbeModel_Abstract{
    /**
     * Constructor
     * @param id
     * @param type
     * @param description
     * @param link_wire the wire the probe is connected to
     */
    public ProbeStartModel(int next_id, Vector<WireModel> wire_out) {
        super(next_id,4,"Start Size of Data "+this.getWire_size().toString(),wire_out);
        reset_values();
    }

    @Override
    public void reset_values() {
        super.reset_values();
        Arrays.fill(data,false);
        HelperController.put_buffer_data_in_wire(wire,data);
    }

    public void setData(boolean[] new_data) {
        //tranfert new data to probe
        if(new_data.length == data.length){
            data = new_data;
        }else{
            System.out.println("Erreur tailles incompatibles");
        }



    }
}
