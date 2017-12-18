package Controller;

import java.util.Vector;
import Model.Wire.WireModel;
public final class HelperController implements Interface.HelperInterface{
    private HelperController(){};
    public static int count_wire_size(Vector<WireModel> target){
        int count = 0;
        for (WireModel aTarget : target) {
            //increment size of bus
            //count += evector.next().get_data_size();
            count += aTarget.getSizeBus();
        }
         return count;
    }
    public static boolean[] put_wire_data_in_buffer(Vector<WireModel> data,boolean[] buffer){
        //debug
        int i=0;
        String debug_string = new String();
        System.out.println("put_wire_data_in_buffer");
        for (WireModel tmp:data){
            for (int j = 0; j < tmp.getSizeBus() ; j++) {
                debug_string+=tmp.getData()[j];
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
    public static void put_buffer_data_in_wire(Vector<WireModel> data,boolean[] buffer){
        int g =0;
        for(WireModel w:data){
            for (int i = 0; i < w.getSizeBus(); i++) {
                w.putDataAtIndex(buffer[g],g);
                g++;
            }
        }
    }
    public static boolean[] set_all_to(boolean value,int size){
        boolean[] ret = new boolean[size];
        for (int i = 0; i < size ; i++) {
            ret[i] = value;
        }
        return ret;
    }
    
}
