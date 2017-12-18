package Model.AbstractClasses;

import Controller.HelperController;
import Model.Wire.WireModel;
import java.util.Vector;

public abstract class ProbeModel_Abstract extends ObjectModel_Abstract{
    protected Integer wire_size;
    protected Vector<WireModel> wire;
    protected boolean[] data;
    /**
     * Constructor
     * @param id
     * @param type
     * @param description 
     */
    public ProbeModel_Abstract(int next_id,Vector<WireModel> link_wire) {
        super(next_id);
        description +="Probe ";
        wire=link_wire;
        wire_size = HelperController.count_wire_size(link_wire);
        data = new boolean[wire_size];
    }

    public Integer getWire_size() {
        return wire_size;
    }

    public void setWire_size(Integer wire_size) {
        this.wire_size = wire_size;
    }

    public Vector<WireModel> getWire() {
        return wire;
    }

    public void setWire(Vector<WireModel> wire) {
        this.wire = wire;
    }

    public boolean[] getData() {
        return data;
    }

    public void setData(boolean[] data) {
        this.data = data;
    }
    
}
