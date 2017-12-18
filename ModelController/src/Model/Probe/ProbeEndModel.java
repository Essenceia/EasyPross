package Model.Probe;

import Controller.HelperController;
import Model.AbstractClasses.ProbeModel_Abstract;
import Model.Wire.WireModel;
import java.util.Vector;
/**
 * \class ProbeEndModel ProbeEndModel.java "Model/Probe/ProbeEndModel.java"
 * \brief Exit point of our graph.
 * <p>
 * \details This probe is the exit point for the graph, the data is retrived by the API from
 * this point. This probe holds the end values of our simulation.
 */
public class ProbeEndModel extends ProbeModel_Abstract{
    /**
     * Constructor
     *\fn ProbeEndMode
     * @param id
     * @param link_wire   the wire the probe is connected to
     */
    public ProbeEndModel(int next_id, Vector<WireModel> wire_out) {
        super(next_id,5,"End Size of Data "+this.getWire_size().toString(),wire_out);
        reset_values();
    }

    /**
     * \fn getData
     * \brief Get the data from the directly connected wire ans store it in this.data
     * @return this.data
     */
    public boolean[] getData() {
        HelperController.put_wire_data_in_buffer(this.wire,this.data);
        return this.data;
    }


}
