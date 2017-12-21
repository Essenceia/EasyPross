package Controller;

public class SimplifiedGraphController implements java.io.Serializable {

    /**
     * Attributes : id, description, type, bitSize, input, output
     */
    private int id; //id du module
    private String description; //description du module simplifié 
    private int type; //type si c'est un wire, un probe ou une logic gate par exemple
    private int bitSize; //size du module
    private int[] input; // tableau des entrées 
    private int[] output; //tableau des sorties

    /**
     * Constructor surchargé
     *
     * @param id
     * @param description
     * @param type
     * @param bitSize
     * @param input
     * @param output
     */
    public SimplifiedGraphController(int id, String description, int type, int bitSize, int[] input, int[] output) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.bitSize = bitSize;
        this.input = input;
        this.output = output;
    }

    /**
     * Constructor default
     */
    public SimplifiedGraphController() {
        id = 0;
        description = " ";
        type = 0;
        bitSize = 0;
        input = null;
        output = null;
    }

    /**
     * Getter pour l'Id
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter pour l'id
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter de la description
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter de la description
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter du type
     *
     * @return type
     */
    public int getType() {
        return type;
    }

    /**
     * Setter pour le type
     *
     * @param type
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * Getter pour le bitSize
     *
     * @return bitSize
     */
    public int getBitSize() {
        return bitSize;
    }

    /**
     * Setter pour le bitSize
     *
     * @param bitSize
     */
    public void setBitSize(int bitSize) {
        this.bitSize = bitSize;
    }

    /**
     * Getter pour input
     *
     * @return input
     */
    public int[] getInput() {
        return input;
    }

    /**
     * Setter pour input
     *
     * @param input
     */
    public void setInput(int[] input) {
        input = new int[input.length];
        this.input = input;
    }

    /**
     * Getter pour output
     *
     * @return output
     */
    public int[] getOutput() {
        return output;
    }

    /**
     * Setter pour output
     *
     * @param output
     */
    public void setOutput(int[] output) {
        output = new int[output.length];
        this.output = output;
    }
}
