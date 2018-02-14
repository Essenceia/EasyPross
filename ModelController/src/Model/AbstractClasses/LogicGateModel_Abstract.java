package Model.AbstractClasses;

import Model.Wire.WireModel;
import java.util.Vector;

public abstract class LogicGateModel_Abstract extends LogicModel_Abstract {

    /**
     * Constructeur de la classe
     *
     * @param syncrone
     * @param id
     * @param type
     * @param description
     * @param wire_input
     * @param wire_output
     */

    public LogicGateModel_Abstract(int id,int type,String description, Vector<WireModel> wire_input, Vector<WireModel> wire_output,boolean syncrone) {
        super(syncrone,id,type," Logic "+description,wire_input,wire_output); // initialisation de LogicModel_Abstract

        //check logic nodes can only have 1 bit as output
        if (data_out.length > 1) {
            System.out.println("Error in value of logic wire, cannot be > 1 bit");
        }
    }

}
