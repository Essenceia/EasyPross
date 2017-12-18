package Model.Probe;

import Controller.HelperController;
import Model.AbstractClasses.ProbeModel_Abstract;
import Model.Wire.WireModel;
import java.util.Arrays;
import java.util.Vector;

public class ProbeStartModel extends ProbeModel_Abstract{
    /**
     * Constructor
     * @param id
     * @param type
     * @param description 
     */
    public ProbeStartModel(int next_id, Vector<WireModel> wire_out) {
        super(next_id,wire_out);
        this.type =4;
        description+="Start Size of Data "+this.getWire_size().toString();
        reset_values();
    }

    //@Override
    public void reset_values() {
        //super.reset_values();
        Arrays.fill(data,false);
        HelperController.put_buffer_data_in_wire(wire,data);
    }

    //@Override
    public void putData(boolean[] new_data) {
        //tranfert new data to probe
        if(new_data.length == data.length){
            data = new_data;
        }else{
            System.out.println("Erreur tailles incompatibles");
        }

    }
}
