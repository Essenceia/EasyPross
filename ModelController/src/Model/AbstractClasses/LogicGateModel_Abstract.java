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
<<<<<<< HEAD
/*<<<<<<< HEAD*/
    
    public LogicGateModel_Abstract(int id,int type,String description, Vector<WireModel> wire_input, Vector<WireModel> wire_output,boolean synchrone) {
        super(synchrone,id,type," Logic "+description,wire_input,wire_output); // initialisation de LogicModel_Abstract
/*=======*/
    /*public LogicGateModel_Abstract(boolean synchrone, int id, int type, String description, Vector<WireModel> wire_input, Vector<WireModel> wire_output) {
        super(synchrone, id, type, description, wire_input, wire_output); // initialisation de LogicModel_Abstract*/
        description += " Logic ";
/*>>>>>>> a703f8b4129d074447d27866fa5adcb6bc789e74*/
=======

    public LogicGateModel_Abstract(int id,int type,String description, Vector<WireModel> wire_input, Vector<WireModel> wire_output,boolean syncrone) {
        super(syncrone,id,type," Logic "+description,wire_input,wire_output); // initialisation de LogicModel_Abstract

>>>>>>> eb7e5744838d33bae8af635f3bec2aa27f182339
        //check logic nodes can only have 1 bit as output
        if (data_out.length > 1) {
            System.out.println("Error in value of logic wire, cannot be > 1 bit");
        }
    }

}
