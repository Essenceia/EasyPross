package Model.Normal_Classes.Wire;

import Controller.Helper_Controller;
import Model.Abstract_Classes.Object_Model_Abstract;

import java.io.Console;
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
            Helper_Controller.errorMessage("Erreur : Wire(), size of the bus must > 0");
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
        String debugMsg ="Wire_Model["+this.id+"]::getData values : ";
        for(boolean b :data){
            debugMsg+= b +".";
        }
        Helper_Controller.debugMessage(debugMsg);
        return this.data.clone();
    }

    /**
     *
     * @param incommingData
     */
    public void setData(boolean[] incommingData) {
        String debugMsg ="Wire_Model["+this.id+"]::setData values : ";
        for(boolean b :incommingData){
            debugMsg+= b +".";
        }
        debugMsg+= " after set we have on internal data : ";
        this.data = incommingData;
        for(boolean b :this.data){
            debugMsg+= b +".";
        }
        Helper_Controller.debugMessage(debugMsg);
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

    public void putDataAtIndex(boolean d, int index) {
        if (index >= sizeBus) {
            Helper_Controller.errorMessage("Erreur index out of range wire");
        } else {
            this.data[index] = d;
            Helper_Controller.debugMessage("Wire_Model["+this.id+"]::putDataAtIndex data stored at index "+index+
                    " value "+d);
        }
    }

    @Override
    public final void resetValues() {
        Helper_Controller.debugMessage("Wire_Model["+this.id+"]::resetValues called");
        Arrays.fill(data,false);
    }
}
