package easypross.View;

public class NodeView {
    protected String name;
    protected int numberInputs;
    protected int numberOutputs;
    protected int posX;
    protected int posY;
    protected int id;
    /**
     * Constructor
     * @param name
     * @param numberInputs
     * @param numberOutputs
     * @param posX
     * @param posY
     * @param id 
     */
    public NodeView(String name, int numberInputs, int numberOutputs, int posX, int posY, int id) {
        this.name = name;
        this.numberInputs = numberInputs;
        this.numberOutputs = numberOutputs;
        this.posX = posX;
        this.posY = posY;
        this.id = id;
    }
    /// Getters and Setters \\\
    /**
     * getter Name
     * @return name
     */
    public String getName() {
        return name;
    }
    /**
     * setter Name
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * getter NumberInputs
     * @return numberInputs
     */
    public int getNumberInputs() {
        return numberInputs;
    }
    /**
     * setter NumberInputs
     * @param numberInputs 
     */
    public void setNumberInputs(int numberInputs) {
        this.numberInputs = numberInputs;
    }
    /**
     * getter NumberOutputs
     * @return numberOutputs
     */
    public int getNumberOutputs() {
        return numberOutputs;
    }
    /**
     * setter numberOutputs
     * @param numberOutputs 
     */
    public void setNumberOutputs(int numberOutputs) {
        this.numberOutputs = numberOutputs;
    }
    /**
     * getter PosX
     * @return posX
     */
    public int getPosX() {
        return posX;
    }
    /**
     * setter PosX
     * @param posX 
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }
    /**
     * getter PosY
     * @return posY
     */
    public int getPosY() {
        return posY;
    }
    /**
     * setter PosY
     * @param posY 
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }
    /**
     * getter ID
     * @return id
     */
    public int getId() {
        return id;
    }
    /**
     * setter ID
     * @param id 
     */
    public void setId(int id) {
        this.id = id;
    }
}
