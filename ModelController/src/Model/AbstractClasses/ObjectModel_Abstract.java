package Model.AbstractClasses;

import Model.GlobalDefines;

/** \class ObjectModel_Abstract ObjectModel_Abstract.java "Model/AbstractClasses/ObjectModel_Abstract.java"
 *  \brief This is the basis for all of the objects of the simulator.
 *
 * It declares the basic attributes that all of the objects must have in our processor simulator for it to
 * function correctly this includes : id, type and a descritption.
 */
public abstract class ObjectModel_Abstract extends GlobalDefines{
    /**
     * Attibutes : id, type, description
     */
    protected int id; /***< Unique identifier of the attribute, used to address the object. */
    protected int type;/***< Type of the object in the simulation , eg: wire, AND gate, ect ...*/
    protected String description;/***< Short textual description of the object will be inistalised during
     construction. */

    /**
     * Constructor, must be called by all child classes.
     * @param id
     * @param type
     * @param description
     */
    public ObjectModel_Abstract(int id, int type, String description) {
        this.id = id;
        this.type = type;
        this.description = "ID "+String.valueOf(id)+" Type "+String.valueOf(type)+" ";
        this.description = description;
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
