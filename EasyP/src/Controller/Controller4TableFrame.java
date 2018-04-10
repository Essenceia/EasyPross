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
        Iterator itWire;
        HashMap<Integer, Wire> wireList;
        //  List<Node<T>> listRegisterNodes = new ArrayList<Node<T>>();
        if (graph != null) {
            wireList = graph.getWireMap();
            listRegisterNodes = graph.getRegisterNodesID(listRegisterNodes);//getNode().getValue().size();
            int sizeTick = 1;
            if (listRegisterNodes != null) {
                sizeTick = listRegisterNodes.get(0).getValue().size();
            }
            List<String> columnNames = new ArrayList<String>();
            columnNames.add("Name register");
            for (int i = 1; i < sizeTick + 1; i++) {
                columnNames.add("t" + i);
            }
            for (int i = 0; i < columnNames.size(); i++) {
                final int finalIdx = i;
                TableColumn<ObservableList<String>, String> column = new TableColumn<>(
                        columnNames.get(i)
                );
                column.setCellValueFactory(param ->
                        new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))
                );
                tView.getColumns().add(column);
            }
            //For each component :
            for (Node<T> l : listRegisterNodes) {

                //If the component's history must be displayed :
                if ((root) || (l.getPos_x() == pos_x && l.getPos_y() == pos_y && l.getWidth() == width && l.getHeight() == height)) {

                    //Create an empy arraylist representing the line;
                    ArrayList<String> row = new ArrayList<>();

                    //Add the name of the component at index 0 (left column)
                    row.add(l.getName());

                    //For each value the component has taken :
                    for (int i = 0; i < columnNames.size(); i++) {

                        //Add the value at the current column;
                        row.add(l.getValueAtIndex(i));
                    }

                    //Add the line to the tableView;
                    tView.getItems().add(FXCollections.observableArrayList(row));

                }
            }
            //For each component :
            itWire = graph.getWire();
            while (itWire.hasNext()) {

                //If the component's history must be displayed :
                if ((root) || (l.getPos_x() == pos_x && l.getPos_y() == pos_y && l.getWidth() == width && l.getHeight() == height)) {

                    //Create an empy arraylist representing the line;
                    ArrayList<String> row = new ArrayList<>();

                    //Add the name of the component at index 0 (left column)
                    row.add(l.getName());

                    //For each value the component has taken :
                    for (int i = 0; i < columnNames.size(); i++) {

                        //Add the value at the current column;
                        row.add(l.getValueAtIndex(i));
                    }

                    //Add the line to the tableView;
                    tView.getItems().add(FXCollections.observableArrayList(row));

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
