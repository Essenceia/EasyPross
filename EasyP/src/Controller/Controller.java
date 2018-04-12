package Controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import Model.Graph;
import Model.Node;
import Model.Register;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private Button buttonChooseModule;
    @FXML
    private ListView<String> listView;
    @FXML
    private Label labelDescription;
    @FXML
    private ImageView imageView;
    @FXML
    private Pane pane;
    @FXML
    private Button playButton;
    @FXML
    private Button tickButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Button stopButton;
    @FXML
    private Label labelASM;
    private String[] listViewFile;
    private XMLtoGraph graph;
    private boolean moduleSelected;
    private String fileMod;

    private boolean play = false;
    private boolean pause = false;
    private boolean stop = false;
    private boolean tick = false;

    private Stage stageSecond;
    private Stage stage;
    private Controller4TableFrame controllerMainFrame;
    private API_IHM api;


    public Controller() {
        api = new API_IHM();
        stageSecond = new Stage();
        buttonChooseModule = new Button();
        imageView = new ImageView();
        labelDescription = new Label();
        labelASM = new Label();
        listView = new ListView<String>();
        FileReader f = new FileReader(Config.PATH_FILE + "modules.txt");
        listViewFile = f.readFile();

    }

    public String getFileMod() {
        return fileMod;
    }

    public void setFileMod(String fileMod) {
        this.fileMod = fileMod;
    }

    /**
     * Method to initialize the window (stage)
     */
    @FXML
    void initialize() {
        if (listView.getItems().isEmpty()) {
            for (String line : listViewFile) {
                listView.getItems().add(line.trim());
            }
        }

        //listView.getSelectionModel().select(0); // select first item by default
        if (moduleSelected == true) {
        	stage = null;
        	String file = listView.getSelectionModel().getSelectedItem().toString();
            loadModuleDescription(file);
            moduleSelected = false;
        }

    }

    /**
     * callback when button on first frame is clicked
     *
     * @param e
     */
    @FXML
    public void buttonChooseModuleOnAction(ActionEvent e) {
        if (stageSecond.isShowing() == false) {
            moduleSelected = true;

            Pane root;
            try {
                FXMLLoader f = new FXMLLoader(getClass().getResource("second.fxml"));
                f.setController(this);
                root = (Pane) f.load();
                stageSecond.setScene(new Scene(root));
                stageSecond.show();

            } catch (IOException eu) {
                eu.printStackTrace();
            }
        }

    }

    /**
     * Method to go through the graph and look at the mouse coordinates
     * -> display new window with data if necessary
     *
     * @param mousePosX
     * @param mousePosY
     * @return
     */
    public boolean goThroughGraph(double mousePosX, double mousePosY) {
        boolean found = false;

        Iterator itNode = this.graph.getRoot().getNonRegisterNodes();
        Iterator itReg = this.graph.getRoot().getDataRegisterNodes();

        Register reg;
        Node node;

        //for registers
        while (itReg.hasNext()){
            reg = (Register)itReg.next();

           /* double minx = reg.getPos_x() * imageView.getFitWidth() / reg.getWidth();
            double miny = reg.getPos_y() * imageView.getFitHeight() / reg.getHeight();

            double maxx = minx + reg.getWidth() * imageView.getFitWidth() / reg.getWidth();
            double maxy = miny + reg.getHeight()*imageView.getFitHeight() / reg.getHeight();*/

            double minx = reg.getPos_x() * imageView.getFitWidth() / reg.getWidth();
            double miny = reg.getPos_y() * imageView.getFitHeight() / reg.getHeight();

            double maxx = minx + reg.getWidth() * imageView.getFitWidth() / reg.getWidth();
            double maxy = miny + reg.getHeight()*imageView.getFitHeight() / reg.getHeight();


            if (reg.checkCursotFit(mousePosX,mousePosY)) {
                    //ask for data on register
                    api.getDataRegisterSimul(6,reg.getId());
                    if(api.a.check==1){
                            if (reg.getStage() != null) {
                                reg.getStage().close();
                                reg.setStage(null);
                            }
                            reg.setPath(api.a.path);
                            Stage s = new Stage();
                            Pane root;
                            try {
                                FXMLLoader f = new FXMLLoader(getClass().getResource("fourth.fxml"));
                                root = (Pane) f.load();
                                s.setScene(new Scene(root));
                                s.show();

                                ControllerMemory controllerMemory = f.getController();
                                controllerMemory.loadMemory(reg.getPath(), reg.getType(),reg);

                            } catch (IOException eu) {
                                eu.printStackTrace();
                            }
                            reg.setStage(s);

                    }
                     return true;
                }

        }
        
        //for nodes
        while (itNode.hasNext()) {
            node = (Node) itNode.next();

            double minx = node.getPos_x() * imageView.getFitWidth() / node.getWidth();
            double miny = node.getPos_y() * imageView.getFitHeight() / node.getHeight();

            double maxx = minx + node.getWidth() * imageView.getFitWidth() / node.getWidth();
            double maxy = miny + node.getHeight() * imageView.getFitHeight() / node.getHeight();

            if (node.checkCoordFit(minx,miny,maxx,maxy)) {
                    //ask for data on nodeister
                    if (node.getStage() != null) {
                        node.getStage().close();
                        node.setStage(null);
                    }
                    Stage s = new Stage();
                    Pane root;
                    try {
                        FXMLLoader f = new FXMLLoader(getClass().getResource("third.fxml"));
                        root = (Pane) f.load();
                        s.setScene(new Scene(root));
                        s.show();
                        Controller4TableFrame controller = f.getController();
                        controller.setGraph(graph.getRoot());
                        controller.setPos_x(node.getPos_x());
                        controller.setPos_y(node.getPos_y());
                        controller.setWidth(node.getWidth());
                        controller.setHeight(node.getHeight());
                        controller.update();

                    } catch (IOException eu) {
                        eu.printStackTrace();
                    }
                    node.setStage(s);
                    return true;

            }

        }



        return false;

    }

    /**
     * Method to load the description of the module and display it on the screen
     * methods to display image and ASM file called
     *
     * @param file
     */
    public void loadModuleDescription(String file) {
        FileReader fileDescriptionModule;
        fileDescriptionModule = new FileReader(Config.PATH_FILE + file + ".txt");
        String[] content = fileDescriptionModule.readFile();
        String str = "";
        str = String.join("\n", content);
        if (labelDescription != null) {
            labelDescription.setText(str); 
            displayImage(file);
        }
        graph=null;
        graph = new XMLtoGraph(Config.PATH_FILE + file);
        if(controllerMainFrame!=null) {
        	controllerMainFrame.update();
        }
        /*****************/
        /***Ask Modu API**/
        /*****************/
        //todo have to unify descriptions of modules
        loadModule(file);
        String p = Config.PATH_BASE + "fichier.txt";// "C:\\Users\\julie\\Desktop\\EasyP\\fichier.txt";// askModule(file,"S");
        /*****************/
        /***	END		 **/
        /***Ask Modu API**/
        /*****************/
        FileReader fileASM;
        fileASM = new FileReader(p);
        String[] contentASM = fileASM.readFile();
        String strASM = "";
        strASM = String.join("\n", contentASM);
        if (labelASM != null) {
            labelASM.setText(strASM);
        }
    }

    public String getPATH_FILE() {
        return Config.PATH_FILE;
    }

	/*
	Mis en commentaire car tu ne l'utiliser pas et que sa permetaient de mettre des configs en dure
	public void setPATH_FILE(String pATH_FILE) {
		Config.PATH_FILE = pATH_FILE;
	}*/

    /**
     * method to display the module circuit on the imageView
     *
     * @param file
     */
    public void displayImage(String file) {
        Image imgUsa = new Image(file + ".png");
        imageView.setImage(imgUsa);
        ImageView i = new ImageView(imgUsa);
        i.setImage(imgUsa);
    }

    /**
     * Callback for mouse clicked on the image View
     *
     * @param e
     */
    @FXML
    public void onMouseClicked(MouseEvent e) {
       double mousePosX = e.getSceneX();
        double mousePosY = 0;
        if(System.getProperty("os.name").toLowerCase().indexOf("win") >= 0){
           System.out.println("IS WINDOWS");
            mousePosY = e.getSceneY() - Config.WINDOWS_MOUSE_OFFSET;
        }else{
            mousePosY = e.getSceneY() - Config.LINUX_MOUSE_OFFSET;
        }
        System.out.println("Mouse screen "+ e.getSceneX()+" "+e.getSceneY());
        System.out.println("Mouse scean "+ e.getScreenX()+" "+e.getScreenY());
        System.out.println("Mouse scean "+mousePosX % imageView.getLayoutX()+" "+mousePosY % imageView.getLayoutY());
        boolean end = false;

        if (graph != null) {
            //goThroughGraph(mousePosX % imageView.getLayoutX(), mousePosY % imageView.getLayoutY());
            goThroughGraph(mousePosX , mousePosY);
        }
       /* List<Graph> listGraphC = graph.getRoot().getChildren();
        do {
            for (Graph g : listGraphC) {
                end = goThroughGraph(mousePosX, mousePosY);
                if (end == true) break;
            }
            end = true;
        } while (end == false);*/
       // System.err.println("error never used");
    }

    /**
     * Callback for Simulation buttons
     *
     * @param e
     */
    @FXML
    public void onClickListener(ActionEvent e) {

        Object o = e.getSource();
        if (o.equals(playButton)) {
            play();
        } else if (o.equals(tickButton)) {
            tick();
        } else if (o.equals(pauseButton)) {
            pause = true;
        } else if (o.equals(stopButton)) {
            stop = true;
            play = false;
            tick = false;
            pause = false;
            reset();
        }

    }

    /**
     * method to reset the graph
     */
    public void reset() {
        if (graph != null) {
            Node tmpn;
            Register tmpr;
            Iterator node = graph.getRoot().getNonRegisterNodes();
            Iterator reg = graph.getRoot().getDataRegisterNodes();
            //call on all nodes and registers
            while (node.hasNext()) {
                tmpn = (Node) node.next();
                if (tmpn.getStage() != null) {
                    tmpn.getStage().close();
                }
                node.remove();
            }
            while (reg.hasNext()) {
                tmpr = (Register) reg.next();
                if (tmpr.getStage() != null) {
                    tmpr.getStage().close();
                }
                reg.remove();
            }

            /** Call reset on api **/
            askReset();

        }
        if (stage != null) {
            stage.close();
        }
    }

    /**
     * method Play called when play button clicked
     */
    public void play() {
        boolean exit = false;

        while (!exit) {
            if (stop) {
                exit = true;
            } else {
                if (!pause) {
                    tick();
                }
                pause = false;
                stop = false;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * method tick called when tick button clicked
     */
    public void tick() {
    	TableColumn<ObservableList<String>, String> column = null;
    	if(controllerMainFrame!=null) {
    		 TableView<ObservableList<String>> tView = controllerMainFrame.gettView();
    		 int sizeTable = tView.getColumns().size();
    		 if(sizeTable>1) {
    			 column = (TableColumn<ObservableList<String>, String>) tView.getColumns().get(sizeTable-1);
    		 }
    	}
    	
        Register reg;
        Iterator itReg = graph.getRoot().getDataRegisterNodes();

        //check if no requests to modify data have to be sent
        while (itReg.hasNext()){
            reg = (Register)itReg.next();
            if(reg.isDataChanged()){
                //we have to send a request to modify values
                askChangeDataRegister(reg.getId(),reg.getNewdatapath());
            }
        }
        int check = askTick();
        String path = "";
        Vector<Data_Tuple> newData = askDataItem(graph.getRoot().getIdList());
        if (api.a.check == 1) {
            //load data gotten on node
            graph.getRoot().tickUpdateBuffer(newData);
        //upload data

            //Display All data in one window
            if(stage==null){
                stage=new Stage();
                Pane root;
                try{
                    FXMLLoader f=new FXMLLoader(getClass().getResource("third.fxml"));
                    root=(Pane)f.load();
                    stage.setScene(new Scene(root));
                    stage.show();
                    controllerMainFrame=f.getController();
                    controllerMainFrame.setGraph(graph.getRoot());
                    controllerMainFrame.setRoot(true);
                    controllerMainFrame.setApi(api);
                    controllerMainFrame.update();


                }catch(IOException eu){
                    eu.printStackTrace();
                }
            }
            else{
            	if(controllerMainFrame!=null)
            		if(controllerMainFrame.getApi()==null) {
            			controllerMainFrame.setApi(api);
            		}
                controllerMainFrame.update();
                stage.close();
                stage.show();
            }



        } else {
            System.out.println("Oops something went wrong when getting data info from simulator");
        }
        graph.getRoot().setTimePlus1();
    }

    /*}
				else

    {
        System.out.println("load failed");
        JOptionPane.showMessageDialog(new Frame(), "Eggs are not supposed to be green.");
    }


		}


                }*/


/****************************************************************************/
/**                                                                            */
/**        function asking the simulator to load a module to simulator 		*/
/**     current available modules can be found under the XML file path		*/
/**      the ModuelController/XML/*.xml										*/
/**                                                                            */
    /****************************************************************************/

    public String loadModule(String nameModule) {
        String path;

        api.APISender(1, "s", 0, nameModule, null, null);

        path = api.a.path;
        return path;
    }

/****************************************************************************/
/**                                                                            */
/**        function asking the simulator to forward the simulation one tick	*/
/**                Appeler dans une boucle pour + d'1 tic						*/
    /****************************************************************************/

    public int askTick() {
        int check;
        api.APISender(2, "s", 0, "", null, null);
        check = api.a.check;
        return check;
    }

/****************************************************************************/
/**                                                                            */
/**    function asking the data contained in one item that is not a register	*/
/**                Not need to be called in a loop just enter					*/
/**                all requested ids in the vector							*/
    /****************************************************************************/

    public Vector<Data_Tuple> askDataItem(String requestedValuesId) {
        int check;
        api.APISender(3, "s", 0, "", requestedValuesId, null);
        check = api.a.check;
        return api.a.changes;

    }


/******************************************************************************/
/**                                                                              */
/**Sending new data to be stored on wires
 * This doesn't apply for changing values on registers*/
/**                                                                              */
    /******************************************************************************/


    public int askChangeDataItem(Vector<Data_Tuple> NewValue) {

        int check;
        api.APISender(4, "s", 0, "", null, NewValue);
        check = api.a.check;
        return check;
    }


/****************************************************************************/
/**                                                                            */
/**            function asking the simulator to reset the simulation			*/
/**                                                                            */
    /****************************************************************************/

    public int askReset() {

        int check;
        api.APISender(5, "s", 0, "", null, null);
        check = api.a.check;
        return check;
    }

/****************************************************************************/
/**                                                                            */
/**        function asking the data contained in one item that is  a register
 * 			Will return the absolute path to it's file
 * 		! Make a copy of the data : don't  keep file opened 				*/
/**                Appeler dans une boucle pour + d'1 objet					*/
    /****************************************************************************/

    public String askDataRegister(int Objcode) {
        String target = "s";
        String path;
        api.APISender(6, "s", Objcode, "", null, null);
        path = api.a.path;
        return path;
    }

/****************************************************************************/
/**                                                                            */
/**    function passing the new data a node that is a register should contain	*/
/**                                                                            */
    /****************************************************************************/

    public void askChangeDataRegister(int Objcode, String AbsolutePath) {
        int check;

        System.out.println("Data set to register model with ID " + Objcode + " data to write contained in  " + api.a.path);

        //trust me I know what i'm doing ^^        vvvvvvvvvvv   vvvvvvvvvvvvv
        api.APISender(7, "s", Objcode, AbsolutePath, null, null);
        check = api.a.check;

        //id=api.a.id; why objcode is alread id ?
    }

    @FXML
    public void stop(){
        api.APISender(0, "", 0, "", null, null);

    }


}
