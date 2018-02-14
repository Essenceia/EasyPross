package Model.Register;

import Model.AbstractClasses.RegisterModel_Abstract;
import Model.Wire.WireModel;

import java.util.Vector;

/**
 * DATAModel DATAModel.java Model/Register/DATAModel.java
 * <p>
 * This class represents the a register block in our simulation. Eatch block
 * represents a register and the block size the size size of eatch ot thows
 * variables.
 * <p>
 * Wire that comme in carry the data about how this node is going to act.
 * 1st wire ( 2 bit )- information about the operating mode:
 *  +0 do nothing
 *  +1 we are in read
 *  +2 wight
 * 2nd wire ( log2 number of blocks ) - addresse of the block we are to read/write
 * 3rd wire ( size of block bits ) - data that is to be writen if we are in withing mode
 * <p>
 * Wire that comes out carries the information that has been written form the
 * register
 */
public class DATAModel extends RegisterModel_Abstract {

    /**
     * Index of the diffrent wires and there roles
     */
    private static final int IN_ADDRESS_WIRE = 1;
    /*commande wire where we get the next address we want to interact with on the register block*/
    private static final int IN_COMMANDE_WIRE = 0; /*commande wire where we get instruction of what we are to perform on register*/
    private static final int IN_DATA_WIRE = 2;/*data to modify the register value*/
    private static final int OUT_DATA_WIRE = 0;/*wire we put our read data on register on*/
    /**
     * Values of commands that we get on the commande wire
     */
    private static final int DATA_CODE_SLEEP = 0;/*do nothing*/
    private static final int DATA_CODE_READ = 1;/*read data from register*/
    private static final int DATA_CODE_WRIGHT = 2;/*wright data to register*/
    /**
     * expected size of wires
     */
    private static final int DATA_WIRE_SIZE_COMMANDE = 2;/*Size of

    /**
     * Constructor
     * <p>
     * This constructor intialises the DATAModel
     *
     * @param id
     * @param wire_input
     * @param wire_output
     * @param absPath
     * @param fname
     * @param block_size
     * @param block_number
     */
    public DATAModel(int id, Vector<WireModel> wire_input, Vector<WireModel> wire_output,
            String absPath, String fname, int block_size, int block_number) {
        super(id, TYPE_REG_DATA, wire_input, wire_output, "Data ", absPath, fname, block_number, block_size);
        check_wire_number(wire_input.size(),wire_output.size(),3,1);
    }

    /**
     * action
     * <p>
     * This action will ether perform a read or a write on our work data in our
     * text file, according to the value of the commande we have recived on our
     * first in wire.
     * <p>
     * The seconde in wire gives us the adresse of the data block that has to be
     * read/witen.
     * <p>
     * The third wire give us the data that is to be writen to the register if
     * we are in write mode
     * <p>
     * The out wire will recive our data if the data is read from the register.
     */
    @Override
    public void action() {
        //todo implement

        //read control signal
        //read adresse to modify/read
        //return read block
        //wright new block

    }
}
