package Model;

/**
 * GlobalDefines GlobalDefines.java Model/GlobalDefines.java
 *
 * Abstract class used to creat common defines shared across models
 */
public abstract class GlobalDefines {
    /**
     * Types of objects
     */
    protected static final int TYPE_ERR = 0;
    protected static final int TYPE_WIRE = 1;
    protected static final int TYPE_PROBE_START = 2;
    protected static final int TYPE_PROBE_END = 3;
    protected static final int TYPE_REG_DATA = 4;
    protected static final int TYPE_REG_PC = 5;
    protected static final int TYPE_REG_TEXT = 6;
    protected static final int TYPE_LOGIC_MUX= 7;
    protected static final int TYPE_LOGIC_DEMUX = 8;
    protected static final int TYPE_LOGIC_ALU = 9;
    protected static final int TYPE_LOGIC_AND = 10;
    protected static final int TYPE_LOGIC_NOT = 11;
    protected static final int TYPE_LOGIC_OR = 12;

    //TODO implement for logic gates
}
