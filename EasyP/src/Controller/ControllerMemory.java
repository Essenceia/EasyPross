package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ControllerMemory {
	@FXML private Label labelMemory;
	@FXML private Label labelName;
	public ControllerMemory() {
		labelMemory = new Label();
		labelName = new Label();
	}
	/**
	 * load memory file and display its content on the window associated
	 * @param filename
	 * @param name
	 */
	public void loadMemory(String filename, String name) {
		String[] contentMemory;
		String str ="";
		if(labelName!=null) {
			labelName.setText(name);
		}
		FileReader f= new FileReader(filename);
		contentMemory= f.readFile();
		str= String.join("\n", contentMemory);
		if(labelMemory!=null) {
			labelMemory.setText(str.trim());
		}
	}

}
