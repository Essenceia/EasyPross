package Model.Normal_Classes.Logic.Unit;

import Interface.Object_Interface;
import Model.Abstract_Classes.Logic_Model_Abstract;
import Model.Normal_Classes.Wire.Wire_Model;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Vector;

public class Mux_Model extends Logic_Model_Abstract implements Object_Interface{

    private static final int IN_COMMANDE_MUX = 0;
    private int nbBitMux;
    private boolean[] instruct;

    /**
     *
     * @param id
     * @param description
     * @param wireInput
     * @param wireOutput
     */
    public Mux_Model( int id, String description, Vector<Wire_Model> wireInput, Vector<Wire_Model> wireOutput) {
        super(id, TYPE_LOGIC_MUX, description, wireInput, wireOutput);
        this.nbBitMux =wireInput.get(IN_COMMANDE_MUX).getSizeBus();

        this.instruct = new boolean[this.nbBitMux];
        Arrays.fill(this.instruct, false);
        if (input.isEmpty()) {
            System.out.println("Error no input wire");
        }
    }

    /**
     *
     */
    @Override
    public void action() {
        getIncomingData();
        switch (nbBitMux) {
            case 2:
                if (instruct[0] == false) {
                    output.get(0).setData(input.get(1).getData());
                } else {
                    output.get(0).setData(input.get(2).getData());
                }
                break;
            case 4:
                if (instruct[0] == false && instruct[1] == false) {
                    output.get(0).setData(input.get(1).getData());
                }
                if (instruct[0] == true && instruct[1] == false) {
                    output.get(0).setData(input.get(2).getData());
                }
                if (instruct[0] == false && instruct[1] == true) {
                    output.get(0).setData(input.get(3).getData());
                }
                if (instruct[0] == true && instruct[1] == true) {
                    output.get(0).setData(input.get(4).getData());
                }
                break;
            case 8:
                if (instruct[0] == false && instruct[1] == false && instruct[2] == false) {
                    output.get(0).setData(input.get(1).getData());
                }
                if (instruct[0] == true && instruct[1] == false && instruct[2] == false) {
                    output.get(0).setData(input.get(2).getData());
                }
                if (instruct[0] == false && instruct[1] == true && instruct[2] == false) {
                    output.get(0).setData(input.get(3).getData());
                }
                if (instruct[0] == true && instruct[1] == true && instruct[2] == false) {
                    output.get(0).setData(input.get(4).getData());
                }
                if (instruct[0] == false && instruct[1] == false && instruct[2] == true) {
                    output.get(0).setData(input.get(5).getData());
                }
                if (instruct[0] == true && instruct[1] == false && instruct[2] == true) {
                    output.get(0).setData(input.get(6).getData());
                }
                if (instruct[0] == false && instruct[1] == true && instruct[2] == true) {
                    output.get(0).setData(input.get(7).getData());
                }
                if (instruct[0] == true && instruct[1] == true && instruct[2] == true) {
                    output.get(0).setData(input.get(8).getData());
                }
                break;
            default:
                System.out.println("Error number of bits for multiplexor");
                break;
        }
    }

    /**
     */

    @Override
    public void putDataAtIndex(boolean d, int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

/**/
    @Override
    public void resetValues() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @return
     */
    public int getNbBitMux() {
        return nbBitMux;
    }

    /**
     *
     * @param nbBitMux
     */
    public void setNbBitMux(int nbBitMux) {
        this.nbBitMux = nbBitMux;
    }

    /**
     *
     * @return
     */
    public boolean[] getInstruct() {
        return instruct;
    }

    /**
     *
     * @param instruct
     */
    public void setInstruct(boolean[] instruct) {
        this.instruct = instruct;
    }
}
