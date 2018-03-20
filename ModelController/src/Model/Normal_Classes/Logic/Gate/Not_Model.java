package Model.Normal_Classes.Logic.Gate;

import Controller.Helper_Controller;
import Model.Abstract_Classes.Logic_Model_Abstract;
import Model.Normal_Classes.Wire.Wire_Model;
import java.util.Vector;

public class Not_Model extends Logic_Model_Abstract {

    /**
     *
     * @param id
     * @param wireInput
     * @param wireOutput
     */
    public Not_Model(int id, Vector<Wire_Model> wireInput, Vector<Wire_Model> wireOutput) {
        super(id, TYPE_LOGIC_NOT, " NOT", wireInput, wireOutput);
    }

    /**
     *
     */
    @Override
    public void action() {
        int count = 0;
        getIncomingData();
        for (boolean bool : this.dataIn) {
            if(count < this.dataOut.length)this.dataOut[count] = !bool;
            count++;
        }
        putOutputingData();
    }

    @Override
    public void resetValues() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
