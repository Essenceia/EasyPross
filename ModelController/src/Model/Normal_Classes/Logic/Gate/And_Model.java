package Model.Normal_Classes.Logic.Gate;

import Controller.Helper_Controller;
import Interface.Object_Interface;
import Model.Abstract_Classes.Logic_Model_Abstract;
import Model.Normal_Classes.Wire.Wire_Model;
import java.util.Vector;

public class And_Model extends Logic_Model_Abstract implements Object_Interface{

    /**
     *
     * @param id
     * @param wireInput
     * @param wireOutput
     */
    public And_Model(int id, Vector<Wire_Model> wireInput, Vector<Wire_Model> wireOutput) {
        super(id, TYPE_LOGIC_AND, " AND", wireInput, wireOutput);
    }

    /**
     *
     */
    @Override
    public void action() {
        boolean b = false;
        getIncomingData();
        for (boolean bool : dataIn) {
            b &= bool;
        }
        dataOut = Helper_Controller.setAllTo(b, dataOut.length);
        putOutputingData();
    }


    /**
     *
     */
    @Override
    public void resetValues() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
