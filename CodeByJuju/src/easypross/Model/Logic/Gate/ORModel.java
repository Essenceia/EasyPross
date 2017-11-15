package easypross.Model.Logic.Gate;

import easypross.Model.AbstractClasses.LogicGateModel_Abstract;

public class ORModel extends LogicGateModel_Abstract {
    /**
     * Constructor
     * @param lock
     * @param data_in
     * @param data_out
     * @param id
     * @param type
     * @param description 
     */
    public ORModel(String lock, boolean[] data_in, boolean[] data_out, int id, int type, String description) {
        super(lock, data_in, data_out, id, type, description);
    }
    //Override of interface methods of NodeInterface
    /**
     * Override action from NodeInterface
     */
    @Override
    public void action(){
        
    }
}
