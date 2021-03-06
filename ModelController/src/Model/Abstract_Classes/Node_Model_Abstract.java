package Model.Abstract_Classes;

import Controller.Helper_Controller;
import Interface.Node_Interface;
import Model.Normal_Classes.Wire.Wire_Model;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Vector;

public abstract class Node_Model_Abstract extends Object_Model_Abstract implements Node_Interface {

    protected boolean[] dataIn;
    protected boolean[] dataOut;
    protected Vector<Wire_Model> input;
    protected Vector<Wire_Model> output;
    protected int dataInSize;
    protected int dataOutSize;

    /**
     *
     * @param id
     * @param type
     * @param description
     * @param wireInput
     * @param wireOutput
     */
    public Node_Model_Abstract(int id, int type, String description, Vector<Wire_Model> wireInput, Vector<Wire_Model> wireOutput) {
        super(id, type, "Noeud "+description);
        dataInSize = Helper_Controller.countWireSize(wireInput);
        dataOutSize = Helper_Controller.countWireSize(wireOutput);
        dataIn = new boolean[dataInSize];
        dataOut = new boolean[dataOutSize];
        input = wireInput;
        output = wireOutput;
        System.out.println("Node_Model_Abstract:: creating node of input size "+dataInSize+" output size "+dataOutSize);
    }

    /**
     *
     * @return
     */
    public boolean[] getDataIn() {
        return dataIn;
    }

    /**
     *
     * @param dataIn
     */
    public void setDataIn(boolean[] dataIn) {
        this.dataIn = dataIn;
    }

    /**
     *
     * @return
     */
    public boolean[] getDataOut() {
        return dataOut;
    }

    /**
     *
     * @param dataOut
     */
    public void setDataOut(boolean[] dataOut) {
        this.dataOut = dataOut;
    }

    /**
     *
     * @return
     */
    public Vector<Wire_Model> getInput() {
        return input;
    }

    /**
     *
     * @param input
     */
    public void setInput(Vector<Wire_Model> input) {
        this.input = input;
    }

    /**
     *
     * @return
     */
    public Vector<Wire_Model> getOutput() {
        return output;
    }

    /**
     *
     * @param output
     */
    public void setOutput(Vector<Wire_Model> output) {
        this.output = output;
    }

    /**
     *
     */
    public void getIncomingData() {
        dataIn = Helper_Controller.putWireDataInBuffer(input, dataIn);
        dataOut = Helper_Controller.putWireDataInBuffer(output, dataOut);

    }

    /**
     *
     */
    public void putOutputingData() {
        Helper_Controller.putBufferDataInWire(this.output, this.dataOut);
    }

    /**
     *
     * @param array
     * @return
     */
    protected int boolArrayToInt(boolean[] array) {
        int retval = 0;
        Helper_Controller.debugMessage3("Helper_Controller::boolArrayInt start" );
        for (int i = 0; i < array.length; i++) {
            if (array[i]) {
                Helper_Controller.debugMessage2("Helper_Controller::boolArrayInt true on index "+i);
                retval += Math.pow(2, i);
            }
        }
        Helper_Controller.debugMessage3("Helper_Controller::boolArrayInt value counted "+retval);
        return retval;
    }

    /**
     *
     * @param data
     * @param boolSize
     * @return
     */
    protected boolean[] intToBoolArray(int data, int boolSize) {
        String debigString="";
        Helper_Controller.debugMessage0("Node_Model_Abstract::intToBoolArray int data : "+data+" boolean size "+boolSize);

        boolean[] retval = new boolean[boolSize];
        for (int i = 0; i < boolSize; i++) {
            if(data % 2== 0){
                debigString +=".0";
                retval[i]=false;
            }else {
                retval[i]=true;
                debigString +=".1";
            }
            Helper_Controller.debugMessage0("i "+i +" data "+data+" value "+retval[i]);
            data/=2;
        }
        Helper_Controller.debugMessage0("Node_Model_Abstract::intToBoolArray result "+debigString);
        return retval;
    }
    
        /**
     * @param nmninw
     * @param nmboutw
     * @param expected_in_wire
     * @param expected_out_wire
     */
    protected void checkWireNumber(int nmninw, int nmboutw, int expected_in_wire, int expected_out_wire) {
        if (nmninw != expected_in_wire) {
            System.err.println("Error on id[" + this.id + "] unexpected number of in wires, " + expected_in_wire + " expected and got " + nmninw);
        }
        if (nmboutw != expected_out_wire) {
            System.err.println("Error on id[" + this.id + "] unexpected number of out wires, " + expected_out_wire + " expected and got " + nmninw);
        }
    }
}
