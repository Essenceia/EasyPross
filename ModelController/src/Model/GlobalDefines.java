package Model;

/**
 * GlobalDefines GlobalDefines.java Model/GlobalDefines.java
 *
 * Abstract class used to creat common defines shared across models
 */
public class GlobalDefines {

    /**
     * Types of objects
     */
    public static final int TYPE_ERR = 0;
    public static final int TYPE_WIRE = 1;
    public static final int TYPE_PROBE_START = 2;
    public static final int TYPE_PROBE_END = 3;
    public static final int TYPE_REG_DATA = 4;
    public static final int TYPE_REG_PC = 5;
    public static final int TYPE_REG_TEXT = 6;
    public static final int TYPE_LOGIC_MUX= 7;
    public static final int TYPE_LOGIC_DEMUX = 8;
    public static final int TYPE_LOGIC_ALU = 9;
    public static final int TYPE_LOGIC_AND = 10;
    public static final int TYPE_LOGIC_NOT = 12;
    public static final int TYPE_LOGIC_OR = 11;

    //TODO implement for logic gates
}
