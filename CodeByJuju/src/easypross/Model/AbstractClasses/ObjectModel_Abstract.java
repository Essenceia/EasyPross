package easypross.Model.AbstractClasses;

public abstract class ObjectModel_Abstract {
    /**
     * Attibutes : id, type, description
     */
    protected int id;
    protected int type;
    protected String description;
    /**
     * Constructor
     * @param id
     * @param type
     * @param description 
     */
    public ObjectModel_Abstract(int id, int type, String description) {
        this.id = id;
        this.type = type;
        this.description = description;
    }
    /**
     * Constructor
     * @param id 
     */
    public ObjectModel_Abstract(int id) {
        this.id = id;
    }
    /**
     * getter ID
     * @return id
     */
    public int getId() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.id;
    }
    /**
     * setter ID
     * @param value 
     */
    public void setId(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.id = value;
    }
    /**
     * getter Type
     * @return type
     */
    public int getType() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.type;
    }
    /**
     * setter Type
     * @param value 
     */
    public void setType(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.type = value;
    }
    /**
     * getter Description
     * @return 
     */
    public String getDescription() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.description;
    }
    /**
     * setter Description
     * @param value 
     */
    public void setDescription(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.description = value;
    }

}
