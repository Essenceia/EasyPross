package Controller;

import Model.AbstractClasses.NodeModel_Abstract;
import Model.Probe.ProbeEndModel;
import Model.Probe.ProbeStartModel;
import Model.Wire.WireModel;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
//import Model.AbstractClasses.ObjectModel_Abstract;

public class SimplifiedGraphObjectController {

    private HashMap<Integer, SimplifiedGraphController> Msimp;

    /**
     * Contructeur du graphe simplifié
     *
     * @param gwire
     * @param gnode
     * @param gprobes
     * @param gprobend
     */
    public SimplifiedGraphObjectController(Map<Integer, WireModel> gwire, Map<Integer, NodeModel_Abstract> gnode, Map<Integer, ProbeStartModel> gprobes, Map<Integer, ProbeEndModel> gprobend) {

        Msimp = new HashMap<Integer, SimplifiedGraphController>(); // table de hachage du graph simplifié 
        SimplifiedGraphController tmp; // objet temporaire de la classe @SimplifiedGraphController
        int key = 0; // nous servira pour définir la clé dans la table de hachage

        //Obtenir les objets du type : Wire
        for (Map.Entry<Integer, WireModel> mapentry : gwire.entrySet()) {
            tmp = new SimplifiedGraphController();  // on crée notre objet temporaire
            WireModel wtmp = mapentry.getValue();  // on récupère l'objet de la classe WireModel et on le met dans wtmp

            // on rempli notre objet temporaire par les attributs de l'objet récupéré en paramètre
            tmp.setId(wtmp.getId());
            tmp.setType(wtmp.getType());
            tmp.setDescription(wtmp.getDescription());
            tmp.setBitSize(wtmp.getSizeBus());

            //on met notre objet temporaire à l'indice key dans notre table de hachage
            Msimp.put(key, tmp);
            key += 1;
        }

        //Obtenir les objets du type : Noeud
        for (Map.Entry<Integer, NodeModel_Abstract> mapentry : gnode.entrySet()) {
            tmp = new SimplifiedGraphController(); // on crée notre objet temporaire
            NodeModel_Abstract ntmp = mapentry.getValue(); // on récupère l'objet de la classe NodeModel_Abstract et on le met dans ntmp

            // on rempli notre objet temporaire par les attributs de l'objet récupéré en paramètre
            tmp.setId(ntmp.getId());
            tmp.setType(ntmp.getType());
            tmp.setDescription(ntmp.getDescription());
            tmp.setInput(tab_concatenate(ntmp.getInput()));
            tmp.setOutput(tab_concatenate(ntmp.getOutput()));

            //on met notre objet temporaire à l'indice key dans notre table de hachage
            Msimp.put(key, tmp);
            key += 1;
        }

        //Obtenir les objets du type : Probe d'entrée
        for (Map.Entry<Integer, ProbeStartModel> mapentry : gprobes.entrySet()) {
            tmp = new SimplifiedGraphController(); // on crée notre objet temporaire
            ProbeStartModel pstmp = mapentry.getValue(); // on récupère l'objet de la classe ProbeStartModel et on le met dans pstmp

            // on rempli notre objet temporaire par les attributs de l'objet récupéré en paramètre
            tmp.setId(pstmp.getId());
            tmp.setType(pstmp.getType());
            tmp.setDescription(pstmp.getDescription());
            tmp.setOutput(tab_concatenate(pstmp.getWire()));

            //on met notre objet temporaire à l'indice key dans notre table de hachage
            Msimp.put(key, tmp);
            key += 1;
        }

        //Obtenir les objets du type : Probe de sortie
        for (Map.Entry<Integer, ProbeEndModel> mapentry : gprobend.entrySet()) {
            tmp = new SimplifiedGraphController();// on crée notre objet temporaire
            ProbeEndModel petmp = mapentry.getValue();// on récupère l'objet de la classe ProbeEndModel et on le met dans petmp

            // on rempli notre objet temporaire par les attributs de l'objet récupéré en paramètre
            tmp.setId(petmp.getId());
            tmp.setType(petmp.getType());
            tmp.setDescription(petmp.getDescription());
            tmp.setInput(tab_concatenate(petmp.getWire()));

            //on met notre objet temporaire à l'indice key dans notre table de hachage
            Msimp.put(key, tmp);
            key += 1;
        }
    }

    /**
     * Serialise_to_file
     *
     * Classe permettant de sérialiser notre graphe simplifié
     *
     * @param file
     */
    public void serialise_to_file(String file) {
        try {
            try (FileOutputStream fos = new FileOutputStream(file) // on crée un flux d'entrée
                    ;
                     ObjectOutputStream oos = new ObjectOutputStream(fos) // on crée un objet dd'entrée
                    ) {
// on crée un objet dd'entrée
                oos.writeObject(Msimp);
                // fermeture de tous les fichiers
                // on sérialise notre table de hachage contenant le graphe simplifié
            } // on crée un objet dd'entrée
            // on crée un objet dd'entrée
            System.out.println("Le graph simplifier a etait serialiser dans " + file);
        } catch (IOException ioe) { // si la sérialisation a eu un problème
            ioe.printStackTrace();
        }
    }

    /**
     *
     * @param vw
     * @return tab
     */
    private int[] tab_concatenate(Vector<WireModel> vw) {
        int size = HelperController.count_wire_size(vw); // on compte le nombre de wire de vw pour obtenir la size du vector 
        int[] tab = new int[size];
        int j = 0;
        for (WireModel in : vw) {
            tab[j] = in.getId(); // on récupère l'id de chaque wire contenu dans vw et on le met dans le tableau
            j++;
        }
        return tab; // on retourn le tableau
    }
}
