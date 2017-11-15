package easypross.Model.AbstractClasses;

public abstract class LogicModel_Abstract extends NodeModel_Abstract {
    /**
     * Attributes : synchrone
     */
    protected boolean synchrone;
    /**
     * Constructor
     * @param synchrone
     * @param lock
     * @param data_in
     * @param data_out
     * @param id
     * @param type
     * @param description 
     */
    public LogicModel_Abstract(boolean synchrone, String lock, boolean[] data_in, boolean[] data_out, int id, int type, String description) {
        super(lock, data_in, data_out, id, type, description);
        this.synchrone = synchrone;
    }    
}
