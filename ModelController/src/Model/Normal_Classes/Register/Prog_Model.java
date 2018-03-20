package Model.Normal_Classes.Register;

import Controller.Helper_Controller;
import Model.Abstract_Classes.Register_Model_Abstract;
import Model.Normal_Classes.Wire.Wire_Model;
import java.lang.reflect.Array;
import java.util.Vector;

public class Prog_Model extends Register_Model_Abstract {

    private static final int IN_COMMANDE_READ_WIRE = 0;
    private static final int IN_COMMANDE_WRITE_WIRE = 1;
    private static final int IN_DATA_WIRE = 2;
    private static final int OUT_DATA_WIRE = 3;
    private static final int PC_CODE_SLEEP = 0;
    private static final int PC_WRITE_COMMANDE = 1;
    private static final int PC_READ_COMMANDE = 2;
    private static final int PC_VALUE_FILE_INDEX = 0;

    /**
     *
     * @param id
     * @param wireInput
     * @param wireOutput
     * @param absPath
     * @param fileName
     * @param blockSize
     * @param blockNumber
     */
    public Prog_Model(int id, Vector<Wire_Model> wireInput, Vector<Wire_Model> wireOutput, String absPath, String fileName, int blockSize, int blockNumber) {
        super(id, TYPE_REG_TEXT, wireInput, wireOutput, "TEXT(list of instructions) ", absPath, fileName, blockNumber, blockSize);
        checkWireNumber(wireInput.size(), wireOutput.size(), 3, 1);
    }

    /**
     *
     */
    @Override
    public void action() {
        int read_commande = boolArrayToInt(this.input.get(IN_COMMANDE_READ_WIRE).getData());
        int write_commande = boolArrayToInt(this.input.get(IN_COMMANDE_READ_WIRE).getData());
        boolean new_data[];
        this.output.get(OUT_DATA_WIRE).setActive(false);
        if (read_commande + write_commande > PC_CODE_SLEEP) {
            this.output.get(OUT_DATA_WIRE).setActive(true);
            if (read_commande == IN_COMMANDE_READ_WIRE) {
                boolean[] value = this.input.get(IN_DATA_WIRE).getData();
                new_data = value.clone();
                if (new_data.equals(readFile(PC_VALUE_FILE_INDEX)) == false) {
                    writeToFile(PC_VALUE_FILE_INDEX, value);
                }
            }
            if (write_commande == IN_COMMANDE_WRITE_WIRE) {
                this.dataOut = readFile(PC_VALUE_FILE_INDEX);
                Helper_Controller.putBufferDataInWire(this.output, this.dataOut);
            }
        }
    }
/*
    @Override
    public void putDataAtIndex(boolean d, int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void resetValues() {
        throw new UnsupportedOperationException("Not supported yet.");
    }*/
    void decodeur()
    {   
        boolean [] tab = new boolean[14];
        tab = this.input.get(IN_COMMANDE_READ_WIRE).getData();
        boolean [] opcode = new boolean[3];
        boolean [] ctrlDM = new boolean[2];
        boolean [] ctrlALU = new boolean[3];
        boolean [] adopA = new boolean[3];
        boolean [] adopB = new boolean[3];
        boolean [] cst = new boolean[8];
        for (int i =0; i<3;i++)
        {
            opcode[i]=tab[i];
        }
        
        /// si l'opcode est égale à 110 veut dire alu(A,B) -> A
        if(opcode[0]== true && opcode[1]== true && opcode[2]== false)
        {
           for (int i =3; i<6;i++)
            {
                adopA[i]=tab[i];
            }
           for (int i =6; i<9;i++)
            {
                adopB[i]=tab[i];
            }
           for (int i =9; i<12;i++)
            {
                ctrlALU[i]=tab[i];
            }
           for (int i =0; i<8;i++)
            {
                cst[i]=tab[i];
            }
           ctrlDM[0] = opcode[0]; // si 1 alors vers reg si 0 vers pc
           ctrlDM[1] = true; // pour active sinon false
           
        }
        /// si l'opcode est égale à 001 veut dire cst -> pc bra
        if(opcode[0]== false && opcode[1]== false && opcode[2]== true )
        {
           for (int i =6; i<14;i++)
            {
                cst[i]=tab[i];
            }
           for (int i =0; i<3;i++)
            {
                adopA[i]=false;
            }
           for (int i =0; i<3;i++)
            {
                adopB[i]=false;
            }
           for (int i =0; i<3;i++)
            {
                ctrlALU[i]=opcode[i];
            }
           ctrlDM[0] = opcode[0]; // si 1 alors vers reg si 0 vers pc
           ctrlDM[1] = true; // pour active sinon false
        }
        
        /// si opcode est égale à 010, veut dire jump pc+cst -> pc
        if(opcode[0]== false && opcode[1]== true && opcode[2]== false)
        {
           for (int i =3; i<6;i++)
            {
                ctrlALU[i]=tab[i];
            }
           for (int i =6; i<14;i++)
            {
                cst[i]=tab[i];
            }
           for (int i =0; i<3;i++)
            {
                adopA[i]=false;
            }
           for (int i =0; i<3;i++)
            {
                adopB[i]=false;
            }
           ctrlDM[0] = opcode[0]; // si 1 alors vers reg si 0 vers pc
           ctrlDM[1] = true; // pour active sinon false
        }
        
        /// si opcode est égale à 011, veut dire bnz si z == 0 pc+cst -> pc sinon pc +1 ->pc
        if(opcode[0]== false && opcode[1]== true && opcode[2]== true)
        {
           for (int i =6; i<14;i++)
            {
                cst[i]=tab[i];
            }
           for (int i =0; i<3;i++)
            {
                adopA[i]=false;
            }
           for (int i =0; i<3;i++)
            {
                adopB[i]=false;
            }
           for (int i =0; i<3;i++)
            {
                ctrlALU[i]=opcode[i];
            }
           ctrlDM[0] = opcode[0]; // si 1 alors vers reg si 0 vers pc
           ctrlDM[1] = true; // pour active sinon false
        }
        
        /// si opcode est égae à 100, veut dire alu(A, cst) -> A
        if(opcode[0]== true && opcode[1]== false && opcode[2]== false)
        {
           for (int i =3; i<6;i++)
            {
                adopA[i]=tab[i];
            }
           for (int i =6; i<14;i++)
            {
                cst[i]=tab[i];
            }
           for (int i =0; i<3;i++)
            {
                adopB[i]=false;
            }
           for (int i =0; i<3;i++)
            {
                ctrlALU[i]=opcode[i];
            }
           ctrlDM[0] = opcode[0]; // si 1 alors vers reg si 0 vers pc
           ctrlDM[1] = true; // pour active sinon false
        }
       
        //// put this data in different wire 
        
    }
}
