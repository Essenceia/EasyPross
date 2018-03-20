package Controller;

import Model.Normal_Classes.Wire.Wire_Model;
import java.util.Vector;

public class Helper_Controller implements Interface.Helper_Interface {

    /**
     *
     */
    private Helper_Controller() {

    }

    /**
     *
     * @param target
     * @return
     */
    public static int countWireSize(Vector<Wire_Model> target) {
        int count = 0;
        for (Wire_Model aTarget : target) {
            count += aTarget.getSizeBus();
        }
        return count;
    }

    /**
     *
     * @param data
     * @param buffer
     * @return
     */
    public static boolean[] putWireDataInBuffer(Vector<Wire_Model> data, boolean[] buffer) {
        int i = 0;
        String debugString = new String();
        System.out.println("We put wire the data in buffer");
        for (Wire_Model tmp : data) {
            for (int j = 0; j < tmp.getSizeBus(); j++) {
                debugString += tmp.getData()[j];
                buffer[i++] = tmp.getData()[j];
            }
        }
        System.out.println(debugString);
        debugString = "";
        for (boolean aBuffer : buffer) {
            debugString += aBuffer;
        }
        System.out.println(debugString);
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
    public static void putBufferDataInWire(Vector<Wire_Model> data, boolean[] buffer) {
        int g = 0;
        for (Wire_Model w : data) {
            for (int i = 0; i < w.getSizeBus(); i++) {
                if (g < buffer.length) {
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
     * @return
     */
    public static boolean[] setAllTo(boolean value, int size) {
        boolean[] ret = new boolean[size];
        for (int i = 0; i < size; i++) {
            ret[i] = value;
        }
        return ret;
    }
}
