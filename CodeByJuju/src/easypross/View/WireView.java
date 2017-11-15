package easypross.View;

public class WireView {
    private String wireName;
    private int numberBits;
    private int posX;
    private int posY;
    private int graphicalLength;
    private int id;
    /**
     * Constructor
     * @param wireName
     * @param numberBits
     * @param posX
     * @param posY
     * @param graphicalLength
     * @param id 
     */
    public WireView(String wireName, int numberBits, int posX, int posY, int graphicalLength, int id) {
        this.wireName = wireName;
        this.numberBits = numberBits;
        this.posX = posX;
        this.posY = posY;
        this.graphicalLength = graphicalLength;
        this.id = id;
    }
    /// Getters and Setters \\\
    /**
     * getter WireName
     * @return wireName
     */
    public String getWireName() {
        return wireName;
    }
    /**
     * setter WireName
     * @param wireName 
     */
    public void setWireName(String wireName) {
        this.wireName = wireName;
    }
    /**
     * getter NumberBits
     * @return numberBits
     */
    public int getNumberBits() {
        return numberBits;
    }
    /**
     * setter NumberBits
     * @param numberBits 
     */
    public void setNumberBits(int numberBits) {
        this.numberBits = numberBits;
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
     * getter graphicalLength
     * @return graphicalLength
     */
    public int getGraphicalLength() {
        return graphicalLength;
    }
    /**
     * setter GraphicalLength
     * @param graphicalLength 
     */
    public void setGraphicalLength(int graphicalLength) {
        this.graphicalLength = graphicalLength;
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
