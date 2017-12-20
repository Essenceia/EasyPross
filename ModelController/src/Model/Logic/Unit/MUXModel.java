package Model.Logic.Unit;

import Model.AbstractClasses.LogicUnitModel_Abstract;
import Model.Wire.WireModel;
import java.util.Vector;

public class MUXModel extends LogicUnitModel_Abstract {

    private int nb_bit_mux;
    private boolean[] instruct;

    /**
     * Constructeur
     *
     * @param synchrone
     * @param id
     * @param type
     * @param description
     * @param wire_input
     * @param wire_output
     * @param nb_mux
     * @param instruct
     */
    public MUXModel(boolean synchrone, int id, int type, String description, Vector<WireModel> wire_input, Vector<WireModel> wire_output, int nb_mux, boolean[] instruct) {
        super(synchrone, id, type, description, wire_input, wire_output);
        this.nb_bit_mux = nb_mux;

        int taille = 0;
        if (nb_mux == 2) {
            taille = 1;
        }
        if (nb_mux == 4) {
            taille = 2;
        }
        if (nb_mux == 8) {
            taille = 3;
        }
        this.instruct = new boolean[taille];
        for (int i = 0; i < taille; i++) {
            this.instruct[i] = instruct[i];
        }
    }

    //Override of interface methods of NodeInterface
    /**
     * Override action from NodeInterface
     */
    @Override
    public void action() {
        get_incomming_data();
        switch (nb_bit_mux) {
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
                System.out.println("Erreur dans le nombre de bit du multiplexeur");
                break;

        }
    }
}
