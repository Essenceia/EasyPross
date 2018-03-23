package Model.Normal_Classes.Logic.Unit;

import Controller.Helper_Controller;
import Model.Abstract_Classes.Logic_Model_Abstract;
import Model.Normal_Classes.Wire.Wire_Model;

import java.util.Vector;

public class Alu_Model extends Logic_Model_Abstract {

    private static final int IN_COMMANDE_ALU = 0;
    private static final int IN_DATA_OP1 = 1;
    private static final int IN_DATA_OP2 = 2;

    private static final int OUT_DATA = 0;

    private static final int COMMANDE_SIZE = 3;
    private static final int COMMANDE_CODE_ADD = 0;
    private static final int COMMANDE_CODE_COPY_OP1 = 1;
    private static final int COMMANDE_CODE_SUB= 2;
    private static final int COMMANDE_CHECK_Z = 3;
    private static final int COMMANDE_CODE_COPY_OP2 = 4;
    private static final int COMMANDE_CODE_EQUAL = 5;
    private static final int COMMANDE_CODE_NOT = 6;
    private static final int COMMANDE_CODE_GREATER = 7;



    //todo get value from width of output wire
    private int nbreBitsAlu;
    private boolean Z, N,O;
    private int maxValue;


    /**
     * Constructeur
     *
     * @param wireInput
     * @param wireOutput
     * @param id
     * @param description
     */
    public Alu_Model(Vector<Wire_Model> wireInput, Vector<Wire_Model> wireOutput, int id, String description) {
        super(id, TYPE_LOGIC_ALU, "ALU " + description, wireInput, wireOutput);

        if (input.isEmpty()) {
            System.out.println("Erreur il n'y a pas de fils en entrée");
        }
        Z = false;
        N = false;
        this.nbreBitsAlu = this.input.get(IN_COMMANDE_ALU).getSizeBus();
        if(this.nbreBitsAlu!= COMMANDE_SIZE){
            Helper_Controller.errorMessage("Alu_Model::Alu_Model expected a commande wire of size : "+COMMANDE_SIZE+" found : "+this.nbreBitsAlu);
        }
        int outDataLength = this.output.get(OUT_DATA).getSizeBus();
        double power = outDataLength;//convert to double
        this.maxValue = (int)Math.pow(2,power)-1; // get the maximum value that can be represented on n bits , n beeing the width of the output wire
    }
    /**
     * @param nbreBitsAlu
     */
    public void setNbreBitsAlu(int nbreBitsAlu) {
        this.nbreBitsAlu = nbreBitsAlu;
    }

    /**
     * @return
     */
    public int getNbreBitsAlu() {
        return nbreBitsAlu;
    }
    //Override of interface methods of Node_Interface
    /**
     * Override action from Node_Interface
     */
    /**
     *
     */
    @Override
    public void action() {
        int control = boolArrayToInt(this.input.get(IN_COMMANDE_ALU).getData());
        int dataOP1 = boolArrayToInt(this.input.get(IN_DATA_OP1).getData());
        int dataOP2 = boolArrayToInt(this.input.get(IN_DATA_OP2).getData());
        int result=0;

        Helper_Controller.debugMessage0("Alu_Model::action commande "+control+" op1 : "+dataOP1+" op2 "+dataOP2);
        //act accorfing to control
        if(this.nbreBitsAlu >= 1 && control < 2 ){
            switch (control){
                case COMMANDE_CODE_ADD://case 0
                    result = dataOP1 + dataOP2;
                    break;
                case COMMANDE_CODE_COPY_OP1: //case 1
                    result = dataOP1;
                    break;
                default:Helper_Controller.errorMessage("Alu_Model::action reatched default case on control "+control+" with nbits control "+this.nbreBitsAlu);
            }
        }
        else{
            if(this.nbreBitsAlu >= 2 && control < 4 ){
                switch (control){
                    case COMMANDE_CODE_SUB://case 2
                        result = dataOP1 - dataOP2;
                        break;
                    case COMMANDE_CHECK_Z: //case 3 : if Z is false we wright op1+1 else op1+op2 this is used to creat conditional jumps with pc
                        if(this.Z){
                            result = dataOP1+dataOP2;
                        }else{
                            result = dataOP1+1;
                        }
                        break;
                    default:Helper_Controller.errorMessage("Alu_Model::action reatched default case on control "+control+" with nbits control "+this.nbreBitsAlu);
                }
            }else {
                if(this.nbreBitsAlu >= 3  ){
                    switch (control){
                        case COMMANDE_CODE_COPY_OP2://case 4
                            result =  dataOP2;
                            break;
                        case COMMANDE_CODE_EQUAL: //case 5 : if Z is false we wright op1+1 else op1+op2 this is used to creat conditional jumps with pc
                            if(dataOP1==dataOP2){
                                result = this.maxValue;
                            }else{
                                result = 0;
                            }
                            break;
                        case COMMANDE_CODE_NOT://case 6
                            //we have to work on the boolean values of data to perform this not
                            result = aluNot();
                            break;
                        case COMMANDE_CODE_GREATER://case 7
                            if(dataOP1> dataOP2){
                                result = maxValue;
                            }else{
                                result = 0;
                            }
                            break;
                        default:Helper_Controller.errorMessage("Alu_Model::action reatched default case on control "+control+" with nbits control "+this.nbreBitsAlu);
                    }
                }
            }
        }
        //wright to Z
        if(result!=0)Z=true;
        else Z= false;
        //check if no overflow
        if(result> this.maxValue)O = true;
        else O = false;

        this.dataOut = intToBoolArray( result,this.output.get(OUT_DATA).getSizeBus() );
        //wright data to output
        this.output.get(OUT_DATA).setActive(true);
        Helper_Controller.putBufferDataInWire(this.output.get(OUT_DATA), this.dataOut);

        Helper_Controller.debugMessage0("Alu_Model::action result "+result+" Z : "+Z+" O "+O);

    }
    private int aluNot(){
        boolean[] op1;
        boolean[] notop1;
        int i = 0;
        op1 = this.input.get(IN_DATA_OP1).getData();
        notop1 = new boolean[op1.length];
        for (boolean b: op1){
            notop1[i++] = !b;
        }
        return boolArrayToInt(notop1);
    }


}
