package Model.Register;

import Controller.HelperController;
import Model.AbstractClasses.RegisterModel_Abstract;
import Model.Wire.WireModel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.Vector;

/**
 * PCModel PCModel.java Model/Register/PCModel.java
 * <p>
 * This class represents the a PC (Programme counter) in our simulation.
 * <p>
 * Wire that comme in carry the data about how this node is going to act. 1st
 * wire ( 2 bit )- information about the operating mode if 0 do nothing, if 1 we
 * are in incrementing our PC by the value on our 2nd wire and if 2, we are
 * replacing the value of our PC by the value on our 2nd wire. 2nd wire ( size
 * of block bits ) - data that is to be added/writen to out PC
 * <p>
 * 1st wire ( size of block bits ) - Wire that comes out carries the current
 * value of our PC
 */
public class PCModel extends RegisterModel_Abstract {

    /**
     * Index of the diffrent wires and there roles
     */
    private static final int IN_COMMANDE_READ_WIRE = 0;
    private static final int IN_COMMANDE_WRITE_WIRE = 1;
    /*commande wire where we get instruction of what we are to perform on pc*/
    private static final int IN_DATA_WIRE = 2;/*data to modify the pc value*/
    private static final int OUT_DATA_WIRE = 3;/*wire we put our new pc value on*/
    /**
     * Values of commands that we get on the commande wire
     */
    private static final int PC_CODE_SLEEP = 0;/*do nothing*/
    private static final int PC_WRITE_COMMANDE = 1;/*if value on write wire = this then write new value*/
    private static final int PC_READ_COMMANDE = 2;/*if value on read wire = this then read value*/
    /**
     * Value of the index at whitch we have set our data in our file
     */
    private static final int PC_VALUE_FILE_INDEX = 0;

    /**
     * Constructor
     * <p>
     * We don't specify a block number because we are only expected to have one
     * value in our PC : the current programme counter.
     *
     * @param id
     * @param wire_input
     * @param wire_output
     * @param absPath
     * @param fname
     * @param block_size
     */
    public PCModel(int id, Vector<WireModel> wire_input, Vector<WireModel> wire_output,
                   String absPath, String fname, int block_size) {
        super(id, TYPE_REG_PC, wire_input, wire_output, "PC ", absPath, fname, 1, block_size);
        check_wire_number(wire_input.size(), wire_output.size(), 3, 1);

    }

    //Override of interface methods of NodeInterface

    /**
     * Override action from NodeInterface
     * <p>
     * Acording to the mode specified by input wire 1 we will be : doing nothing
     * / incrementing by a value/ rewriting the value of PC , with our 2nd input
     * wire as a basis.
     * <p>
     * Our output wire reflects the current value of our programme counter.
     */
    @Override
    public void action() {
        //ressources
        int read_commande = boolarray_to_int(this.input.get(IN_COMMANDE_READ_WIRE).getData());
        int write_commande = boolarray_to_int(this.input.get(IN_COMMANDE_READ_WIRE).getData());
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
                if (new_data.equals(read_file(PC_VALUE_FILE_INDEX)) == false) {
                    //write change to file
                    write_file(PC_VALUE_FILE_INDEX, value);
                }
            }

            //if write to output wire
            if (write_commande == IN_COMMANDE_WRITE_WIRE) {
                //write out data to the exit wire
                this.data_out = read_file(PC_VALUE_FILE_INDEX);
                HelperController.put_buffer_data_in_wire(this.output, this.data_out);
            }
        }
    }


}
