/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easypross.View;

import easypross.Controller.GraphManagerController;
import easypross.Model.AbstractClasses.NodeModel_Abstract;
import easypross.Model.Probe.ProbeEndModel;
import easypross.Model.Probe.ProbeStartModel;
import easypross.Model.Wire.WireModel;
import java.awt.Graphics;
import java.awt.List;
import java.awt.RenderingHints.Key;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author julie
 */
public class SimulationView1 extends JPanel {

    GraphManagerController graph;
    private BufferedImage image;

    public SimulationView1(GraphManagerController g) {
        graph = g;

        try {
            /* Path file = FileSystems.getDefault().getPath("", "listImages.txt");
            Charset charset = Charset.forName("US-ASCII");
            try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
            image.add(ImageIO.read(new File("src\\mainPackage\\"+line)));
            }
            } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
            }*/
            image = ImageIO.read(new File("C:\\Users\\julie\\Documents\\Ecole Centrale d'Electronique\\INGs\\ING4\\PPE\\EasyPross\\EasyProssGit\\CodeByJuju\\src\\easypross\\View\\PlayWhite.png"));
        } catch (IOException ex) {
            Logger.getLogger(SimulationView1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void paint(Graphics g) {
        GraphManagerController Graph = new GraphManagerController();
        Graph.load_new_module("testfile.txt");
        System.out.println("Chargement des maps fait");
        //Graph.init_simplified_graph("serialtest.ser");
        
        Map<Integer, ProbeStartModel> probeStart = Graph.getDebut();
        int pSSize = probeStart.size();
        Set<Integer> keySetStart = probeStart.keySet();

        Map<Integer, ProbeEndModel> probeEnd = Graph.getFin();
        int pESize = probeEnd.size();
        Set<Integer> keySetEnd = probeEnd.keySet();

        Map<Integer, NodeModel_Abstract> Node = Graph.getNoeud();
        int pNSize = Node.size();
        Set<Integer> keySetNode = Node.keySet();

        for (int i = 0; i < pSSize; i++) {
            g.drawImage(image, (int) i * pSSize / 1000, (int) i * image.getHeight() * pSSize / 1000 + i * 100, image.getHeight(), image.getWidth(), this);
        }

        for (int i = 0; i < pESize; i++) {
            g.drawImage(image, 300 + (int) i * pESize / 1000, (int) i * image.getHeight() * pESize / 1000 + i * 100, image.getHeight(), image.getWidth(), this);
        }
        for (int i = 0; i < pNSize; i++) {
            g.drawImage(image, 150 + (int) i * pNSize / 1000, (int) i * image.getHeight() * pNSize / 1000 + i * 100, image.getHeight(), image.getWidth(), this);
        }
        for (Integer i : keySetStart) {
            Vector<WireModel> vectorWire;
            ProbeStartModel probe = probeStart.get(i);

            if (probe != null) {
                System.out.println("Probe id " + probe.getId());
                vectorWire = probe.getWire();
                if (vectorWire.isEmpty() == false) {
                    while (vectorWire.isEmpty() == false) {
                        System.out.println("Hellllo");
                        if (vectorWire.firstElement() != null) {
                            WireModel myWire = vectorWire.remove(0);
                            for (Integer j : keySetNode) {
                                System.out.println("Hellllo 222");
                                NodeModel_Abstract nodeOne = Node.get(7);
                                 System.out.println("Hellllo 232323");
                                if (nodeOne== null) {
                                    System.out.println("NodeOne Null");
                                }
                                else {
                                    System.out.println("Hellllo 3333");
                                    if (nodeOne.getInput().contains(myWire)) {
                                        System.out.println("Hellllo 44444");
                                        g.drawLine((int) i * pNSize / 1000 + image.getHeight(), (int) i * image.getHeight() * pNSize / 1000 + i * 100 + image.getWidth() / 2, 150 + (int) j * pNSize / 1000, (int) j * image.getHeight() * pNSize / 1000 + j * 100 + image.getWidth() / 2);
                                    }
                                }
                                 System.out.println("Hellllo 55555");
                            }
                        } else System.out.println("Wire Null");
                    }
                }
                else  System.out.println("vector Wire empty");
            }
        }

        //g.drawImage(image, x, y, w, z, this);
        // g.drawLine(x, y, w, z);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        GraphManagerController Graph = new GraphManagerController();
        Graph.load_new_module("testfile.txt");
        System.out.println("Chargement des maps fait");
        Graph.init_simplified_graph("serialtest.ser");
        frame.getContentPane().add(new SimulationView1(Graph));
        frame.setSize(600, 400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }
}
