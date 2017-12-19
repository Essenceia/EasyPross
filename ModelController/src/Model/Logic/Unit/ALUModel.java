package Model.Logic.Unit;

import Model.AbstractClasses.LogicUnitModel_Abstract;

public class ALUModel extends LogicUnitModel_Abstract {

    private int nbreBitsALU;

    /**
     * Constructor
     *
     * @param data_in
     * @param data_out
     * @param id
     * @param type
     * @param description
     * @param nbreBitsALU
     */
    public ALUModel(boolean[] data_in, boolean[] data_out, int id, int type, String description, int nbreBitsALU) {
        super(data_in, data_out, id, type, description);
        this.nbreBitsALU = nbreBitsALU;
    }

    /**
     * set the number of bits of ALU
     *
     * @param nbreBitsALU
     */
    public void setNbreBitsALU(int nbreBitsALU) {
        this.nbreBitsALU = nbreBitsALU;
    }

    /**
     * get number of bits of ALU
     *
     * @return nbreBitsALU
     */
    public int getNbreBitsALU() {
        return nbreBitsALU;
    }

    //Override of interface methods of NodeInterface
    /**
     * Override action from NodeInterface
     */
    @Override
    public void action() {
        int nbre1 = 0, nbre2 = 0, nbre3 = 0, opCode = 0;
        for (int i = 0; i < 2; i++) {
            if (data_in[i]) {
                opCode += Math.pow(2, 3 - i);
            }
        }
        for (int i = 3; i < 2 * nbreBitsALU + 3; i++) {
            if (i >= 2 && i <= nbreBitsALU + 2) {
                if (data_in[i]) {
                    nbre1 += Math.pow(2, (nbreBitsALU - i) + 2);
                }
            }
            if (i >= nbreBitsALU + 3 && i <= 2 * nbreBitsALU + 2) {
                if (data_in[i]) {
                    nbre2 += Math.pow(2, (nbreBitsALU - i) + 2 + nbreBitsALU);
                }
            }
        }
        switch (opCode) {
            case 0:
                nbre3 = nbre1 + nbre2;
                break;
            case 1:
                nbre3 = nbre1 - nbre2;
                break;
            case 2: 
                nbre3 = nbre1 * nbre2;
                break;
            case 3:
                nbre3 = nbre1 / nbre2;
                break;
            case 4:
                nbre3 = nbre1 & nbre2;
                break;
            case 5:
                nbre3 = nbre1 | nbre2;
                break;
            case 6:
                nbre3 = ~nbre1;
                break;
            case 7:
                nbre3 = nbre1^nbre2;
                break;
            default:
                break;
        }
        boolean []data_out = new boolean[nbreBitsALU];
        for(int i = nbreBitsALU; i > -1; i--) {
            int dr = (int)(nbre3/Math.pow(2,i));
            if(dr != 0) nbre3 -= dr*Math.pow(2,i);
            data_out[i] = (dr != 0);
        }       
    }
}
