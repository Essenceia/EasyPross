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
    //TODO implement for logic gates
}
