package Model.Normal_Classes.Probe;

import Controller.Helper_Controller;
import Model.Abstract_Classes.Probe_Model_Abstract;
import Model.Normal_Classes.Wire.Wire_Model;
import java.util.Vector;

public class Probe_Start_Model extends Probe_Model_Abstract {

    private Vector<Wire_Model> wireOut;

    /**
     *
     * @param nextId
     * @param wireOut
     */
    public Probe_Start_Model(int nextId, Vector<Wire_Model> wireOut) {
        super(nextId, TYPE_PROBE_START, "Start Size of Data " + wireOut.size(), wireOut);
        resetValues();
        this.wireOut = wireOut;
    }

    /**
     *
     */
    @Override
    public final void resetValues() {
        super.resetValues();
        Helper_Controller.putBufferDataInWire(wire, data);
    }

    /**
     *
     * @param newData
     */
    @Override
    public void setData(boolean[] newData) {
        if (newData.length == data.length) {
            data = newData;
        } else {
            System.out.println("Error incompatible lengths");
        }
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

    /**
     *
     * @param d
     * @param index

    @Override
    public void putDataAtIndex(boolean d, int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }*/
}
