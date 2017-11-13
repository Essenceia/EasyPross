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
    
    

}
