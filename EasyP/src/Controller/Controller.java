package Controller;

import java.io.IOException;
import java.util.List;
import java.util.Vector;
import Model.Graph;
import Model.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Controller {
	
	@FXML private Button buttonChooseModule;
	@FXML private ListView<String> listView;
	@FXML private Label labelDescription;
	@FXML private ImageView imageView;
	@FXML private Pane pane;
	@FXML private Button playButton;
	@FXML private Button tickButton;
	@FXML private Button pauseButton;
	@FXML private Button stopButton;
	@FXML private Label labelASM;
	private String[] listViewFile;
	private XMLtoGraph graph;
	private boolean moduleSelected;
	private String fileMod;

	private boolean play=false;
	private boolean pause=false;
	private boolean stop=false;
	private boolean tick=false;
	
	private Stage stageSecond;
	private Stage stage;
	private Controller4TableFrame controllerMainFrame;
	private API_IHM api;
	
	
	public Controller() {
		api = new API_IHM();
		stageSecond= new Stage();
		imageView = new ImageView();
		labelDescription = new Label();
		labelASM = new Label();
		listView = new ListView<String>();
		FileReader f= new FileReader(Config.PATH_FILE+"modules.txt");
		listViewFile= f.readFile();
		
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
	@FXML void initialize() {
		if(listView.getItems().isEmpty()) {
			for(String line: listViewFile) {
				listView.getItems().add(line.trim());
			}
		}
		
		
		//listView.getSelectionModel().select(1); // select first item by default
		if(moduleSelected==true) {
			String file = listView.getSelectionModel().getSelectedItem().toString();
			System.out.println(file);
			loadModuleDescription(file);
			moduleSelected=false;
		}
		
	}
	/**
	 * callback when button on first frame is clicked
	 * @param e
	 */
	@FXML public void buttonChooseModuleOnAction(ActionEvent e) {
		if(stageSecond.isShowing()==false) {
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
	 * @param n
	 * @param mousePosX
	 * @param mousePosY
	 * @return
	 */
	public boolean goThroughGraph(Node n,double mousePosX, double mousePosY ) {
		double minx = n.getPos_x()*imageView.getFitHeight()/graph.getRoot().getNode().getHeight();
		double miny = n.getPos_y()*imageView.getFitWidth()/graph.getRoot().getNode().getWidth();
		double maxx = minx + n.getHeight()*imageView.getFitHeight()/graph.getRoot().getNode().getHeight();
		double maxy = miny +n.getWidth()*imageView.getFitWidth()/graph.getRoot().getNode().getWidth();
		
		if(mousePosX <maxx && mousePosX > minx) {
			if(mousePosY <maxy && mousePosY > miny) {
				if(n.getType().equals("PM")||n.getType().equals("DM")) {
					if(!n.getPath().equals("")) {
						if(n.getStage()!=null) {
							n.getStage().close();
							n.setStage(null);
						}
						Stage s= new Stage();
						Pane root;
						try {
							FXMLLoader f = new FXMLLoader(getClass().getResource("fourth.fxml"));
							root = (Pane)f.load();
							s.setScene(new Scene(root));
							s.show();
							
								ControllerMemory controllerMemory = f.getController();
								controllerMemory.loadMemory(n.getPath(), n.getType());
							
						} catch (IOException eu) {
							eu.printStackTrace();
						}
						n.setStage(s);
					}
				}else {
					if(n.getStage()!=null) {
						n.getStage().close();
						n.setStage(null);
					}
						Stage s= new Stage();
						Pane root;
						try {
							FXMLLoader f = new FXMLLoader(getClass().getResource("third.fxml"));
							root = (Pane)f.load();
							s.setScene(new Scene(root));
							s.show();
							Controller4TableFrame controller = f.getController();
							controller.setGraph(graph.getRoot());
							controller.setPos_x(n.getPos_x());
							controller.setPos_y(n.getPos_y());
							controller.setWidth(n.getWidth());
							controller.setHeight(n.getHeight());
							controller.update();
							
						} catch (IOException eu) {
							eu.printStackTrace();
						}
						n.setStage(s);
						return true;
				}
			}
		}
		return false;
	}
	/**
	 * Method to load the description of the module and display it on the screen
	 * methods to display image and ASM file called
	 * @param file
	 */
	public void loadModuleDescription(String file) {
		FileReader fileDescriptionModule;
		fileDescriptionModule = new FileReader(Config.PATH_FILE+file+".txt");
		String[] content = fileDescriptionModule.readFile();
		String str ="";
		str= String.join("\n", content);
		if(labelDescription!=null) {
			labelDescription.setText(str);
			graph = new XMLtoGraph(Config.PATH_FILE+file);
			displayImage(file);
		}
		 /*****************/
		/***Ask Modu API**/
	   /*****************/
	   //todo have to unify descriptions of modules
		loadModule(file);
		String p =Config.PATH_BASE+"fichier.txt";// askModule(file,"S");
		/*****************/
		 /***	END		 **/
		/***Ask Modu API**/
	   /*****************/
		FileReader fileASM;
		fileASM = new FileReader(p);
		String[] contentASM = fileASM.readFile();
		String strASM ="";
		strASM= String.join("\n", contentASM);
		if(labelASM!=null) {
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
	 * @param file
	 */
	public void displayImage(String file) {
		Image imgUsa = new Image (file+".png");
		imageView.setImage(imgUsa);
		ImageView i = new ImageView(imgUsa);
		i.setImage(imgUsa);
	}
	/**
	 * Callback for mouse clicked on the image View
	 * @param e
	 */
	@FXML public void onMouseClicked(MouseEvent e) {
		double mousePosX =e.getSceneX();
		double mousePosY =e.getScreenY();
		boolean end= false;
		
		if(graph !=null) {
			goThroughGraph(graph.getRoot().getNode(), mousePosX%imageView.getLayoutX(),  mousePosY%imageView.getLayoutY() );
		}
		List<Graph> listGraphC = graph.getRoot().getChildren();
		do {
			for(Graph g : listGraphC) {
				end=goThroughGraph(g.getNode(), mousePosX,  mousePosY );
				if(end==true) break;
			}
			end=true;
		}while(end == false);
	}
	/**
	 * Callback for Simulation buttons
	 * @param e
	 */
	@FXML public void onClickListener(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(playButton)) {
			play();
		}
		else if(o.equals(tickButton)){
			tick();
		}else if(o.equals(pauseButton)) {
			pause=true;
		}else if(o.equals(stopButton)) {
			stop=true;
			play=false;
			tick=false;
			pause=false;
			reset();			
		}
	}
	/**
	 * method to reset the graph
	 */
	public void reset() {
		if(graph !=null) {
			graph.getRoot().getNode().getValue().clear();
			if(graph.getRoot().getNode().getStage()!=null) {
				graph.getRoot().getNode().getStage().close();
			}
			List<Graph> listGraphC = graph.getRoot().getChildren();
			for(Graph g : listGraphC) {
					g.getNode().getValue().clear();
					if(g.getNode().getStage()!=null) {
						g.getNode().getStage().close();
					}
				}

				/** Call reset on api **/
				askReset();

		}
		if(stage!=null) {
			stage.close();
		}
	}
	
	/**
	 * method Play called when play button clicked 
	 * -> disabled for now
	 */
	public void play()
	{
		boolean exit=false;
		
		while(!exit)
		{
			if(stop)
			{
				exit=true;
			}
			else
			{
				if(!pause)
				{
					tick();
				}
				pause=false;
				stop=false;
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
	public void tick()
	{
		int check = askTick();
		String path="";
		Vector<Integer> wireGetData = new Vector<>();
		Vector<Data_Tuple> newData = new Vector<>();

		if(check==1)
		{
			if(graph !=null) {
				/*** First Node is Schema ! Does not have a value !! ***/
				List<Graph> listGraphC = graph.getRoot().getChildren();
				for(Graph g : listGraphC) {
					if(g.getNode().getType().equals("PM")||g.getNode().getType().equals("DM")) {
						 /*****************/
						/***Ask Regi API**/
					   /*****************/
						System.out.println(g.getNode().getId());
						path=askDataRegister(g.getNode().getId());
						//path=Config.PATH_FILE+" fichier.txt";
						  /*****************/
						 /***	END		 **/
						/***Ask Regi API**/
					   /*****************/
						g.getNode().setPath(path);
					}else {
						 /*****************/
						/***Ask Wire API**/
					   /*****************/
						/*Vector<Boolean> ve=new Vector<Boolean>();
						ve.add(new Boolean(true));
						ve.add(new Boolean(false));*/
						/**
						 * Call for wire data should be done at the end to win in efficency
						 * Ideally should only have one unique call to simulator
						 */
						wireGetData.removeAllElements();
						wireGetData.add(g.getNode().getId());
						//send call to api for wires
						newData = askDataItem(wireGetData);
						if(check!= 0 && newData.size() == 1) {
							//load data gotten on node
							g.getNode().getValue().add(newData.get(0).getStringValues());
							System.out.println("Set on id#"+g.getNode().getId()+ " data "+newData.get(0).getStringValues());
						}else{
							System.out.println("Oops something went wrong when getting data info from simulator");
						}
						  /*****************/
						 /***	END		 **/
						/***Ask Wire API**/
					   /*****************/
						/*for(Boolean b: ve) {
							String boo = "";
							if(b.equals(true)) {
								boo="1";
							}else boo="0";
								
							value+= boo;
						}
						g.getNode().getValue().add(value);*/
					}
				}

			}
		}
		/***Display All data in one window**/
		if(stage==null) {
			stage = new Stage();
			Pane root;
			try {
				FXMLLoader f = new FXMLLoader(getClass().getResource("third.fxml"));
				root = (Pane) f.load();
				stage.setScene(new Scene(root));
				stage.show();
				controllerMainFrame = f.getController();
				controllerMainFrame.setGraph(graph.getRoot());
				controllerMainFrame.setRoot(true);
				controllerMainFrame.update();
				
				
			} catch (IOException eu) {
				eu.printStackTrace();
			}
		}
		else {
			controllerMainFrame.update();
			stage.close();
			stage.show();
		}
		
		
	}
	
	/****************************************************************************/
	/**																			*/
	/**		function asking the simulator to load a module to simulator 		*/
	/**     current available modules can be found under the XML file path		*/
	/**      the ModuelController/XML/*.xml										*/
	/**																			*/
	/****************************************************************************/
	
	public String loadModule(String nameModule)
	{
		String path;
		
		api.APISender(1, "s", 0, nameModule, null, null);

		path=api.a.path;
		return path;
	}	

	/****************************************************************************/
	/**																			*/
	/**		function asking the simulator to forward the simulation one tick	*/
	/**				Appeler dans une boucle pour + d'1 tic						*/
	/****************************************************************************/
	
	public int askTick()
	{
		int check;		
		api.APISender(2, "s",0, "", null,null);
		check=api.a.check;
		return check;
	}

	/****************************************************************************/
	/**																			*/
	/**	function asking the data contained in one item that is not a register	*/
	/**				Not need to be called in a loop just enter					*/
	/** 				all requested ids in the vector							*/
	/****************************************************************************/
	
	public Vector<Data_Tuple> askDataItem(Vector<Integer> requestedValuesId)
	{
		int check;
		api.APISender(3, "s",0,"", requestedValuesId,null);
		check=api.a.check;
		return api.a.changes;
		
	}
	
	
	/******************************************************************************/
	/**																			  */
	/**Sending new data to be stored on wires
	 * This doesn't apply for changing values on registers*/
	/**																			  */
	/******************************************************************************/
	

	public int askChangeDataItem(Vector<Data_Tuple> NewValue)
	{

		int check;
		api.APISender(4,"s", 0, "", null, NewValue);
		check=api.a.check;
		return check;
	}
	
	
	/****************************************************************************/
	/**																			*/
	/**			function asking the simulator to reset the simulation			*/
	/**																			*/
	/****************************************************************************/
	
	public int askReset()
	{

		int check;
		api.APISender(5, "s", 0, "", null,null);
		check=api.a.check;
		return check;
	}
	
	/****************************************************************************/
	/**																			*/
	/**		function asking the data contained in one item that is  a register
	 * 			Will return the absolute path to it's file
	 * 		! Make a copy of the data : don't  keep file opened 				*/
	/**				Appeler dans une boucle pour + d'1 objet					*/
	/****************************************************************************/
	
	public String askDataRegister(int Objcode)
	{
		String target="s";
		String path;
		api.APISender(6,"s", Objcode, "", null,null);
		path=api.a.path;
		return path;
	}
	
	/****************************************************************************/
	/**																			*/
	/**	function passing the new data a node that is a register should contain	*/
	/**																			*/
	/****************************************************************************/
	
	public void askChangeDataRegister(int Objcode,String AbsolutePath)
	{
		int check;

		System.out.println("Data set to register model with ID "+Objcode+" data to write contained in  "+api.a.path);

		//trust me I know what i'm doing ^^        vvvvvvvvvvv   vvvvvvvvvvvvv
		api.APISender(7, "s", Objcode, AbsolutePath, null,null);
		check=api.a.check;

		//id=api.a.id; why objcode is alread id ?
	}
}
