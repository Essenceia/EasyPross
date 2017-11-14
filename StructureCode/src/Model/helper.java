package Model;

import java.util.Vector;
/*
*Helper class static de signleton
 */
public final class helper {
    private helper(){};
    public static int count_wire_size(Vector<Wire> target){
        int count = 0;
        for (Wire aTarget : target) {
            //increment size of bus
            //count += evector.next().get_data_size();
            count += aTarget.get_data_size();
        }
         return count;
    }
    public static Boolean[] put_wire_data_in_buffer(Vector<Wire> data,Boolean[] buffer){
        //debug
        int i=0;
        String debug_sting = new String();
        System.out.println("put_wire_data_in_buffer");
        for (Wire tmp:data){
            for (int j = 0; j < tmp.get_data_size() ; j++) {
                debug_sting+=tmp.get_data()[j];
                buffer[i++] = tmp.get_data()[j];
            }
        }
        System.out.println(debug_sting);
        //debug
        debug_sting = "";
        for (Boolean aBuffer : buffer) {

            debug_sting += aBuffer;
        }
        System.out.println(debug_sting);
        return buffer;
    }
    public static void put_buffer_data_in_wire(Vector<Wire> data,Boolean[] buffer){
        int g =0;
        for(Wire w:data){
            for (int i = 0; i < w.get_data_size(); i++) {
                w.put_data_at_index(buffer[g],g);
                g++;
            }
        }
    }
    public static Boolean[] set_all_to(Boolean value,int size){
        Boolean[] ret = new Boolean[size];
        for (int i = 0; i < size ; i++) {
            ret[i] = value;
        }
        return ret;
    }
}
