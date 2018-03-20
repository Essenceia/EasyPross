package Model.Normal_Classes.Register;

import Model.Abstract_Classes.Register_Model_Abstract;
import Model.Normal_Classes.Wire.Wire_Model;
import java.util.Vector;

public class Data_Model extends Register_Model_Abstract {

    private static final int IN_COMMANDE_WIRE = 0;
    private static final int IN_ADDRESS_WIRE = 1;
    private static final int IN_DATA_WIRE = 2;

    private static final int OUT_DATA_WIRE = 0;

    private static final int DATA_CODE_SLEEP = 0;
    private static final int DATA_CODE_READ = 1;
    private static final int DATA_CODE_WRIGHT = 2;

    private static final int DATA_WIRE_SIZE_COMMANDE = 2;

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
    public Data_Model(int id, Vector<Wire_Model> wireInput, Vector<Wire_Model> wireOutput,
                      String absPath, String fileName, int blockSize, int blockNumber) {
        super(id, TYPE_REG_DATA, wireInput, wireOutput, "Data ", absPath, fileName, blockNumber, blockSize);
        checkWireNumber(wireInput.size(), wireOutput.size(), 3, 1);
    }

    /**
     *
     */
    @Override
    public void action() {

    }

    /**
     *
    @Override
    public void putDataAtIndex(boolean d, int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void resetValues() {
        throw new UnsupportedOperationException("Not supported yet.");
    }*/
}
