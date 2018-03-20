package easypross.Model.Wire;

import easypross.Model.AbstractClasses.ObjectModel_Abstract;

public class WireModel extends ObjectModel_Abstract implements easypross.Interface.ObjectInterface{
    /**
     * Attributes : sizeBus, data[], active
     */
    private int sizeBus;
    private boolean[] data;
    private boolean active;

    /**
     * Constructor
     *
     * @param sizeBus
     * @param data
     * @param active
     * @param id
     * @param type
     * @param description
     */
    public WireModel(int id, int bus_size) {
        super(id);
        this.type=1;
        if(bus_size > 0)sizeBus = bus_size;
        else {
            sizeBus = 1;
            System.out.println("Erreur : Wire() , bus size doit etre > 0");
        }
        //descriptif
        this.description += "Bus de donne "+Integer.toString(sizeBus)+"bits";
        this.data = new boolean[sizeBus];
        reset_values();
    }
    /**
     * getter sizeBus
     * @return sizeBus
     */
    public int getSizeBus() {
        return sizeBus;
    }
    /**
     * setter sizeBus
     * @param sizeBus 
     */
    public void setSizeBus(int sizeBus) {
        this.sizeBus = sizeBus;
    }
    /**
     * getter Data
     * @return data
     */
    public boolean[] getData() {
        return data;
    }
    /**
     * setter Data
     * @param data 
     */
    public void setData(boolean[] data) {
        this.data = data;
    }
    /**
     * getter Active
     * @return active
     */
    public boolean isActive() {
        return active;
    }
    /**
     * setter Active
     * @param active 
     */
    public void setActive(boolean active) {
        this.active = active;
    }
    /**
     * Methods
     */
    /**
     * putDataAtIndex
     * @param d
     * @param index 
     */
    @Override
    public void putDataAtIndex(boolean d, int index) {
        if(index >= sizeBus){
            System.out.println("Erreur index out of range wire");
        }else{
            data[index] =d;
        }
    }
    @Override
    public void reset_values() {
        //super.resetValues();
        for(int i=0; i < sizeBus ; i++){
            data[0] = false;
        }
    }
}
