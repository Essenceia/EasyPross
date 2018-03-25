package Controller;
import Model.Normal_Classes.Probe.Probe_End_Model;
import Model.Normal_Classes.Probe.Probe_Start_Model;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main_Controller {

    public static void main(String[] args) {
        boolean nv[] = new boolean[1];
        nv[0] = true;
        //test register model
        //XML_Manager_Controller manager = new XML_Manager_Controller();
       // manager.openFile("testxml.xml");
        Graph_Manager_Controller Graph = new Graph_Manager_Controller();
        Graph.load_new_module("XML_tests/testcomplexePcProgDecode.xml");

        Graph.tick();
        Graph.save_module("XML_tests/save_complexePcProgDecode_0.xml");

        Graph.getDebut().get(40).setData(nv);//setting pc to read data
        Graph.tick();
        Graph.save_module("XML_tests/save_complexePcProgDecode_1.xml");
        Graph.getDebut().get(42).setData(nv);//setting pc to write data
        Graph.tick();
        Graph.save_module("XML_tests/save_complexePcProgDecode_2.xml");
        Graph.tick();
        Graph.save_module("XML_tests/save_complexePcProgDecode_3.xml");
        Graph.tick();
        Graph.save_module("XML_tests/save_complexePcProgDecode_4.xml");


    }
   
}
