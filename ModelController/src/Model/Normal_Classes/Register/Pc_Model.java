package Model.Normal_Classes.Register;

import Controller.Helper_Controller;
import Model.Abstract_Classes.Register_Model_Abstract;
import Model.Normal_Classes.Wire.Wire_Model;
import java.util.Vector;

public class Pc_Model extends Register_Model_Abstract {



    private static final int PC_VALUE_FILE_INDEX = 0;

    private static final int IN_COMMANDE_READ_WIRE = 0;
    private static final int IN_COMMANDE_WRITE_WIRE = 1;

    private static final int IN_DATA_WIRE = 2;
    private static final int OUT_DATA_WIRE = 0;
    /**
     * Values of commands that we get on the commande wire
     */
    private static final int PC_CODE_SLEEP = 0;/*do nothing*/

    /**
     *
     * @param id
     * @param wireInput
     * @param wireOutput
     * @param absPath
     * @param fileName
     * @param blockSize
     */
    public Pc_Model(int id, Vector<Wire_Model> wireInput, Vector<Wire_Model> wireOutput,
                    String absPath, String fileName, int blockSize) {
        super(id, TYPE_REG_PC, wireInput, wireOutput, "PC ", absPath, fileName, 1, blockSize);
        checkWireNumber(wireInput.size(), wireOutput.size(), 3, 1);
    }

    /**
     *
     */
    @Override
    public void action() {
        //ressources
        int read_commande = boolArrayToInt(this.input.get(IN_COMMANDE_READ_WIRE).getData());
        int write_commande = boolArrayToInt(this.input.get(IN_COMMANDE_READ_WIRE).getData());
        boolean new_data[] ;

        //todo implement
        //blindage
        this.output.get(OUT_DATA_WIRE).setActive(false);
        if (read_commande + write_commande > PC_CODE_SLEEP) {
            //set output wire as active
            this.output.get(OUT_DATA_WIRE).setActive(true);

            //if read
            if (read_commande == IN_COMMANDE_READ_WIRE) {
                boolean[] value = this.input.get(IN_DATA_WIRE).getData();
                new_data = value.clone();
                //check if diffrent
                if (new_data.equals(readFile(PC_VALUE_FILE_INDEX)) == false) {
                    //write change to file
                    writeToFile(PC_VALUE_FILE_INDEX, value);
                }
            }

            //if write to output wire
            if (write_commande == IN_COMMANDE_WRITE_WIRE) {
                //write out data to the exit wire
                this.dataOut = readFile(PC_VALUE_FILE_INDEX);
                Helper_Controller.putWireDataInBuffer(this.output, this.dataOut);
            }
        }
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
