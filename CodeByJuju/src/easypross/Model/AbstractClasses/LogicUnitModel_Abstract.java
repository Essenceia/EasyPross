package easypross.Model.AbstractClasses;

public abstract class LogicUnitModel_Abstract extends LogicModel_Abstract{
    /**
     * Constructor
     * @param lock
     * @param data_in
     * @param data_out
     * @param id
     * @param type
     * @param description 
     */
    public LogicUnitModel_Abstract(String lock, boolean[] data_in, boolean[] data_out, int id, int type, String description) {
        super(true, lock, data_in, data_out, id, type, description);
    }
    
}
