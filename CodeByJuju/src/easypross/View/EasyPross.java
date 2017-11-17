/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easypross.View;

import easypross.Controller.GraphManagerController;


/**
 *
 * @author julie
 */
public class EasyPross {
    public AsmView asm;
    public ModuleView module;
    public SimulationView simulation;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    	System.out.println("coucou");
        GraphManagerController Graph = new GraphManagerController();
        Graph.load_new_module("testfile.txt");
        System.out.println("Chargement des map fait");
        Graph.init_simplified_graph("serialtest.ser");
    }
    /**
     * Methods
     */
    public void selectModule() {
    }
    public void displayDescription() {
    }

    public void readDescription() {
    }
    
}
