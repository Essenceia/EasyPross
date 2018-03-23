package Controller;
import Model.Normal_Classes.Probe.Probe_End_Model;
import Model.Normal_Classes.Probe.Probe_Start_Model;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main_Controller {

    public static void main(String[] args) {
        //test register model
        //XML_Manager_Controller manager = new XML_Manager_Controller();
       // manager.openFile("testxml.xml");
        Graph_Manager_Controller Graph = new Graph_Manager_Controller();
        Graph.load_new_module("XML_tests/testmux.xml");


        Graph.tick(); //appelle de tick avec la fonction action de AND
        
        /*Map<Integer, Probe_End_Model> fin;
        fin = Graph.getFin();
        Probe_End_Model sortie;
        sortie = fin.get(6);
        boolean [] sort;
        sort = sortie.getData();
        for(int i=0; i<sortie.getData().length;i++)
        {
            System.out.println(sort[i]);
        }
        HashMap<Integer, Probe_Start_Model> debut;
        debut = Graph.getDebut();
        boolean [] entr = new boolean [debut.get(1).getWireSize()]; 
        Scanner sc = new Scanner(System.in);
        System.out.println("Veuillez saisir la valeur de l'entrée(true ou false) que vous souhaitez :");
        String str = sc.nextLine();
        if("false".equals(str)) 
        {
            for(int i=0;i<debut.get(1).getWireSize();i++)
            {
              entr[i]=false;  
            }
                
        }
        else
            if("true".equals(str))
            {
                for(int i=0;i<debut.get(1).getWireSize();i++)
                {
                    entr[i]=true;  
                }
            }
        Probe_Start_Model entree;
        for(int i=1;i<=debut.size();i=i+2)
        {
            entree = debut.get(i);
            entree.setData(entr);
            debut.put(i, entree); 
        }
        Graph.setDebut(debut);
        Graph.tick();
        sortie = fin.get(6);
        sort = sortie.getData();
        for(int i=0; i<sortie.getData().length;i++)
        {
            System.out.println(sort[i]);
        }*/
        //Graph.save_module("save_progmemory.xml");
}
   
}
