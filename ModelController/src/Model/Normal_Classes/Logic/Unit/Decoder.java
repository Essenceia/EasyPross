package Model.Normal_Classes.Logic.Unit;

import Controller.Helper_Controller;
import Model.Abstract_Classes.Logic_Model_Abstract;
import Model.Normal_Classes.Wire.Wire_Model;
import java.util.Vector;
import Model.Abstract_Classes.Register_Model_Abstract;

public  class Decoder extends Logic_Model_Abstract {
    
    private static final int IN_READ_DECODER_WIRE = 0;
    
    private static final int OUT_OPCODE_DECODER_WIRE = 0;
    private static final int OUT_OPA_DECODER_WIRE = 1;
    private static final int OUT_OPB_DECODER_WIRE = 2;
    private static final int OUT_CTRLALU_DECODER_WIRE = 3;
    private static final int OUT_CTRLDM_AF_DECODER_WIRE = 4;
    private static final int OUT_CTRLDM_RW_DECODER_WIRE = 5;
    private static final int OUT_CST_DECODER_WIRE = 6;
    
    
    
    public Decoder(int id, String description, Vector<Wire_Model> wireInput, Vector<Wire_Model> wireOutput)
    {
      super(id, TYPE_LOGIC_DECODER, description, wireInput, wireOutput);
      checkWireNumber(wireInput.size(), wireOutput.size(), 1, 7);
    }

    @Override
    public void action() {  
        boolean [] tab = new boolean[14];
        tab = this.input.get(IN_READ_DECODER_WIRE).getData();
        boolean [] opcode = new boolean[3];
        boolean [] ctrlDM_RW =  new boolean[1];
        boolean [] ctrlDM_AF =  new boolean[1];
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
                adopA[i-3]=tab[i];
            }
           for (int i =6; i<9;i++)
            {
                adopB[i-6]=tab[i];
            }
           for (int i =9; i<12;i++)
            {
                ctrlALU[i-9]=tab[i];
            }
           for (int i =0; i<8;i++)
            {
                cst[i]= false;
            }
           ctrlDM_RW[0] = opcode[0]; // si 1 alors vers reg si 0 vers pc
           ctrlDM_AF[0] = true; // pour active sinon false
           
        }
        /// si l'opcode est égale à 001 veut dire cst -> pc bra
        if(opcode[0]== false && opcode[1]== false && opcode[2]== true )
        {
           for (int i =6; i<14;i++)
            {
                cst[i-6]=tab[i];
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
           ctrlDM_RW[0] = opcode[0]; // si 1 alors vers reg si 0 vers pc
           ctrlDM_AF[0] = true; // pour active sinon false
        }
        
        /// si opcode est égale à 010, veut dire jump pc+cst -> pc
        if(opcode[0]== false && opcode[1]== true && opcode[2]== false)
        {
           for (int i =3; i<6;i++)
            {
                ctrlALU[i-3]=tab[i];
            }
           for (int i =6; i<14;i++)
            {
                cst[i-6]=tab[i];
            }
           for (int i =0; i<3;i++)
            {
                adopA[i]=false;
            }
           for (int i =0; i<3;i++)
            {
                adopB[i]=false;
            }
           ctrlDM_RW[0] = opcode[0]; // si 1 alors vers reg si 0 vers pc
           ctrlDM_AF[0] = true; // pour active sinon false
        }
        
        /// si opcode est égale à 011, veut dire bnz si z == 0 pc+cst -> pc sinon pc +1 ->pc
        if(opcode[0]== false && opcode[1]== true && opcode[2]== true)
        {
           for (int i =6; i<14;i++)
            {
                cst[i-6]=tab[i];
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
           ctrlDM_RW[0] = opcode[0]; // si 1 alors vers reg si 0 vers pc
           ctrlDM_AF[0] = true; // pour active sinon false
        }
        
        /// si opcode est égae à 100, veut dire alu(A, cst) -> A
        if(opcode[0]== true && opcode[1]== false && opcode[2]== false)
        {
           for (int i =3; i<6;i++)
            {
                adopA[i-3]=tab[i];
            }
           for (int i =6; i<14;i++)
            {
                cst[i-6]=tab[i];
            }
           for (int i =0; i<3;i++)
            {
                adopB[i]=false;
            }
           for (int i =0; i<3;i++)
            {
                ctrlALU[i]=opcode[i];
            }
           ctrlDM_RW[0] = opcode[0]; // si 1 alors vers reg si 0 vers pc
           ctrlDM_AF[0] = true; // pour active sinon false
        }
        this.dataOut = opcode;
        Helper_Controller.putBufferDataInWire(this.output.get(OUT_OPCODE_DECODER_WIRE), dataOut);
        this.dataOut = adopA;
        Helper_Controller.putBufferDataInWire(this.output.get(OUT_OPA_DECODER_WIRE), dataOut);
        this.dataOut = adopB;
        Helper_Controller.putBufferDataInWire(this.output.get(OUT_OPB_DECODER_WIRE), dataOut);
        this.dataOut = cst;
        Helper_Controller.putBufferDataInWire(this.output.get(OUT_CST_DECODER_WIRE), dataOut);
        this.dataOut = ctrlALU;
        Helper_Controller.putBufferDataInWire(this.output.get(OUT_CTRLALU_DECODER_WIRE), dataOut);
        this.dataOut = ctrlDM_RW;
        Helper_Controller.putBufferDataInWire(this.output.get(OUT_CTRLDM_RW_DECODER_WIRE), dataOut);
        this.dataOut = ctrlDM_AF;
        Helper_Controller.putBufferDataInWire(this.output.get(OUT_CTRLDM_AF_DECODER_WIRE), dataOut);
    }
}
