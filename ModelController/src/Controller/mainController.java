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
public class mainController {

    public static void main(String[] args) {
        XMLManager manager = new XMLManager();
        manager.open_file("testxml.xml");
        GraphManagerController Graph = new GraphManagerController();
        Graph.load_new_module("testxml.xml");
       // System.out.println("Chargement des map fait");
        Graph.tick(); //appelle de tick avec la fonction action de not
}
   
}
