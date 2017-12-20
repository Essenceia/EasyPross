package Model.Probe;

import Controller.HelperController;
import Model.AbstractClasses.ProbeModel_Abstract;
import Model.Wire.WireModel;

import java.util.Arrays;
import java.util.Vector;

/**
 * \class ProbeStartModel ProbeStartModel.java "Model/Probe/ProbeStartModel.java"
 * \brief Entry point of our graph.
 * <p>
 * \details This probe is the entry point for the graph, the data is recived by the API and transfered to the connected
 * lines.
 */
public class ProbeStartModel extends ProbeModel_Abstract {
    /**
     * Constructor
     *\fn ProbeStartMode
     * @param id
     * @param link_wire   the wire the probe is connected to
     */
    public ProbeStartModel(int next_id, Vector<WireModel> wire_out) {
        super(next_id, 4, "Start Size of Data " + this.getWire_size().toString(), wire_out);
        reset_values();
    }

    /**
     * \fn reset_values
     * \brief Override, transfers data in probe buffer ( this.data ) to the connected wire
     */
    @Override
    public void reset_values() {
        super.reset_values();
        HelperController.put_buffer_data_in_wire(wire, data);
    }

    /**
     * \fn setData
     * \brief Override, transfers data in probe buffer ( this.data ) to the connected wire.
     */
    public void setData(boolean[] new_data) {
        //tranfert new data to probe
        if (new_data.length == data.length) {
            data = new_data;
        } else {
            System.out.println("Erreur tailles incompatibles");
        }


    }
}