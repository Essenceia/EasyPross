package Controller;

import java.util.*;

import com.sun.javafx.css.converters.StringConverter;

import Model.Graph;
import Model.Node;
import Model.Wire;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import sun.security.krb5.internal.rcache.AuthTimeWithHash;

public class Controller4TableFrame implements Interface4TableEventHandler{


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
    
    private API_IHM api;


    public API_IHM getApi() {
		return api;
	}

	public void setApi(API_IHM api) {
		this.api = api;
	}

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
                System.out.println(obs.getValue()+"old "+oldSelection+"new selection "+newSelection);
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
        HashMap<Integer,ArrayList<String>> histories = graph.getHistory();

        //Cache component names;
        HashMap<String,Integer> componentNames = graph.getRegName();

        //Determine the number of ticks, as the number of elements of a line;
        int nbTicks = graph.getTime()+1;
        System.out.println("Time "+nbTicks);

        /*
         * First, let's build the first line that will contain column names;
         */

        //Create the arrayList;
        ArrayList<String> columnNames = new ArrayList<>();

        //Add the first column name;
        columnNames.add("Name register");


        //Add each tick's id;
        for (int i = 0; i <= nbTicks; i++) {
            columnNames.add("t" + i);
            System.out.println("Collon name "+"t" + i);
        }

        System.out.println("Collum name size "+columnNames.size());
        //Set the table's columns;
        for (int i = 0; i < columnNames.size(); i++) {//NO FUCKING IDEA OF WHAT IT DOES...
            final int finalIdx = i;
            //set le nom de la collone
            System.out.println("Populating collum "+columnNames.get(i));
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnNames.get(i));
            column.setCellValueFactory(param ->
                    new ReadOnlyObjectWrapper<>(
                            param.getValue().get(finalIdx)));
            tView.getColumns().add(column);
            //ObservableList<String> ListString = FXCollections.observableArrayList(histories.get(i));
            column.setSortable(false);

            if(finalIdx==columnNames.size()-1){
                column.setCellFactory(TextFieldTableCell.forTableColumn());
              //  EventHandler<CellEditEvent<ObservableList<String>, String>> value =  null;
  
               // column.setOnEditCommit(value);
                column.setOnEditCommit(
                        new EventHandler<CellEditEvent<ObservableList<String>, String>>() {
                           @Override
							public void handle(CellEditEvent<ObservableList<String>, String> event) {
								event.getTableView().getItems().get(event.getTablePosition().getRow()).set(finalIdx, event.getNewValue());
								String coName = event.getTableView().getItems().get(event.getTablePosition().getRow()).get(0);
								int id = graph.getRegName().get(coName);
								Data_Tuple data = new Data_Tuple(id,event.getNewValue().toString()  );
								Vector<Data_Tuple> v = new Vector<Data_Tuple>();
								v.add(data);
								api.APISender(4, "s", 0, null, null, v);
							}
                        }
                    );
                column.setEditable(true);
                column.setResizable(true);
            }
        }
        
        /*
         * Now, we can fill the table's content;
         */
        tView.setEditable(true);
        String defaut = "0";
        //If the whole history must be displayed :
        if (root) {

            //For each component :
            for (String name:componentNames.keySet() ) {

                Integer nameLine = componentNames.get(name);
                //get array list on objects
                ArrayList<String> regValues= new ArrayList<>();
                regValues.add(0,name);
                regValues.addAll(1,histories.get(nameLine));
                System.out.println("Values :: "+regValues.toString());

                if(regValues.size()<columnNames.size()){
                    while (regValues.size()!=columnNames.size()){
                        regValues.add(defaut);
                    }
                }
                ObservableList<String> ListString = FXCollections.observableArrayList(regValues);
                System.out.println("list :: "+ListString.toString());


                //Add the line to the tableView;
                tView.getItems().add(ListString);

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
   // @FXML
    @Override
    public void setOnEditCommit(CellEditEvent<?,?> event){
        Object newValue = event.getNewValue();
        // other data that might be helpful:
        TablePosition<?,?> position = event.getTablePosition();
        int row = position.getRow();
        // etc ...
        System.out.println(row);
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
