package Controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;


public class Helper_Data_Handler {
    /**
     * Parse_to_String
     * @param ids
     * @param newValues
     * @return
     */
    public static String Parse_to_String(Vector<Integer> ids, Vector<String> newValues ){
        String retVal = "";
        int maxit = Math.min(ids.size(),newValues.size());
        for(int i = 0 ; i < maxit; i ++){
            retVal+= ids.toString()+":"+newValues;
            if(i != maxit-1)retVal+=",";
        }
        return retVal;
    }
    public static String Parse_to_String(Vector<Data_Tuple> dataVector){
        String retVal = "";
        Data_Tuple current;
        int dataSize = dataVector.size();

        for (int i = 0; i < dataSize; i++) {

            current = dataVector.get(i);
            retVal+= current.getId().toString();
            retVal+=":";
            retVal+=current.getStringValues();
            if(i != dataSize-1)retVal+=",";

        }
        return retVal;
    }

    /**
     * parseFromStrin
     *
     * This function is used to parse the data from a recived message
     * example:: <id>:<data>,<id>:<data>,<id>:<data>
     * @param recMsg
     * @return
     */
    public static Vector<Data_Tuple> parseIdAndDataFromString(String recMsg){
        Vector<Data_Tuple> retVec = new Vector<>();
        String inddata[];
        System.out.println("Extracting data from ::"+recMsg);
        String datatuples[] = recMsg.split(",");
        for (String tuple:datatuples
             ) {
            inddata = tuple.split(":");
            if(inddata.length==2) {
                retVec.add(new Data_Tuple(inddata[0], inddata[1]));
            }else{
                System.out.println("Error :: expected ellements after split 2 gotten "+inddata.length);
            }
        }
        return retVec;
    }
    public static String createIdString(Vector<Integer> Idwire){
        String nvmsg="";
        for (int i = 0; i < Idwire.size(); i++) {
            nvmsg+= Idwire.get(i).toString();
            if(i!= Idwire.size() - 1)nvmsg+=",";
        }
        System.out.println("createIdString:: input "+Idwire.toString()+" result "+nvmsg);
        return nvmsg;
    }
    public static Vector<Integer> parseIdString(String idString) {
        Vector<Integer> retId = new Vector<>();
        String nvmsg[] = idString.split(",");
        for (String id : nvmsg
                ) {
            retId.add(Integer.parseInt(id));
        }
        return retId;
    }
    public static void printDataTupleArray(Vector<Data_Tuple> toprint){
        String val= "Values recived :\n";
        for (Data_Tuple data:toprint
             ) {
            val+= "id "+data.getId()+"::"+data.getStringValues()+" \n";
        }
    }
    public static String createTmpFile() {
    	String tempFilePath="easypross.tmp";
    	      String absolutePath="easypross.tmp";
    	       System.out.println("Creating tmp file ");
            //create a temp file
            try {
                File temp = File.createTempFile(Long.toString(System.currentTimeMillis()), ".tmp");

                //Get tempropary file path
                 absolutePath = temp.getAbsolutePath();
                tempFilePath = absolutePath.
                        substring(0,absolutePath.lastIndexOf(File.separator));

            }catch(IOException e){

                e.printStackTrace();

        }
        return absolutePath;
    }
    public static String toWireWithDot(String in, Integer size){
        String out="";
        int i= 0;
        for(char c : in.toCharArray()){
            if(i>=size)break;
            if(i!= 0)out+=".";
            if(c=='1')out+="1";
            else out += "0";
            i++;
        }
        return out;
    }
}
