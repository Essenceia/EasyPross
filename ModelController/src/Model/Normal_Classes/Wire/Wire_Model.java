package Model.Normal_Classes.Wire;

import Model.Abstract_Classes.Object_Model_Abstract;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Wire_Model extends Object_Model_Abstract implements Interface.Object_Interface{

    private int sizeBus;
    private boolean[] data;
    private boolean active;

    /**
     *
     * @param id
     * @param sizeBus
     */
    public Wire_Model(int id, int sizeBus) {
        super(id, TYPE_WIRE, "Wire width " + sizeBus);
        if (sizeBus > 0) {
            this.sizeBus = sizeBus;
        } else {
            this.sizeBus = 1;
            System.out.println("Erreur : Wire(), size of the bus must > 0");
        }
        this.description += "Data bus " + Integer.toString(sizeBus) + "bits";
        this.data = new boolean[this.sizeBus];
        resetValues();
    }

    /**
     *
     * @return
     */
    public int getSizeBus() {
        return sizeBus;
    }

    /**
     *
     * @param sizeBus
     */
    public void setSizeBus(int sizeBus) {
        this.sizeBus = sizeBus;
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
        this.data = data;
    }

    /**
     *
     * @return
     */
    public boolean getActive() {
        return active;
    }

    /**
     *
     * @param active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void putDataAtIndex(boolean d, int index) {
        if (index >= sizeBus) {
            System.out.println("Erreur index out of range wire");
        } else {
            data[index] = d;
        }
    }

    @Override
    public final void resetValues() {

        Arrays.fill(data,false);
    }
}
