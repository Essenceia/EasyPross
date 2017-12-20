package Model.Logic.Unit;

import Model.AbstractClasses.LogicUnitModel_Abstract;

public class DEMUXModel extends LogicUnitModel_Abstract {

    private int nb_bit_demux;
    private boolean[] instruct;

    /**
     * Constructor
     *
     * @param data_in
     * @param data_out
     * @param id
     * @param type
     * @param description
     */
    public DEMUXModel(boolean[] data_in, boolean[] data_out, int id, int type, String description, int nb_demux, boolean[] instruct) {
        super(data_in, data_out, id, type, description);
        this.nb_bit_demux = nb_demux;
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
        boolean[] data_in;
        boolean[] s0;
        boolean[] s1;
        boolean[] s2;
        boolean[] s3;
        switch (nb_bit_demux) {
            case 2:
                s0 = new boolean[nb_bit_demux];
                s1 = new boolean[nb_bit_demux];
                data_in = new boolean[nb_bit_demux];
                if (c[0] == false && c[1] == false && c[2] == false) {
                    s0 = data_in;
                    for (int i = 0; i < nb_bit_demux; i++) {
                        s1[i] = false;
                    }
                } else {
                    s1 = data_in;
                    for (int i = 0; i < nb_bit_demux; i++) {
                        s0[i] = false;
                    }
                }
                break;

            case 4:
                s0 = new boolean[nb_bit_demux];
                s1 = new boolean[nb_bit_demux];
                s2 = new boolean[nb_bit_demux];
                s3 = new boolean[nb_bit_demux];
                data_in = new boolean[nb_bit_demux];
                if (c[0] == false && c[1] == false && c[2] == false) {
                    s0 = data_in;
                    for (int i = 0; i < nb_bit_demux; i++) {
                        s1[i] = false;
                        s2[i] = false;
                        s3[i] = false;
                    }
                }
                if (c[0] == true && c[1] == false && c[2] == false) {
                    s1 = data_in;
                    for (int i = 0; i < nb_bit_demux; i++) {
                        s0[i] = false;
                        s2[i] = false;
                        s3[i] = false;
                    }
                }
                if (c[0] == false && c[1] == true && c[2] == false) {
                    s2 = data_in;
                    for (int i = 0; i < nb_bit_demux; i++) {
                        s1[i] = false;
                        s0[i] = false;
                        s3[i] = false;
                    }
                }
                if (c[0] == true && c[1] == true && c[2] == false) {
                    s3 = data_in;
                    for (int i = 0; i < nb_bit_demux; i++) {
                        s1[i] = false;
                        s2[i] = false;
                        s0[i] = false;
                    }
                }
                break;
            default:
                System.out.println("Erreur dans le nombre de bit du multiplexeur");
                break;

        }

    }
}
