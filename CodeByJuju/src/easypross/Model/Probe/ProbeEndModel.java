package easypross.Model.Probe;

import easypross.Controller.HelperController;
import easypross.Model.AbstractClasses.ProbeModel_Abstract;
import easypross.Model.Wire.WireModel;
import java.util.Vector;

public class ProbeEndModel extends ProbeModel_Abstract{
    /**
     * Constructor
     * @param id
     * @param type
     * @param description 
     */
    public ProbeEndModel(int next_id, Vector<WireModel> wire_out) {
        super(next_id,wire_out);
        this.type =5;
        description+="End Size of Data "+this.getWire_size().toString();
        reset_values();
    }
    //@Override
    public void reset_values() {
        //super.reset_values();
        //get data from wire
       HelperController.put_wire_data_in_buffer(this.wire,this.data);
    }

    //@Override
    public boolean[] getData() {
        HelperController.put_wire_data_in_buffer(this.wire,this.data);
        return this.data;
    }
}
