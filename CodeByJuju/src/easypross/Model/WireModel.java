package easypross.Model;

public class WireModel extends ObjectModel {

    private int sizeBus;
    private boolean[] data;
    private boolean active;
    /**
     * Constructor
     * @param id
     * @param type
     * @param description 
     */
    public WireModel(int id, int type, String description) {
        super(id, type, description);
    }

}
