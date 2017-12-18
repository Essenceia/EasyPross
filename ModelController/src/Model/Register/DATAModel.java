package Model.Register;

import Model.AbstractClasses.RegisterModel_Abstract;

public class DATAModel extends RegisterModel_Abstract{
    /**
     * Constructor
     * @param fileName
     * @param block_size
     * @param block_count
     * @param data_in
     * @param data_out
     * @param id
     * @param type
     * @param description 
     */
    public DATAModel(String fileName, int block_size, int block_count,boolean[] data_in, boolean[] data_out, int id, int type, String description) {
        super(data_in, data_out, id, type, description);
        this.fileName = fileName;
        this.block_size = block_size;
        this.block_count = block_count;
    }
}
