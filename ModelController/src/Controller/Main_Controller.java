package Controller;

/**
 *
 * @author rick from rick and morty ^w^
 */
public class Main_Controller {

    public static void main(String[] args) {
        //test register model
        //XML_Manager_Controller manager = new XML_Manager_Controller();
       // manager.openFile("testxml.xml");
        Graph_Manager_Controller Graph = new Graph_Manager_Controller();

        Graph.load_new_module("test_progmemory.xml");

        Graph.tick(); //appelle de tick avec la fonction action de not
        Graph.save_module("save_progmemory.xml");
}
   
}
