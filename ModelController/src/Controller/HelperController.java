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
            count += aTarget.getSizeBus();
        }
        return count;
    }

    /**
     * put_wire_data_in_buffer
     *
     * transfert contents of data on wires to the given buffer of boolean
     *
     * @param data - origin of data
     * @param buffer - array that is going to be filled
     *
     * @return - filled boolean
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
        // todo - take out when tested finished debug
        debug_string = "";
        for (boolean aBuffer : buffer) {

            debug_string += aBuffer;
        }
        System.out.println(debug_string);
        return buffer;
    }

    /**
     *put_buffer_data_in_wire
     *
     * Transfert data from a boolean array into the output wires. This will fill all wires
     * starting from the lowest indexed wire up untill the highest index and iterate over all
     * the ellements of theys wires to copy the next ellement of our buffer into it.
     *
     * eg :
     * data -> 2 wires, 1st of size 2, 2nd of size 6
     * buffer -> 8 booleans
     * ...
     * data[0][0] = buffer[0]
     * ...
     * data[1][0] = buffer[2]
     *
     * @param data - where the data will be copied
     * @param buffer - origin of data
     */
    public static void put_buffer_data_in_wire(Vector<WireModel> data, boolean[] buffer) {
        int g = 0;
        for (WireModel w : data) {
            for (int i = 0; i < w.getSizeBus(); i++) {
                if(g < buffer.length){
                    w.putDataAtIndex(buffer[g], g);
                    g++;
                }

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
