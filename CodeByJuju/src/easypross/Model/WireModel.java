package easypross.Model;

import easypross.Model.AbstractClasses.ObjectModel_Abstract;

public class WireModel extends ObjectModel_Abstract {
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
    public WireModel(int sizeBus, boolean[] data, boolean active, int id, int type, String description) {
        super(id, type, description);
        this.sizeBus = sizeBus;
        this.data = data;
        this.active = active;
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
    

}
