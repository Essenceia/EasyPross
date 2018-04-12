package View;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class EasyProssMainFrame {

	
	/**
	 * Main Data display frame (All data in a table
	 */
	public EasyProssMainFrame() {
		//Create a new stage
		Stage s= new Stage();
		Pane root;
		try {
			//Load the XML file
			FXMLLoader f = new FXMLLoader(getClass().getResource("third.fxml"));
			//Create the pane out of the XML file
			root = (Pane)f.load();
			//Create a new scene from the pane and add it to the stage
			s.setScene(new Scene(root));
			//Show the window
			s.show();
			
		} catch (IOException eu) {
			eu.printStackTrace();
		}
	}
}

