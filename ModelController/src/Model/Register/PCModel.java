package Model.Register;

import Model.AbstractClasses.RegisterModel_Abstract;

public class PCModel extends RegisterModel_Abstract {
    /**
     * Constructor
     * @param data_in
     * @param data_out
     * @param id
     * @param type
     * @param description 
     */
    public PCModel(boolean[] data_in, boolean[] data_out, int id, int type, String description) {
        super(data_in, data_out, id, type, description);
    }
    //Override of interface methods of NodeInterface
    /**
     * Override action from NodeInterface
     */
    @Override
    public void action(){
        
    }
}
