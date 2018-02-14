package Model.Register;

import Controller.HelperController;
import Model.AbstractClasses.RegisterModel_Abstract;
import Model.Wire.WireModel;

import java.lang.reflect.Array;
import java.util.Vector;

/**
 * TEXTModel TEXTModel.java Model/Register/TEXTModel.java
 * <p>
 * This class represents the a Instruction register (Programme in assembly) in
 * our simulation.
 * <p>
 * Wire that comme in carry the data about how this node is going to act. 1st
 * wire ( 1 bit )- information about the operating mode if 0 do nothing, if 1
 * read incoming block address on wire 2 and wire it as output on our output
 * wire. 2nd wire ( size of PC ) - addresse ( block number ) to be read from our
 * working file ( programme memory).
 * <p>
 * 1st wire ( size of instruction ) - Wire with the instruction at addresse
 * specified on incomming wire 2.
 */
public class TEXTModel extends RegisterModel_Abstract {

    /**
     * Constructor
     * <p>
     * This creats a new instance of a TextModel ( a.k.a a programme memory )
     *
     * @param id
     * @param wire_input
     * @param wire_output
     * @param absPath
     * @param fname
     * @param block_size
     * @param block_number
     */
    public TEXTModel(int id, Vector<WireModel> wire_input, Vector<WireModel> wire_output,
            String absPath, String fname, int block_size, int block_number) {
        super(id, TYPE_REG_TEXT, wire_input, wire_output, "TEXT(list of instructions) ", absPath, fname, block_number, block_size);
        //perform checks on the number of in/out wires
        check_wire_number(wire_input.size(), wire_output.size(), 2, 1);
    }

    /**
     * action
     * <p>
     * In action, if our object is marked as activated ( information present on
     * the first incomming wire ) we will reform a get on the instruction
     * present in the data memory at the address specified in our 2nd wire.
     */
    @Override
    public void action() {
        Array.setBoolean(this.data_out, this.data_out.length, false);
        //protection
        this.output.get(0).setActive(false);
        if (this.input.size() < 2) {
            boolean[] active = this.input.get(0).getData();
            boolean[] adress = this.input.get(1).getData();

            //verification of the size of wire
            if (active.length > 0 & adress.length == this.block_size) {

                //check if the block is to be activated - in wire 1
                if (active[0] == true) {

                    //read address of the next instruction + blindage - in wire 2
                    //get the current instruction to be read in working file
                    //wright back to exit wire
                    this.data_out = this.readBlock(boolarray_to_int(adress));
                    //set exit wire as active
                    this.output.get(0).setActive(true);

                } else {
                    System.out.println("id " + this.id + "Not activated for reading a new intruction");

                }
            } else {
                System.err.println("Error : unexpected size of input wires in text model ");

            }
        }

        //write out data to the exit wire
        HelperController.put_buffer_data_in_wire(this.output,this.data_out);
    }

}
