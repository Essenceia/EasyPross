package Controller;


import Model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Graph_simplifie_objet implements java.io.Serializable{
    
    private HashMap<Integer,Graph_simplifie> Msimp;
    
    public Graph_simplifie_objet(Map<Integer,Wire> gwire, Map<Integer, Node_Base> gnode, Map<Integer,Probe_Start>gprobes, Map<Integer,Probe_End>gprobend)
    {
        Msimp = new HashMap<Integer, Graph_simplifie>();
        Graph_simplifie tmp;
        int key=0;
        for(Map.Entry<Integer,Wire> mapentry : gwire.entrySet()){
            tmp = new Graph_simplifie();
            Wire wtmp = mapentry.getValue();
            tmp.setID(wtmp.get_id());
            tmp.setType(wtmp.get_type());
            tmp.setdescrip(wtmp.getDescription());
            tmp.setBit(wtmp.get_data_size());
            Msimp.put(key, tmp);
            key+=1;
        }
        for(Map.Entry<Integer,Node_Base> mapentry : gnode.entrySet()){
            tmp = new Graph_simplifie();
            Node_Base ntmp = mapentry.getValue();
            tmp.setID(ntmp.get_id());
            tmp.setType(ntmp.get_type());
            tmp.setdescrip(ntmp.getDescription());
            tmp.setentree(tab_concatenate(ntmp.getWireIn()));
            tmp.setsortie(tab_concatenate(ntmp.getWireOut()));
            Msimp.put(key, tmp);
            key+=1;
        }
        for(Map.Entry<Integer,Probe_Start> mapentry : gprobes.entrySet()){
            tmp = new Graph_simplifie();
            Probe_Start pstmp = mapentry.getValue();
            tmp.setID(pstmp.get_id());
            tmp.setType(pstmp.get_type());
            tmp.setdescrip(pstmp.getDescription());
            tmp.setsortie(tab_concatenate(pstmp.getwire()));
            Msimp.put(key, tmp);
            key+=1;
        }
        for(Map.Entry<Integer,Probe_End> mapentry : gprobend.entrySet()){
            tmp = new Graph_simplifie();
            Probe_End petmp = mapentry.getValue();
            tmp.setID(petmp.get_id());
            tmp.setType(petmp.get_type());
            tmp.setdescrip(petmp.getDescription());
            tmp.setentree(tab_concatenate(petmp.getwire()));
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
    private int[] tab_concatenate(Vector<Wire> vw)
    {
        int size =helper.count_wire_size(vw);
       int[] tab= new int[size];
       int j=0;
       for(Wire in:vw)
       {
           tab[j] = in.get_id();
           j++;
       }
        return tab;
    }
    
}
