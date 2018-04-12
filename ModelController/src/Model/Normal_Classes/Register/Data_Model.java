package Model.Normal_Classes.Register;

import Controller.Helper_Controller;
import Model.Abstract_Classes.Register_Model_Abstract;
import Model.Normal_Classes.Wire.Wire_Model;
import java.util.Vector;

public class Data_Model extends Register_Model_Abstract {

    private static final int IN_COMMANDE_WRITE_WIRE = 0;
    private static final int IN_COMMANDE_READ_WIRE = 1;
    private static final int IN_ADDRESS_OP1 = 2;
    private static final int IN_ADDRESS_OP2 = 3;
    private static final int IN_DATA_WRITE = 4;

    private static final int OUT_DATA_OP1 = 0;
    private static final int OUT_DATA_OP2 = 1;

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
        super(id, TYPE_REG_DATA, wireInput, wireOutput, " Data ", absPath, fileName, blockNumber, blockSize);
        checkWireNumber(wireInput.size(), wireOutput.size(), 5, 2);
    }

    /**
     *
     */
    @Override
    public void action() {
        //read commands
        int op1,op2;
        boolean memoryOp1[],memoryOp2[];
        int read_commande = boolArrayToInt(this.input.get(IN_COMMANDE_READ_WIRE).getData());
        int write_commande = boolArrayToInt(this.input.get(IN_COMMANDE_WRITE_WIRE).getData());
        this.output.get(OUT_DATA_OP1).setActive(false);
        this.output.get(OUT_DATA_OP2).setActive(false);
        Helper_Controller.debugMessage3("Data_Model::action start read commande "+read_commande+" write commande "+write_commande);

        //if write is enabled get data at addresses op1 & op2 and write them onto output wires
        if(write_commande!= 0){

            op1 = boolArrayToInt(this.input.get(IN_ADDRESS_OP1).getData());
            op2 = boolArrayToInt(this.input.get(IN_ADDRESS_OP2).getData());
            memoryOp1 = readFile(op1);
            memoryOp2 = readFile(op2);
            Helper_Controller.debugMessage3("Data_Model::action start write op1 "+op1+" data "+this.boolArrayToInt(memoryOp1) +" op2 "+op2+ " data " + this.boolArrayToInt(memoryOp2));
            //write to ourput wire
            this.output.get(OUT_DATA_OP1).setActive(true);
            this.output.get(OUT_DATA_OP2).setActive(true);
            Helper_Controller.putBufferDataInWire(this.output.get(OUT_DATA_OP1),memoryOp1);
            Helper_Controller.putBufferDataInWire(this.output.get(OUT_DATA_OP2),memoryOp2);
        }
        //if read is enabled get data incomming and address of op1 and write it to internal buffer -> file
        if(read_commande!= 0){
            memoryOp1 = this.input.get(IN_DATA_WRITE).getData();
            op1 = boolArrayToInt(this.input.get(IN_ADDRESS_OP1).getData());
            if(memoryOp1.equals(readFile(op1))==false){//check if we have a change in values
                writeToFile(op1,memoryOp1);
            }
        }
        Helper_Controller.debugMessage3("Data_Model::action end");


    }



}
