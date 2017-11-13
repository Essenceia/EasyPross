package easypross.Model;

public class ObjectModel {
    protected int id;
    protected int type;
    protected String description;
    /**
     * Constructor
     * @param id
     * @param type
     * @param description 
     */
    public ObjectModel(int id, int type, String description) {
        this.id = id;
        this.type = type;
        this.description = description;
    }
    /**
     * getter ID
     * @return id
     */
    int getId() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.id;
    }
    /**
     * setter ID
     * @param value 
     */
    void setId(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.id = value;
    }
    /**
     * getter Type
     * @return type
     */
    int getType() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.type;
    }
    /**
     * setter Type
     * @param value 
     */
    void setType(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.type = value;
    }
    /**
     * getter Description
     * @return 
     */
    String getDescription() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.description;
    }
    /**
     * setter Description
     * @param value 
     */
    void setDescription(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.description = value;
    }

}
