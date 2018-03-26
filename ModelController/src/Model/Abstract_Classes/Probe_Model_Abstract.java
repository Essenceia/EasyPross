package Model.Abstract_Classes;

import Controller.Helper_Controller;
import Interface.Object_Interface;
import Model.Normal_Classes.Wire.Wire_Model;
import java.util.Arrays;
import java.util.Vector;

public abstract class Probe_Model_Abstract extends Object_Model_Abstract {

    protected Integer wireSize;
    protected Vector<Wire_Model> wire;
    protected boolean[] data;

    /**
     *
     * @param nextId
     * @param type
     * @param description
     * @param linkWire
     */
    public Probe_Model_Abstract(int nextId, int type, String description, Vector<Wire_Model> linkWire) {
        super(nextId, type, description);
        description += "Probe ";
        wire = linkWire;
        wireSize = Helper_Controller.countWireSize(linkWire);
        data = new boolean[wireSize];
    }

    /**
     *
     * @return
     */
    public Integer getWireSize() {
        return wireSize;
    }

    /**
     *
     * @param wireSize
     */
    public void setWireSize(Integer wireSize) {
        this.wireSize = wireSize;
    }

    /**
     *
     * @return
     */
    public boolean[] getData() {
        return data;
    }

    /**
     *
     * @param data
     */
    public void setData(boolean[] data) {

    }

    /**
     *
     */
    @Override
    public void resetValues() {
        Arrays.fill(data, false);
    }

    public Vector<Wire_Model> getWire() {
        return null;
    }
}
