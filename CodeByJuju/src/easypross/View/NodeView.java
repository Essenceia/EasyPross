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
    

}
