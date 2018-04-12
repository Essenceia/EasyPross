package Model.Normal_Classes.Logic.Gate;

import Model.Abstract_Classes.Logic_Model_Abstract;
import Model.Normal_Classes.Wire.Wire_Model;
import java.util.Arrays;
import java.util.Vector;

public class And_Model extends Logic_Model_Abstract{

    private static final int IN_NBBITS_AND = 0;
    private int nbBit;
    
    
    /**
     *
     * @param id
     * @param wireInput
     * @param wireOutput
     */
    public And_Model(int id, Vector<Wire_Model> wireInput, Vector<Wire_Model> wireOutput) {
        super(id, TYPE_LOGIC_AND, " AND", wireInput, wireOutput);
        this.nbBit =wireInput.get(IN_NBBITS_AND).getSizeBus();
    }

    /**
     *
     */
    @Override
    public void action() {
        int nbfil;
        nbfil=this.input.size();
        Vector<boolean []> fil = new Vector(nbfil);
        int max_size =0;
        for(Wire_Model wire_in:this.input)
        {
          fil.add(wire_in.getData());
          if(wire_in.getData().length> max_size)
          {
              max_size = wire_in.getData().length;
          }
        }
        boolean [] resultat = new boolean[max_size];
        int i=0;
        Arrays.fill(resultat, true);
        for(boolean [] recup:fil)
        {
           i=0; 
          for(boolean b:recup)
          {
            resultat[i++]&=b;  
          }
        }
        dataOut=resultat;
        putOutputingData();
    }
}
