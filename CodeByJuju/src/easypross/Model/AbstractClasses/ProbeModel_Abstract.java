package easypross.Model.AbstractClasses;

import easypross.Controller.HelperController;
import easypross.Model.Wire.WireModel;

import java.util.Vector;

/**
 * \class ProbeModel_Abstract ProbeModel_Abstract.java "Model/AbstractClasses/ProbeModel_Abstract.java"
 * \brief Direct child of ObjectModel_Absract and asbtract class from which prob classes inherite.
 * <p>
 * \details Probes are the the inputs and outputs at the expremities of the graph, they are the points
 * where we give our initial data and get the data from at the end of the simulation.
 * <p>
 * \note Addes the necessary attributes for a probe sutch as the width of the connected wire and a buffer
 * for the data on that wire.
 */
public abstract class ProbeModel_Abstract extends ObjectModel_Abstract {
    protected Integer wire_size;
    /***< Size of data wire the probe is connected to */
    protected Vector<WireModel> wire;
    /*** < Wire the probe is connected to */
    protected boolean[] data; /*** < Cache for the data the probe contains, will not change unless signal
     to do so is recived by API. */

    /**
     * Constructor
     *
     * @param id
     * @param type
     * @param description
     * @param link_wire   the wire the probe is connected to
     */
    public ProbeModel_Abstract(int next_id, int type, String description, Vector<WireModel> link_wire) {
        super(next_id, type, description);
        description += "Probe ";
        wire = link_wire;
        wire_size = HelperController.count_wire_size(link_wire);
        data = new boolean[wire_size];
    }

    /**
     * \fn getter wire_size
     *
     * @return wire_size
     */
    public Integer getWire_size() {
        return wire_size;
    }

    /**
     * \fn setter wire_size
     *
     * @return wire_size
     */
    public void setWire_size(Integer wire_size) {
        this.wire_size = wire_size;
    }

    /*
    /note don't think this is necessart - Ja
     */
    /*public Vector<WireModel> getWire() {
        return wire;
    }

    public void setWire(Vector<WireModel> wire) {
        this.wire = wire;
    }
    */
    public boolean[] getData() {
        return data;
    }

    /**
     * \fn setter wire_size
     *
     * @return wire_size
     * \note if setData has not been overwritten you are not supposed to be using it,
     * for instance a end probe is not suposed to be setting the data on it's line,
     * is is supposed to be a read only.
     */
    public void setData(boolean[] data) {
    }

    /**
     * \fn reset_values
     * <p>
     * \note does noting here, needs to be overriden by child classes
     */
    private void reset_values() {

    }

}
