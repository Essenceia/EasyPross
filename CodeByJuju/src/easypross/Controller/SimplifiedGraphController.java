package easypross.Controller;

public class SimplifiedGraphController {
    /**
     * Attributes : id, description, type, bitSize, input, output
     */
    private int id;
    private String description;
    private int type;
    private int bitSize;
    private boolean[] input;
    private boolean[] output;
    /**
     * Constructor
     * @param id
     * @param description
     * @param type
     * @param bitSize
     * @param input
     * @param output 
     */
    public SimplifiedGraphController(int id, String description, int type, int bitSize, boolean[] input, boolean[] output) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.bitSize = bitSize;
        this.input = input;
        this.output = output;
    }
}
