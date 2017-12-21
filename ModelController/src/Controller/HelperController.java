package Controller;

import java.util.Vector;
import Model.Wire.WireModel;

public final class HelperController implements Interface.HelperInterface {

    /**
     * Constructeur privé de la classe
     */
    private HelperController() {
    }

    /**
     * Permet de compter le nombre de wire de la target, un vector de WireModel
     *
     * @param target
     *
     * @return count
     */
    public static int count_wire_size(Vector<WireModel> target) {
        int count = 0;
        for (WireModel aTarget : target) {
            //increment size of bus
            //count += evector.next().get_data_size();
            count += aTarget.getSizeBus();
        }
        return count;
    }

    /**
     * Programme de débug
     *
     * @param data
     * @param buffer
     *
     * @return buffer
     */
    public static boolean[] put_wire_data_in_buffer(Vector<WireModel> data, boolean[] buffer) {
        //debug
        int i = 0;
        String debug_string = new String();
        System.out.println("put_wire_data_in_buffer");
        for (WireModel tmp : data) {
            for (int j = 0; j < tmp.getSizeBus(); j++) {
                debug_string += tmp.getData()[j];
                buffer[i++] = tmp.getData()[j];
            }
        }
        System.out.println(debug_string);
        //debug
        debug_string = "";
        for (boolean aBuffer : buffer) {

            debug_string += aBuffer;
        }
        System.out.println(debug_string);
        return buffer;
    }

    /**
     *
     * @param data
     * @param buffer
     */
    public static void put_buffer_data_in_wire(Vector<WireModel> data, boolean[] buffer) {
        int g = 0;
        for (WireModel w : data) {
            for (int i = 0; i < w.getSizeBus(); i++) {
                w.putDataAtIndex(buffer[g], g);
                g++;
            }
        }
    }

    /**
     *
     * @param value
     * @param size
     *
     * @return ret : tableau de booléen
     */
    public static boolean[] set_all_to(boolean value, int size) {
        boolean[] ret = new boolean[size]; // création du tableau de booléen
        for (int i = 0; i < size; i++) {
            ret[i] = value; // on met la valeur dans le tableau de booléen à l'indice i
        }
        return ret;
    }
}
