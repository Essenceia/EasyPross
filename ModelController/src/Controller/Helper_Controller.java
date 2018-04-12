package Controller;

import Model.Normal_Classes.Wire.Wire_Model;

import java.util.Arrays;
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
        System.out.println("Helper_Controller::countWireSize size found :"+count);
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
        boolean wireBufferValues[];
        Helper_Controller.debugMessage("Helper_Controller::putWireDataInBuffer");
        for (Wire_Model tmp : data) {
            wireBufferValues = tmp.getData();
            for (int j = 0; j < tmp.getSizeBus(); j++) {
                buffer[i++] = wireBufferValues[j];
            }
        }

        return buffer;
    }

    public static void  putWireDataInBuffer(Wire_Model data, boolean[] buffer) {
        int i = 0;
        String debugMessag = "Helper_Controller::putWireDataInBuffer data("+data.getSizeBus()+")";
        boolean wireBufferValues[] = new boolean[data.getSizeBus()];
            wireBufferValues = data.getData();
            for (int j = 0; j < data.getSizeBus(); j++) {
                buffer[i++] = wireBufferValues[j];
                debugMessag+= buffer[j]+" ";
            }
            Helper_Controller.debugMessage3(debugMessag);

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
                    w.putDataAtIndex(buffer[g], i);
                    g++;
                }
            }
        }
    }
    public static void putBufferDataInWire(Wire_Model data, boolean[] buffer) {
        int g = 0;
            for (int i = 0; i < data.getSizeBus(); i++) {
                if (g < buffer.length) {
                    data.putDataAtIndex(buffer[g], g);
                    g++;
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
    /**
     *helperStringToBool
     *
     *Prefix "helper" to not get mixed up with the similar function in Register_Model_Abstract
     *
     * @param input
     * @return
     */
    public static boolean[] helperStringToBool(String input) {
        int i = 0;
        String[] splited = input.split("\\.");
        boolean buff[]= new boolean[input.length()];
        boolean retarray[];
        for (String val : splited) {
            switch (val) {
                case "0":
                    buff[i] = false;
                    break;
                case "1":
                    buff[i] = true;
                    break;
                default:
                    System.err.println("Unexpected string value " + val);
                    buff[i] = false;
                    break;
            }
            i++;
        }
        retarray = new boolean[i];
        //copy value from buffer to return array
        System.arraycopy(buff, 0, retarray, 0, i);
        return retarray;
    }

    /**
     * helperBoolToString
     *
     * Prefix "helper" to not get mixed up with the similar function in Register_Model_Abstract
     *
     * @param input
     * @return
     */
    public  static String helperBoolToString(boolean[] input) {
        int i = 0;
        String nv_string = "";
        boolean to_write[] = new boolean[input.length];
        for (boolean val : to_write) {
            if(i!= 0 & i!= (to_write.length ))nv_string+=".";
            if (!val) {
                nv_string += "0";
            } else {
                nv_string += "1";
            }
            i++;
        }
        return nv_string;
    }

    public static void debugMessage(String msg){
        System.out.println("\033[0;32m"+msg+"\033[0m");
    }
    public static void debugMessage2(String msg){
        System.out.println("\033[0;33m"+msg+"\033[0m");
    }
    public static void debugMessage3(String msg){
        System.out.println("\033[0;34m"+msg+"\033[0m");
    }
    public static void debugMessage0(String msg){
        System.out.println("\033[0;36m"+msg+"\033[0m");
    }
    public static void debugMessage4(String msg){
        System.out.println("\033[0;37m"+msg+"\033[0m");
    }

    public static void errorMessage(String msg){
        System.out.println("\033[0;31m"+msg+"\033[0m");
    }
}
