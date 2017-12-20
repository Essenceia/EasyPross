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
 * 3rd wire ( size of block bits ) - data that is to be added/writen to out PC
 * <p>
 * 1st wire ( size of block bits ) - Wire that comes out carries the current value of our PC
 */
public class PCModel extends RegisterModel_Abstract {
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
        super(id,5,wire_input,wire_output,"PC ",absPath,fname,1,block_size);
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
        //todo implement
    }
}
