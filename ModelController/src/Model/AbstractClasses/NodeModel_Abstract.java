package Model.AbstractClasses;

import Controller.HelperController;
import Interface.NodeInterface;
import Model.Wire.WireModel;

import java.lang.reflect.Array;
import java.util.Vector;

public abstract class NodeModel_Abstract extends ObjectModel_Abstract implements NodeInterface {

    /**
     * Attributes :data_in[], data_out[], input &
     * output (List<WireModel>)
     */
    protected boolean[] data_in; //données entrantes
    protected boolean[] data_out; //données sortantes
    protected Vector<WireModel> input; //donnée du bus d'entrée
    protected Vector<WireModel> output; //donnée du bus de sortie
    protected int data_in_size; // taille du bus de donnée entrant
    protected int data_out_size; // taille du bus de donnée sortant

    /*
    Ceci est une erreur , ce contructeur est le mauvais on devrait pas donner ls data mais les wires connecter
    en entrer- sortie - Ja
    public NodeModel_Abstract(boolean[] data_in, boolean[] data_out, int id, int type, String description) {
        super(id, type, description);
        this.data_in = data_in;
        this.data_out = data_out;
    }
*/

    /**
     * Constructeur qui initialise ces paramètres
     * @param id
     * @param type
     * @param description
     * @param wire_input
     * @param wire_output
     */
    public NodeModel_Abstract(int id,  int type,String description,Vector<WireModel> wire_input, Vector<WireModel> wire_output) {
        super(id, type, description);
        //descriptif
        description += "Noeud ";
        //taille des bus de donnee entrant et sortant
        data_in_size = HelperController.count_wire_size(wire_input);
        data_out_size = HelperController.count_wire_size(wire_output);
        data_in = new boolean[data_in_size];
        data_out = new boolean[data_out_size];
        //donnes des bus ! l'ordre sera conserver
        input = wire_input;
        output = wire_output;
    }

    /**
     * getter Data_In
     *
     * @return data_in
     */
    public boolean[] getData_in() {
        return data_in;
    }

    /**
     * setter Data_In
     *
     * @param data_in
     */
    public void setData_in(boolean[] data_in) {
        this.data_in = data_in;
    }

    /**
     * getter Data_Out
     *
     * @return data_out
     */
    public boolean[] getData_out() {
        return data_out;
    }

    /**
     * setter Data_Out
     *
     * @param data_out
     */
    public void setData_out(boolean[] data_out) {
        this.data_out = data_out;
    }

    /**
     * getter InputList
     *
     * @return input
     */
    public Vector<WireModel> getInput() {
        return input;
    }

    /**
     * setter InputList
     *
     * @param input
     */
    public void setInput(Vector<WireModel> input) {
        this.input = input;
    }

    /**
     * getter OutputList
     *
     * @return output
     */
    public Vector<WireModel> getOutput() {
        return output;
    }

    /**
     * setter OutputList
     *
     * @param output
     */
    public void setOutput(Vector<WireModel> output) {
        this.output = output;
    }

    //@Override
    public void get_incomming_data() {
        //recupérer toutes les données entrantes et les placer dans data_in
        data_in = HelperController.put_wire_data_in_buffer(input, data_in);
        data_out = HelperController.put_wire_data_in_buffer(output, data_out);

    }

    //@Override
    public void put_outputing_data() {
        //mettre les données dans les wires en sortie
        HelperController.put_wire_data_in_buffer(output, data_out);
    }

    /**
     * boolarray_to_int
     *
     * Convert an array of booleans to an integer value
     * @param array - array of boolean to be converted
     * @return - resulting integer value
     */
    protected int boolarray_to_int(boolean[] array){
        int retval = 0;
        for(int i = 0 ; i < array.length ; i ++){
            if(array[i])retval += Math.pow(2,i);
        }
        return retval;
    }

    /**
     * int_to_boolarray
     *
     * Convert an int to an array of booleans
     *
     * @param data integer value to convert
     * @return result of conversion, array of booleans
     */
    protected boolean[] int_to_boolarray(int data,int boolsize){
        int div;
        int asize = Math.max(((int) (Math.log(data)+1)),boolsize);
        boolean[] retval= new boolean[asize];
        Array.setBoolean(retval,retval.length,false);
        for(int i = asize; i >-1; i ++){
            div = (int)(data/Math.pow(2,i));
            if(div>=1){
                data -=div*Math.pow(2,i);
                retval[i] = true;
            }
        }
        return retval;
    }
}
