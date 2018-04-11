package Controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

public class ControllerMemory {
	@FXML
	private Label labelMemory;
	@FXML
	private Label labelName;
	@FXML
	private GridPane DataGrid;
	@FXML
	private Button ButtonUpdate;

	private Vector<Label> grideaddr;
	Integer size;

	public ControllerMemory() {
		labelMemory = new Label();
		labelName = new Label();
		DataGrid = new GridPane();
		grideaddr = new Vector<>();
		ButtonUpdate = new Button();
		size = 0;


	}

	/**
	 * load memory file and display its content on the window associated
	 *
	 * @param filename
	 * @param name
	 */
	public void loadMemory(String filename, String name, Integer block_length, Integer block_size) {
		String[] contentMemory;
		String str ="";
		if(labelName!=null) {
			labelName.setText(name);
		}
		/*FileReader f= new FileReader(filename);
		contentMemory= f.readFile();
		str= String.join("\n", contentMemory);
		if(labelMemory!=null) {
			labelMemory.setText(str.trim());
		}*/
		System.out.println("File path is "+filename);
		loadmyfile(filename,block_length,block_size);
	}

	private void loadmyfile(String path,Integer block_length, Integer block_size) {
		File file = new File(path);
		BufferedReader br;
		String line = "";
		Integer i = 0;

		if (file.exists() && !file.isDirectory())

		{
			try {
				br = new BufferedReader(new java.io.FileReader(file));
				while ((line = br.readLine()) != null && i < block_length) {
					Label inumber = new Label(String.format("%05X", i & 0xFFFFF));
					TextField data =new TextField(line.replaceAll("\\.", ""));
					addTextLimiter(data,block_size);
					DataGrid.add(inumber, 0, i);
					DataGrid.add(data, 1, i);
					System.out.println("Adding data #"+inumber+" -- "+data.getText());
				}
				size = i;
				br.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}else{
			System.err.println("Error file not found  "+path);
		}


	}

	private void handleButtonAction(ActionEvent event) {
		// Button was clicked, get data from gride
		Vector<String> data = new Vector<>();
		for (int i = 0; i < size; i++) {
			System.out.println("Called");
		}

	}
	@FXML
	private void UpdateMem(ActionEvent e){
		handleButtonAction(e);
	}

	public static void addTextLimiter(final TextField tf, final int maxLength) {
		tf.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
				if (tf.getText().length() > maxLength) {
					String s = tf.getText().substring(0, maxLength);
					tf.setText(s);
				}
			}
		});
	}

}
