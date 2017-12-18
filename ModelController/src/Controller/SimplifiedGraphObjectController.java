package Controller;

import Model.AbstractClasses.NodeModel_Abstract;
import Model.Probe.ProbeEndModel;
import Model.Probe.ProbeStartModel;
import Model.Wire.WireModel;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
//import easypross.Model.AbstractClasses.ObjectModel_Abstract;

public class SimplifiedGraphObjectController {
    private HashMap<Integer,SimplifiedGraphController> Msimp;
    public SimplifiedGraphObjectController(Map<Integer,WireModel> gwire, Map<Integer, NodeModel_Abstract> gnode, Map<Integer,ProbeStartModel>gprobes, Map<Integer,ProbeEndModel>gprobend) {
        Msimp = new HashMap<Integer, SimplifiedGraphController>();
        SimplifiedGraphController tmp;
        int key=0;
        for(Map.Entry<Integer,WireModel> mapentry : gwire.entrySet()){
            tmp = new SimplifiedGraphController();
            WireModel wtmp = mapentry.getValue();
            tmp.setId(wtmp.getId());
            tmp.setType(wtmp.getType());
            tmp.setDescription(wtmp.getDescription());
            tmp.setBitSize(wtmp.getSizeBus());
            Msimp.put(key, tmp);
            key+=1;
        }
        for(Map.Entry<Integer,NodeModel_Abstract> mapentry : gnode.entrySet()){
            tmp = new SimplifiedGraphController();
            NodeModel_Abstract ntmp = mapentry.getValue();
            tmp.setId(ntmp.getId());
            tmp.setType(ntmp.getType());
            tmp.setDescription(ntmp.getDescription());
            tmp.setInput(tab_concatenate(ntmp.getInput()));
            tmp.setOutput(tab_concatenate(ntmp.getOutput()));
            Msimp.put(key, tmp);
            key+=1;
        }
        for(Map.Entry<Integer,ProbeStartModel> mapentry : gprobes.entrySet()){
            tmp = new SimplifiedGraphController();
            ProbeStartModel pstmp = mapentry.getValue();
            tmp.setId(pstmp.getId());
            tmp.setType(pstmp.getType());
            tmp.setDescription(pstmp.getDescription());
            tmp.setOutput(tab_concatenate(pstmp.getWire()));
            Msimp.put(key, tmp);
            key+=1;
        }
        for(Map.Entry<Integer,ProbeEndModel> mapentry : gprobend.entrySet()){
            tmp = new SimplifiedGraphController();
            ProbeEndModel petmp = mapentry.getValue();
            tmp.setId(petmp.getId());
            tmp.setType(petmp.getType());
            tmp.setDescription(petmp.getDescription());
            tmp.setInput(tab_concatenate(petmp.getWire()));
            Msimp.put(key, tmp);
            key+=1;
        }
    }
    public void serialise_to_file(String file){
        try {
            FileOutputStream fos =
                    new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(Msimp);
            oos.close();
            fos.close();
            System.out.println("Le graph simplifier a etait serialiser dans "+file);
        }catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
    private int[] tab_concatenate(Vector<WireModel> vw)
    {
        int size =HelperController.count_wire_size(vw);
       int[] tab= new int[size];
       int j=0;
       for(WireModel in:vw)
       {
           tab[j] = in.getId();
           j++;
       }
        return tab;
    }
}
