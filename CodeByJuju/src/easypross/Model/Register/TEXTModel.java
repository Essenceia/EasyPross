package easypross.Model.Register;

import easypross.Model.AbstractClasses.RegisterModel_Abstract;

public class TEXTModel extends RegisterModel_Abstract {
    /**
     * Constructor
     * @param lock
     * @param data_in
     * @param data_out
     * @param id
     * @param type
     * @param description 
     */
    public TEXTModel(String lock, boolean[] data_in, boolean[] data_out, int id, int type, String description) {
        super(lock, data_in, data_out, id, type, description);
    }
    
   
}
