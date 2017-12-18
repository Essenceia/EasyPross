package Controller;

public class SimplifiedGraphController implements java.io.Serializable{
    /**
     * Attributes : id, description, type, bitSize, input, output
     */
    private int id;
    private String description;
    private int type;
    private int bitSize;
    private int[] input;
    private int[] output;
    /**
     * Constructor
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
    public SimplifiedGraphController()
    {
        id=0;
        description=" ";
        type=0;
        bitSize=0;
        input = null;
        output = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getBitSize() {
        return bitSize;
    }

    public void setBitSize(int bitSize) {
        this.bitSize = bitSize;
    }

    public int[] getInput() {
        return input;
    }

    public void setInput(int[] input) {
        input=new int[input.length];
        this.input = input;
    }

    public int[] getOutput() {
        return output;
    }

    public void setOutput(int[] output) {
        output=new int[output.length];
        this.output = output;
    }
}
