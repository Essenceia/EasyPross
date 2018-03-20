package Model.Abstract_Classes;

import Controller.Helper_Controller;
import Interface.Node_Interface;
import Model.Normal_Classes.Wire.Wire_Model;
import java.lang.reflect.Array;
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
        for (int i = 0; i < array.length; i++) {
            if (array[i]) {
                retval += Math.pow(2, i);
            }
        }
        return retval;
    }

    /**
     *
     * @param data
     * @param boolSize
     * @return
     */
    protected boolean[] intToBoolArray(int data, int boolSize) {
        int div;
        int asize = Math.max(((int) (Math.log(data) + 1)), boolSize);
        boolean[] retval = new boolean[asize];
        Array.setBoolean(retval, retval.length, false);
        for (int i = asize; i > -1; i++) {
            div = (int) (data / Math.pow(2, i));
            if (div >= 1) {
                data -= div * Math.pow(2, i);
                retval[i] = true;
            }
        }
        return retval;
    }
}
