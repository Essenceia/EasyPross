package easypross.Model.Logic.Unit;

import easypross.Model.AbstractClasses.LogicUnitModel_Abstract;

public class MUXModel extends LogicUnitModel_Abstract {
    /**
     * Constructor
     * @param lock
     * @param data_in
     * @param data_out
     * @param id
     * @param type
     * @param description 
     */
    public MUXModel(String lock, boolean[] data_in, boolean[] data_out, int id, int type, String description) {
        super(lock, data_in, data_out, id, type, description);
    }
    //Override of interface methods of Node_Interface
    /**
     * Override action from Node_Interface
     */
    @Override
    public void action(){
        
    }
}
