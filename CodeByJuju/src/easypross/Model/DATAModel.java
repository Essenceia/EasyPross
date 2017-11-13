package easypross.Model;


public class DATAModel extends RegisterModel {
    private String fileName;
    private int block_size;
    private int block_count;
    /**
     * Constructor
     * @param id
     * @param type
     * @param description 
     */
    public DATAModel(int id, int type, String description) {
        super(id, type, description);
    }

}
