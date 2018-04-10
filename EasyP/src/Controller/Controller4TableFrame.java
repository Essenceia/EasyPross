package Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import Model.Graph;
import Model.Node;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class Controller4TableFrame {
	
	
	@FXML private TableView<ObservableList<String>> tView;
	
	private Graph graph;
	
	private String fileMod;
	private String PATH_FILE=System.getProperty("user.dir") + System.getProperty("file.separator") + "src";
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
		fileMod="";
		root=false;
	}
	/**
	 * Method update, to update the table view of the window associated
	 */
	public <T> void update() {
		tView.getItems().clear();
		tView.getColumns().clear();
		List<Node<T>> listRegisterNodes = new ArrayList<Node<T>>();
		if(graph!=null) {
			listRegisterNodes= graph.getRegisterNodes(listRegisterNodes);//getNode().getValue().size();
			int sizeTick=1;
			if(listRegisterNodes!=null) {
				sizeTick = listRegisterNodes.get(0).getValue().size();
			}
			List<String> columnNames = new ArrayList<String>();
			columnNames.add("Name register");
			for(int i=1; i<sizeTick+1; i++) {
				columnNames.add("t"+i);
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
	        // add data
			for(Node<T> l : listRegisterNodes) {
				if(root==true)
				{
					Vector<String> vect = new Vector();
					vect.add(l.getName());
					for (int i = 1; i < columnNames.size()+1; i++) {
			            vect.add(l.getValueAtIndex(i-1));
			        }
					
						List<String> row = new ArrayList(vect);
						tView.getItems().add(
			                    FXCollections.observableArrayList(
			                    		row
			                    )
			            );
				}
				if(l.getPos_x()==pos_x && l.getPos_y()==pos_y && l.getWidth()==width && l.getHeight()==height) {
					Vector<String> vect = new Vector();
					vect.add(l.getName());
					for (int i = 1; i < columnNames.size()+1; i++) {
			            vect.add(l.getValueAtIndex(i-1));
			        }
					
						List<String> row = new ArrayList(vect);
						tView.getItems().add(
			                    FXCollections.observableArrayList(
			                    		row
			                    )
			            );
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
