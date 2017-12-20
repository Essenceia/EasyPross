package Model.Register;

import Model.AbstractClasses.RegisterModel_Abstract;
import Model.Wire.WireModel;

import java.util.Vector;
/**
 * PCModel PCModel.java Model/Register/PCModel.java
 * <p>
 * This class represents the a PC (Programme counter) in our simulation.
 * <p>
 * Wire that comme in carry the data about how this node is going to act.
 * 1st wire ( 2 bit )- information about the operating mode if 0 do nothing, if 1 we are in incrementing
 * our PC by the value on our 2nd wire and if 2, we are replacing the value of our PC by the value on our
 * 2nd wire.
 * 2nd wire ( size of block bits ) - data that is to be added/writen to out PC
 * <p>
 * 1st wire ( size of block bits ) - Wire that comes out carries the current value of our PC
 */
public class PCModel extends RegisterModel_Abstract {
    /**
     * Index of the diffrent wires and there roles
     */
    private static final int IN_COMMANDE_WIRE = 0; /*commande wire where we get instruction of what we are to perform on pc*/
    private static final int IN_DATA_WIRE = 1;/*data to modify the pc value*/
    private static final int OUT_DATA_WIRE = 0;/*wire we put our new pc value on*/
    /**
     * Values of commands that we get on the commande wire
     */
    private static final int PC_CODE_SLEEP = 0;/*do nothing*/
    private static final int PC_CODE_INCREMENT = 1;/*increment pc by the value on data wire*/
    private static final int PC_CODE_SET_TO = 2;/*switch out value of pc to the value in data*/
    /**
     * Value of the index at whitch we have set our data in our file
     */
    private static final int PC_VALUE_FILE_INDEX = 0;
    /**
     * Constructor
     *
     * We don't specify a block number because we are only expected to have one value in our
     * PC : the current programme counter.
     * @param id
     * @param wire_input
     * @param wire_output
     * @param absPath
     * @param fname
     * @param block_size
     */
    public PCModel(int id, Vector<WireModel> wire_input, Vector<WireModel> wire_output ,
                   String absPath, String fname, int block_size){
        super(id,TYPE_REG_PC,wire_input,wire_output,"PC ",absPath,fname,1,block_size);
        check_wire_number(wire_input.size(), wire_output.size(), 2, 1);

    }
    //Override of interface methods of NodeInterface
    /**
     * Override action from NodeInterface
     *
     * Acording to the mode specified by input wire 1 we will be : doing nothing / incrementing by a value/
     * rewriting the value of PC , with our 2nd input wire as a basis.
     *
     * Our output wire reflects the current value of our programme counter.
     */
    @Override
    public void action(){
        //ressources
        int pc_commande = boolarray_to_int(this.input.get(IN_COMMANDE_WIRE).getData());
        int new_data =boolarray_to_int(this.input.get(IN_DATA_WIRE).getData());
        int current_pc;
        //todo implement
        //blindage
        this.output.get(OUT_DATA_WIRE).setActive(false);
        if(pc_commande>PC_CODE_SLEEP){
            //set output wire as active
            this.output.get(OUT_DATA_WIRE).setActive(true);

            //modifiy PC value according to commande
            switch (pc_commande){
                case PC_CODE_INCREMENT:
                    current_pc = boolarray_to_int(this.readBlock(PC_VALUE_FILE_INDEX));
                    current_pc+=new_data;
                    break;
                case PC_CODE_SET_TO:
                    current_pc = new_data;
                    break;
                default:System.err.println("Error, unexpected commande on PC , got :"+pc_commande);
                current_pc = 0; // error in PC value
                break;
            }
            System.out.println("New value of PC "+current_pc);
            //wright back value of PC
            this.writeBlock(int_to_boolarray(current_pc,this.block_size),PC_VALUE_FILE_INDEX);

        }else{
            //do nothing we have sleep code
            System.out.println("PC with id "+this.id+" nothing to do, code is to off");
        }
    }
}
