package Controller;
import Model.Normal_Classes.Probe.Probe_End_Model;
import Model.Normal_Classes.Probe.Probe_Start_Model;
import java.util.Map;
import java.util.Scanner;

public class Main_Controller {

    public static void main(String[] args) {
        //test register model
        //XML_Manager_Controller manager = new XML_Manager_Controller();
       // manager.openFile("testxml.xml");
        Graph_Manager_Controller Graph = new Graph_Manager_Controller();
        Graph.load_new_module("testprobe_avec_and.xml");

        Graph.tick(); //appelle de tick avec la fonction action de not
        Graph.save_module("save_progmemory.xml");
}
   
}
