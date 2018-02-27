package Model.Logic.Unit;

import Model.AbstractClasses.LogicUnitModel_Abstract;
import Model.Wire.WireModel;
import java.util.Vector;

public class DEMUXModel extends LogicUnitModel_Abstract {

    private int nb_bit_demux;
    private boolean[] instruct;

    /**
     * Constructeur
     *
     * @param synchrone
     * @param id
     * @param wire_input
     * @param wire_output
     * @param nb_demux
     */
    public DEMUXModel(boolean synchrone, int id,  Vector<WireModel> wire_input, Vector<WireModel> wire_output, int nb_demux) {
        super(synchrone, id, TYPE_LOGIC_DEMUX, " Demux", wire_input, wire_output);
        this.nb_bit_demux = nb_demux;
        int taille = 0;
        if (nb_demux == 2) {
            taille = 1;
        }
        if (nb_demux == 4) {
            taille = 2;
        }
        if (nb_demux == 8) {
            taille = 3;
        }
        this.instruct = new boolean[taille];
        for (int i = 0; i < taille; i++) {
            this.instruct[i] = false;
        }

        if (input.isEmpty()) {
            System.out.println("Erreur il n'y a pas de fils en entrée");
        }

    }

    //Override of interface methods of NodeInterface
    /**
     * Override action from NodeInterface
     */
    @Override
    public void action() {
        get_incomming_data();
        switch (nb_bit_demux) {
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
                System.out.println("Erreur dans le nombre de bit du multiplexeur");
                break;

        }

    }
}
