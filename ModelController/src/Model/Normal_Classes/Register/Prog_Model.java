package Model.Normal_Classes.Register;

import Controller.Helper_Controller;
import Model.Abstract_Classes.Register_Model_Abstract;
import Model.Normal_Classes.Wire.Wire_Model;
import java.lang.reflect.Array;
import java.util.Vector;

public class Prog_Model extends Register_Model_Abstract {

    private static final int IN_COMMANDE_READ_WIRE = 0;
    private static final int IN_COMMANDE_WRITE_WIRE = 1;
    private static final int IN_DATA_WIRE = 2;
    private static final int OUT_DATA_WIRE = 3;
    private static final int PC_CODE_SLEEP = 0;
    private static final int PC_WRITE_COMMANDE = 1;
    private static final int PC_READ_COMMANDE = 2;
    private static final int PC_VALUE_FILE_INDEX = 0;

    /**
     *
     * @param id
     * @param wireInput
     * @param wireOutput
     * @param absPath
     * @param fileName
     * @param blockSize
     * @param blockNumber
     */
    public Prog_Model(int id, Vector<Wire_Model> wireInput, Vector<Wire_Model> wireOutput, String absPath, String fileName, int blockSize, int blockNumber) {
        super(id, TYPE_REG_TEXT, wireInput, wireOutput, "TEXT(list of instructions) ", absPath, fileName, blockNumber, blockSize);
        checkWireNumber(wireInput.size(), wireOutput.size(), 3, 1);
    }

    /**
     *
     */
    @Override
    public void action() {
        int read_commande = boolArrayToInt(this.input.get(IN_COMMANDE_READ_WIRE).getData());
        int write_commande = boolArrayToInt(this.input.get(IN_COMMANDE_READ_WIRE).getData());
        boolean new_data[];
        this.output.get(OUT_DATA_WIRE).setActive(false);
        if (read_commande + write_commande > PC_CODE_SLEEP) {
            this.output.get(OUT_DATA_WIRE).setActive(true);
            if (read_commande == IN_COMMANDE_READ_WIRE) {
                boolean[] value = this.input.get(IN_DATA_WIRE).getData();
                new_data = value.clone();
                if (new_data.equals(readFile(PC_VALUE_FILE_INDEX)) == false) {
                    writeToFile(PC_VALUE_FILE_INDEX, value);
                }
            }
            if (write_commande == IN_COMMANDE_WRITE_WIRE) {
                this.dataOut = readFile(PC_VALUE_FILE_INDEX);
                Helper_Controller.putBufferDataInWire(this.output, this.dataOut);
            }
        }
    }
/*
    @Override
    public void putDataAtIndex(boolean d, int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void resetValues() {
        throw new UnsupportedOperationException("Not supported yet.");
    }*/
}
