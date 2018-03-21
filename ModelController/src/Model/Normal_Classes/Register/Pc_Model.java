package Model.Normal_Classes.Register;

import Controller.Helper_Controller;
import Model.Abstract_Classes.Register_Model_Abstract;
import Model.Normal_Classes.Wire.Wire_Model;

import java.util.Arrays;
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
        /*String debugMessag ="Pc_Model::Pc_Model wire input " ;
        for ( Wire_Model w_in : wireInput){
            debugMessag+=" ["+w_in.getId()+"] ";
        }
        debugMessag +=" this.input " ;
        for ( Wire_Model w_in : this.input){
            debugMessag+=" ["+w_in.getId()+"] ";
        }
        debugMessag+= " wire output ";
        for ( Wire_Model w_out : wireOutput){
            debugMessag+=" ["+w_out.getId()+"] ";
        }
        debugMessag +=" this.output " ;
        for ( Wire_Model w_in : this.output){
            debugMessag+=" ["+w_in.getId()+"] ";
        }
        Helper_Controller.debugMessage(debugMessag);*/
    }

    /**
     *
     */
    @Override
    public void action() {
        //ressources
        Helper_Controller.debugMessage0("Pc_Model::action called");
        String debugMessag ="Pc_Model::Pc_Modelthis.input " ;
        for ( Wire_Model w_in : this.input){
            debugMessag+=" ["+w_in.getId()+"] ";
        }
        debugMessag +=" this.output " ;
        for ( Wire_Model w_in : this.output){
            debugMessag+=" ["+w_in.getId()+"] ";
        }
        Helper_Controller.debugMessage(debugMessag);
        int read_commande = boolArrayToInt(this.input.get(IN_COMMANDE_READ_WIRE).getData());
        int write_commande = boolArrayToInt(this.input.get(IN_COMMANDE_WRITE_WIRE).getData());
        boolean new_data[] ;

        //todo implement
        //blindage
        this.output.get(OUT_DATA_WIRE).setActive(false);
        if (read_commande + write_commande > PC_CODE_SLEEP) {
            //set output wire as active

            //if read
            if (read_commande != 0) {
                //Helper_Controller.putWireDataInBuffer(this.input.get(IN_DATA_WIRE), this.dataIn);
                this.dataIn=this.input.get(IN_DATA_WIRE).getData();
                //check if diffrent
                if (this.dataIn.equals(readFile(PC_VALUE_FILE_INDEX)) == false) {
                    //write change to file
                    Helper_Controller.debugMessage3("Pc_Model::action value is not equal between internal buffer and wire in  going to be rewriten");
                    debugMessag ="Pc_Model::action on dataIn("+this.dataIn.length+")";
                    for(boolean debugBool : this.dataIn){
                        debugMessag+= debugBool+" ";
                    }
                    Helper_Controller.debugMessage3(debugMessag);
                    writeToFile(PC_VALUE_FILE_INDEX, this.dataIn);
                }else{
                    Helper_Controller.debugMessage3("Pc_Model::action value is equal between internal buffer and wire no need to be rewriten");

                }
            }

            //if write to output wire
            if (write_commande!= 0) {
                //write out data to the exit wire
                this.output.get(OUT_DATA_WIRE).setActive(true);
                this.dataOut = readFile(PC_VALUE_FILE_INDEX);
                Helper_Controller.putBufferDataInWire(this.output.get(OUT_DATA_WIRE), this.dataOut);
                //Helper_Controller.putWireDataInBuffer(this.output, this.dataOut);
            }
        }

        Helper_Controller.debugMessage0("Pc_Model::action finised read commande "+read_commande+"" +
                "write commande "+write_commande);
    }

    @Override
    public void resetValues() {
        Arrays.fill(this.dataIn,false);
        Arrays.fill(this.dataOut,false);
        writeToFile(PC_VALUE_FILE_INDEX,this.dataIn);
        Helper_Controller.debugMessage0("Pc_Model:: data has been reset to zero");
    }

}
