package Model.Register;

import Model.AbstractClasses.RegisterModel_Abstract;
import Model.Wire.WireModel;

import java.util.Vector;

/**
 * DATAModel DATAModel.java Model/Register/DATAModel.java
 * <p>
 * This class represents the a register block in our simulation. Eatch block represents a register
 * and the block size the size size of eatch ot thows variables.
 * <p>
 * Wire that comme in carry the data about how this node is going to act.
 * 1st wire ( 1 bit )- information about the operating mode if 0 we are in read, if 1 we are in writh
 * 2nd wire ( log2 number of blocks ) - addresse of the block we are to read/write
 * 3rd wire ( size of block bits ) - data that is to be writen if we are in withing mode
 * <p>
 * Wire that comes out carries the information that has been written form the register
 */
public class DATAModel extends RegisterModel_Abstract{
    /**
     * Constructor
     * <p>
     *  This constructor intialises the DATAModel
     * @param id
     * @param wire_input
     * @param wire_output
     * @param absPath
     * @param fname
     * @param block_size
     * @param block_number
     */
    public DATAModel(int id,Vector<WireModel> wire_input, Vector<WireModel> wire_output ,
                     String absPath, String fname, int block_size, int block_number){
        super(id,4,wire_input,wire_output,"Data ",absPath,fname,block_number,block_size);
    }

    /**
     * action
     * <p>
     * This action will eater perform a read or a write on our work data in our text file,
     * according to the value of the commande we have recived on our first in wire.
     * <p>
     * The seconde in wire gives us the adresse of the data block that has to be read/witen.
     * <p>
     * The third wire give us the data that is to be writen to the register if we are in write mode
     * <p>
     * The out wire will recive our data if the data is read from the register.
     */
    @Override
    public void action() {
        //todo implement
    }
}
