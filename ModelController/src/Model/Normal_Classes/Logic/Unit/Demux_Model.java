package Model.Normal_Classes.Logic.Unit;

import Model.Abstract_Classes.Logic_Model_Abstract;
import Model.Normal_Classes.Wire.Wire_Model;
import java.util.Vector;

public class Demux_Model extends Logic_Model_Abstract {

    private static final int IN_COMMANDE_DEMUX = 0;

    private int nbBitDemux;
    private boolean[] instruct;

    /**
     *
     * @param id
     * @param description
     * @param wireInput
     * @param wireOutput
     */
    public Demux_Model( int id, String description, Vector<Wire_Model> wireInput, Vector<Wire_Model> wireOutput) {
        super(id, TYPE_LOGIC_DEMUX, description, wireInput, wireOutput);
        this.nbBitDemux = wireInput.get(IN_COMMANDE_DEMUX).getSizeBus();

        this.instruct = new boolean[this.nbBitDemux];
        for (int i = 0; i < this.nbBitDemux; i++) {
            this.instruct[i] = false;
        }
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
        switch (nbBitDemux) {
            case 2:
                if (instruct[0] == false) {
                    output.get(0).setData(input.get(1).getData());
                } else {
                    output.get(1).setData(input.get(1).getData());
                }
                break;
            case 4:
                if (instruct[0] == false && instruct[1] == false) {
                    output.get(0).setData(input.get(1).getData());
                }
                if (instruct[0] == true && instruct[1] == false) {
                    output.get(1).setData(input.get(1).getData());
                }
                if (instruct[0] == false && instruct[1] == true) {
                    output.get(2).setData(input.get(1).getData());
                }
                if (instruct[0] == true && instruct[1] == true) {
                    output.get(3).setData(input.get(1).getData());
                }
                break;
            case 8:
                if (instruct[0] == false && instruct[1] == false && instruct[2] == false) {
                    output.get(0).setData(input.get(1).getData());
                }
                if (instruct[0] == true && instruct[1] == false && instruct[2] == false) {
                    output.get(1).setData(input.get(1).getData());
                }
                if (instruct[0] == false && instruct[1] == true && instruct[2] == false) {
                    output.get(2).setData(input.get(1).getData());
                }
                if (instruct[0] == true && instruct[1] == true && instruct[2] == false) {
                    output.get(3).setData(input.get(1).getData());
                }
                if (instruct[0] == false && instruct[1] == false && instruct[2] == true) {
                    output.get(4).setData(input.get(1).getData());
                }
                if (instruct[0] == true && instruct[1] == false && instruct[2] == true) {
                    output.get(5).setData(input.get(1).getData());
                }
                if (instruct[0] == false && instruct[1] == true && instruct[2] == true) {
                    output.get(6).setData(input.get(1).getData());
                }
                if (instruct[0] == true && instruct[1] == true && instruct[2] == true) {
                    output.get(7).setData(input.get(1).getData());
                }
                break;
            default:
                System.out.println("Error in number of bits in demultiplexor");
                break;
        }
    }

    /**


    @Override
    public void putDataAtIndex(boolean d, int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    @Override
    public void resetValues() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @return
     */
    public int getNbBitDemux() {
        return nbBitDemux;
    }

    /**
     *
     * @param nbBitDemux
     */
    public void setNbBitDemux(int nbBitDemux) {
        this.nbBitDemux = nbBitDemux;
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
