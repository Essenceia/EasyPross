package Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import Model.Graph;
import Model.Node;
import Model.Wire;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class Controller4TableFrame {


    @FXML
    private TableView<ObservableList<String>> tView;


    @FXML
    private TextField ttext;

    @FXML
    private Button tbutton;

    private Graph graph;

    private String fileMod;
    private String PATH_FILE = System.getProperty("user.dir") + System.getProperty("file.separator") + "src";
    private int pos_x;
    private int pos_y;
    private int width;
    private int height;
    private boolean root;

    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    public int getPos_x() {
        return pos_x;
    }

    public void setPos_x(int pos_x) {
        this.pos_x = pos_x;
    }

    public int getPos_y() {
        return pos_y;
    }

    public void setPos_y(int pos_y) {
        this.pos_y = pos_y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Controller4TableFrame() {
        tView = new TableView();
        tView.getSelectionModel().selectedIndexProperty().addListener((obs, oldSelection, newSelection) -> {
            System.out.println("SUUUUUS");
            if (newSelection != null) {
                System.out.println(newSelection);
            }
        });
        fileMod = "";
        root = false;
    }

    /**
     * Method update, to update the table view of the window associated
     */
    public <T> void update() {

        tView.getItems().clear();
        tView.getColumns().clear();

        //If the graph doesn't exist, fail;
        if (graph == null)
            return;

        //Cache its history;
        ArrayList<ArrayList<String>> histories = graph.getHistory();

        //Cache component names;
        ArrayList<String> componentNames = graph.getRegName();

        //Determine the number of ticks, as the number of elements of a line;
        int nbTicks = histories.get(0).size();


        /*
         * First, let's build the first line that will contain column names;
         */

        //Create the arrayList;
        ArrayList<String> columnNames = new ArrayList<>();

        //Add the first column name;
        columnNames.add("Name register");

        //Add each tick's id;
        for (int i = 0; i < nbTicks; i++) {
            columnNames.add("t" + i);
        }

        //Set the table's columns;
        for (int i = 0; i < columnNames.size(); i++) {//NO FUCKING IDEA OF WHAT IT DOES...
            final int finalIdx = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnNames.get(i));
            column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx)));
            tView.getColumns().add(column);
        }

        /*
         * Now, we can fill the table's content;
         */

        //If the whole history must be displayed :
        if (root) {

            //For each component :
            for (int componentId = 0; componentId < histories.size(); componentId++) {


                //Create an empy arraylist representing the line;
                ArrayList<String> row = new ArrayList<>();

                //Add the name of the component at index 0 (left column)
                row.add(componentNames.get(componentId));

                //Add all values of the history in the line;
                row.addAll(histories.get(componentId));

                //Add the line to the tableView;
                tView.getItems().add(FXCollections.observableArrayList(row));

            }

        } else {
            //If the history of the clicked point must be displayed :
            Iterator tmpn = graph.getNonRegisterNodes();
            while (tmpn.hasNext()){
            Node component = (Node)tmpn.next(); ;/*TODO ITERER SUR TOUS LES COMPOSANTS A L'ECRAN*/
            tmpn.remove();
                //If we clicked into the component :
                if (component.getPos_x() == pos_x &&
                        component.getPos_y() == pos_y &&
                        component.getWidth() == width &&
                        component.getHeight() == height) {
                    Iterator tmpwire = component.get_connected();
                    while (tmpwire.hasNext()){
                        Wire register = (Wire) tmpwire.next();
                        tmpwire.remove();
                        ArrayList<String> row = new ArrayList<>();
                        //Add the name of the component at index 0 (left column)
                        row.add(register.getName());

                        //Add all values of the history in the line;
                        row.addAll(register.getValue());

                        //Add the line to the tableView;
                        tView.getItems().add(FXCollections.observableArrayList(row));
                    }


                    //As a component has been found, stop here;
                    return;

                }

            }

        }

    }
    public TableView gettView() {
        return tView;
    }

    public void settView(TableView tView) {
        this.tView = tView;
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public String getFileMod() {
        return fileMod;
    }

    public void setFileMod(String fileMod) {
        this.fileMod = fileMod;
    }

    public String getPATH_FILE() {
        return PATH_FILE;
    }

    public void setPATH_FILE(String pATH_FILE) {
        PATH_FILE = pATH_FILE;
    }
}
