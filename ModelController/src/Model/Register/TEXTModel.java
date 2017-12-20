package Model.Register;

import Model.AbstractClasses.RegisterModel_Abstract;
import Model.Wire.WireModel;

import java.util.Vector;
/**
 * TEXTModel TEXTModel.java Model/Register/TEXTModel.java
 * <p>
 * This class represents the a Instruction register (Programme in assembly) in our simulation.
 *
 * Wire that comme in carry the data about how this node is going to act.
 * 1st wire ( 1 bit )- information about the operating mode if 0 do nothing, if 1 read incoming block
 * address on wire 2 and wire it as output on our output wire.
 * 2nd wire ( size of PC ) - addresse ( block number ) to be read from our working file ( programme
 * memory).
 * <p>
 * 1st wire ( size of instruction ) - Wire that comes out carries the current value of our PC
 */
public class TEXTModel extends RegisterModel_Abstract {
    /**
     *Constructor
     *
     * This creats a new instance of a TextModel ( a.k.a a programme memory )
     * @param id
     * @param wire_input
     * @param wire_output
     * @param absPath
     * @param fname
     * @param block_size
     * @param block_number
     */
    public TEXTModel(int id, Vector<WireModel> wire_input, Vector<WireModel> wire_output ,
                     String absPath, String fname, int block_size, int block_number){
        super(id,6,wire_input,wire_output,"TEXT(list of instructions) ",absPath,fname,block_number,block_size);
    }

    /**
     * action
     *
     * In action, if our object is marked as activated ( information presnet on the
     * first incomming wire ) we will reform a get on the instruction present in the
     * data memory at the address specified in our 2nd wire.
     */
    @Override
    public void action() {

    }
}
