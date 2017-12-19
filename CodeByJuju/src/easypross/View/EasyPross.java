/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easypross.View;

import easypross.Controller.GraphManagerController;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


/**
 *
 * @author julie
 */
public class EasyPross  {
    public AsmView asm;
    public ModuleView module;
    public SimulationView simulation;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        functionTest1();
    }
    /**
     * Functions for tests
     */
    public static void functionTest1(){
        GraphManagerController Graph = new GraphManagerController();
        Graph.load_new_module("testfile.txt");
        System.out.println("Chargement des maps fait");
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
