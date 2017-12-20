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
    public MUXModel(boolean synchrone,int id, int type, String description, Vector<WireModel> wire_input, Vector<WireModel> wire_output,int nb_mux, boolean[] instruct) {
        super(synchrone,id, type, description,wire_input, wire_output);
        this.nb_bit_mux = nb_mux;
        this.instruct = new boolean[3];
        for (int i = 0; i < 3; i++) {
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
        boolean[] c = new boolean[3];
        c[0] = instruct[0];
        c[1] = instruct[1];
        c[2] = instruct[2];
        boolean[] data_out;
        boolean[] e0;
        boolean[] e1;
        boolean[] e2;
        boolean[] e3;
        switch (nb_bit_mux) {
            case 2:
                e0 = new boolean[nb_bit_mux];
                e1 = new boolean[nb_bit_mux];
                data_out = new boolean[nb_bit_mux];
                for (int i = 3; i < nb_bit_mux + 3; i++) {
                    e0[i - 3] = data_in[i];
                }
                for (int i = 3 + nb_bit_mux; i < nb_bit_mux + 3 + nb_bit_mux; i++) {
                    e1[i - 3 - nb_bit_mux] = data_in[i];
                }
                if (c[0] == false && c[1] == false && c[2] == false) {
                    data_out = e0;
                } else {
                    data_out = e1;
                }

                break;

            case 4:
                e0 = new boolean[nb_bit_mux];
                e1 = new boolean[nb_bit_mux];
                e2 = new boolean[nb_bit_mux];
                e3 = new boolean[nb_bit_mux];
                data_out = new boolean[nb_bit_mux];
                for (int i = 3; i < nb_bit_mux + 3; i++) {
                    e0[i - 3] = data_in[i];
                }
                for (int i = 3 + nb_bit_mux; i < nb_bit_mux + 3 + nb_bit_mux; i++) {
                    e1[i - 3 - nb_bit_mux] = data_in[i];
                }
                for (int i = 3 + (2 * nb_bit_mux); i < 3 + (3 * nb_bit_mux); i++) {
                    e2[i - 3 - (2 * nb_bit_mux)] = data_in[i];
                }
                for (int i = 3 + (3 * nb_bit_mux); i < 3 + (4 * nb_bit_mux); i++) {
                    e3[i - 3 - (3 * nb_bit_mux)] = data_in[i];
                }
                if (c[0] == false && c[1] == false && c[2] == false) {
                    data_out = e0;
                }
                if (c[0] == true && c[1] == false && c[2] == false) {
                    data_out = e1;
                }
                if (c[0] == false && c[1] == true && c[2] == false) {
                    data_out = e2;
                }
                if (c[0] == true && c[1] == true && c[2] == false) {
                    data_out = e3;
                }
                break;
            default:
                System.out.println("Erreur dans le nombre de bit du multiplexeur");
                break;

        }
    }
}
