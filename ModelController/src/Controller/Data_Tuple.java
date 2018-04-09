package Controller;

import java.util.Arrays;
import java.util.Vector;

public class Data_Tuple{
    private Integer id;
    private String stringValues;
    private Vector<Boolean> boolValues;
    public Data_Tuple(Integer id, String newValue){
        this.id = id;
        this.stringValues = newValue;
        boolValues = new Vector<>();
        stringToBoolVector();
    }
    public Data_Tuple(String id, String newValue){
        this.id = Integer.parseInt(id);
        this.stringValues = newValue;
        boolValues = new Vector<>();
        stringToBoolVector();
    }
    public Data_Tuple(Integer id, Vector<Boolean> newValue){
        this.id = id;
        this.boolValues = newValue;
        boolVectorToString();
    }
    public Data_Tuple(Integer id, boolean[] newValue){
        this.id= id;
        boolValues = new Vector<>();
        for(boolean b: newValue){
            boolValues.add(b);
        }
        boolVectorToString();

    }
    private void boolVectorToString(){
        this.stringValues="";
        int i = 0;
        int boolsize = this.boolValues.size();
        for (Boolean bool: this.boolValues
             ) {
            if(Boolean.TRUE.equals(bool)){
                this.stringValues+="1";
            }else{
                this.stringValues+="0";
            }
            if(i != boolsize -1 )this.stringValues+= ".";
        i++;
        }
        System.out.println("boolVectorToString value :"+this.stringValues);


    }
    private void stringToBoolVector(){
        String boolvalues[] = this.stringValues.split("\\.");
        for (String bool: boolvalues
                ) {
            if(bool.equals("0")){
                this.boolValues.add(false);
            }else this.boolValues.add(true);
        }
    }

    public Vector<Boolean> getBoolValues() {
        return boolValues;
    }
    public boolean[] getboolValues() {
        int i = 0;
        boolean data[] = new boolean[this.boolValues.size()];
        for (Boolean b:this.boolValues
             ) {
            data[i++] = b;
        }
        return data;
    }

    public Integer getId() {
        return id;
    }

    public String getStringValues() {
        return stringValues;
    }
}