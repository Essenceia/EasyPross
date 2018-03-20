package Model.Normal_Classes.Logic.Gate;

import Controller.Helper_Controller;
import Model.Abstract_Classes.Logic_Model_Abstract;
import Model.Normal_Classes.Wire.Wire_Model;
import java.util.Vector;

public class Or_Model extends Logic_Model_Abstract {

    /**
     *
     * @param id
     * @param wireInput
     * @param wireOutput
     */
    public Or_Model(int id, Vector<Wire_Model> wireInput, Vector<Wire_Model> wireOutput) {
        super(id, TYPE_LOGIC_OR, " OR", wireInput, wireOutput);
    }

    /**
     *
     */
    @Override
    public void action() {
        boolean b = false;
        getIncomingData();
        for (boolean bool : dataIn) {
            b |= bool;
        }
        dataOut = Helper_Controller.setAllTo(b, dataOut.length);
        putOutputingData();
    }

    /**
     *
     * @param d
     * @param index

    @Override
    public void putDataAtIndex(boolean d, int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    @Override
    public void resetValues() {
        throw new UnsupportedOperationException("Not supported yet.");
    }*/
}
