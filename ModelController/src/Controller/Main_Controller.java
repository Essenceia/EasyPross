/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

/**
 *
 * @author Utilisateur
 */
public class Main_Controller {

    public static void main(String[] args) {
        //test register model
        //XML_Manager_Controller manager = new XML_Manager_Controller();
       // manager.openFile("testxml.xml");
        Graph_Manager_Controller Graph = new Graph_Manager_Controller();
        Graph.load_new_module("testdecoder.xml");
        Graph.tick(); //appelle de tick avec la fonction action de not
        
}
   
}
