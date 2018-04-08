package Controller;


import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

	public Main() {
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		
		Pane root ;
		
		Stage s=arg0;
		try {
			FXMLLoader f = new FXMLLoader(getClass().getResource("first.fxml"));
			root = (Pane)f.load();
			s.setScene(new Scene(root));
			s.show();
			
		} catch (IOException eu) {
			eu.printStackTrace();
		}
	}
}
