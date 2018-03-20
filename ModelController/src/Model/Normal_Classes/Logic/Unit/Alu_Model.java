package Model.Normal_Classes.Logic.Unit;

import Model.Abstract_Classes.Logic_Model_Abstract;
import Model.Normal_Classes.Wire.Wire_Model;
import java.util.Vector;

public class Alu_Model extends Logic_Model_Abstract {

    private static final int IN_COMMANDE_ALU = 0;
    private static final int IN_DATA_OP1= 1;
    private static final int IN_DATA_OP2 = 2;

//todo get value from width of output wire
    private int nbreBitsAlu;


    /**
     * Constructeur
      * @param wireInput
     * @param wireOutput
     * @param id
     * @param description
     */
    public Alu_Model(Vector<Wire_Model> wireInput, Vector<Wire_Model> wireOutput, int id,String description) {
        super(id, TYPE_LOGIC_ALU,"ALU "+ description, wireInput, wireOutput);
        //this.type= 9; pas besoin deja defin avec super - Ja

         if(input.isEmpty())
        {
            System.out.println("Erreur il n'y a pas de fils en entrée");
        }
    }

    /**
     *
     * @param nbreBitsAlu
     */
    public void setNbreBitsAlu(int nbreBitsAlu) {
        this.nbreBitsAlu = nbreBitsAlu;
    }

    /**
     *
     * @return
     */
    public int getNbreBitsAlu() {
        return nbreBitsAlu;
    }
    //Override of interface methods of Node_Interface
    /**
     * Override action from Node_Interface
     */
    /**
     *
     */
    @Override
    public void action() {
        int nb1 = 0, nb2 = 0, nb3 = 0, opCode = 0;
        boolean[] tab1 = this.input.get(0).getData();
        boolean[] tab2 = this.input.get(1).getData();
        boolean[] tab3 = this.input.get(2).getData();
        boolean[] dataOut = new boolean[nbreBitsAlu];
        for (int i = 0; i < 3; i++) {
            if (tab1[i]) {
                opCode += Math.pow(2, 3 - i);
            }
        }
        for (int i = 0; i < tab2.length; i++) {
            if (tab2[i]) {
                nb1 += Math.pow(2, tab2.length - i);
            }
        }
        for (int i = 0; i < tab3.length; i++) {
            if (tab3[i]) {
                nb2 += Math.pow(2, tab3.length - i);
            }
        }
        switch (opCode) {
            case 0:
                nb3 = nb1 + nb2;
                break;
            case 1:
                nb3 = nb1 - nb2;
                break;
            case 2:
                nb3 = nb1 * nb2;
                break;
            case 3:
                nb3 = nb1 / nb2;
                break;
            case 4:
                nb3 = nb1 & nb2;
                break;
            case 5:
                nb3 = nb1 | nb2;
                break;
            case 6:
                nb3 = ~nb1;
                break;
            case 7:
                nb3 = nb1 ^ nb2;
                break;
            default:
                break;
        }
        for (int i = nbreBitsAlu; i > -1; i--) {
            int dr = (int) (nb3 / Math.pow(2, i));
            if (dr != 0) {
                nb3 -= dr * Math.pow(2, i);
            }
            dataOut[i] = (dr != 0);
        }
    }
    /**
     *
     * @param d
     * @param index

    @Override
    public void putDataAtIndex(boolean d, int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    @Override
    public void resetValues() {
        throw new UnsupportedOperationException("Not supported yet.");
    }*/


}
