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
     * @param  next_id - unique id of object
     * @param wire_out - the wire the probe is connected to
     */
    public ProbeEndModel(int next_id, Vector<WireModel> wire_out) {
        super(next_id,TYPE_PROBE_END,"End Size of Data "+wire_out.size(),wire_out);
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
