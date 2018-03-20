package Model.Normal_Classes.Probe;

import Controller.Helper_Controller;
import Model.Abstract_Classes.Probe_Model_Abstract;
import Model.Normal_Classes.Wire.Wire_Model;
import java.util.Vector;

public class Probe_End_Model extends Probe_Model_Abstract {

    private Vector<Wire_Model> wireOut;

    /**
     *
     * @param nextId
     * @param wireOut
     */
    public Probe_End_Model(int nextId, Vector<Wire_Model> wireOut) {
        super(nextId, TYPE_PROBE_END, "End Size of Data " + wireOut.size(), wireOut);
        resetValues();
        this.wireOut = wireOut;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean[] getData() {
        Helper_Controller.putWireDataInBuffer(this.wire, this.data);
        return this.data;
    }

    /**
     *
     * @return
     */
    public Vector<Wire_Model> getWire() {
        return wireOut;
    }

    /**
     *
     * @param wireOut
     */
    public void setWire(Vector<Wire_Model> wireOut) {
        this.wireOut = wireOut;
    }


}
