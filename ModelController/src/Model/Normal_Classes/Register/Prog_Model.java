package Model.Normal_Classes.Register;

import Controller.Helper_Controller;
import Model.Abstract_Classes.Register_Model_Abstract;
import Model.Normal_Classes.Wire.Wire_Model;

import java.util.Vector;

public class Prog_Model extends Register_Model_Abstract {

    /** Control wire */
    private static final int IN_COMMANDE_READ_WIRE = 0;
    /** Input with instruction address */
    private static final int IN_DATA_INSTRUCTION_ADDR = 1;

    private static final int OUT_DATA_WIRE = 0;

    private static final int PROG_CODE_SLEEP = 0;

    /**
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
        checkWireNumber(wireInput.size(), wireOutput.size(), 2, 1);
    }

    /**
     *
     */
    @Override
    public void action() {
        int instAddr;
        int write_commande = boolArrayToInt(this.input.get(IN_COMMANDE_READ_WIRE).getData());
        this.output.get(OUT_DATA_WIRE).setActive(false);
        if (write_commande > PROG_CODE_SLEEP) {
            instAddr = boolArrayToInt(this.input.get(IN_DATA_INSTRUCTION_ADDR).getData());
            this.dataOut = readFile(instAddr);
            Helper_Controller.putBufferDataInWire(this.output.get(OUT_DATA_WIRE), this.dataOut);
        }
    }


    @Override
    public void resetValues() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
