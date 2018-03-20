package Model.Abstract_Classes;

import Model.Normal_Classes.Wire.Wire_Model;
import java.util.Vector;

public abstract class Logic_Model_Abstract extends Node_Model_Abstract {

    /**
     *
     * @param id
     * @param type
     * @param description
     * @param wireInput
     * @param wireOutput
     */
    public Logic_Model_Abstract(int id, int type, String description, Vector<Wire_Model> wireInput, Vector<Wire_Model> wireOutput) {
        super(id, type, " Logic "+description, wireInput, wireOutput);
       /* if (dataOut.length > 1) {
            System.out.println("Error in value of logic wire, cannot be > 1 bit");
        }*/
    }
}
