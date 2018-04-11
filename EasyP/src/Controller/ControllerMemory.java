package Controller;

import Model.Register;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;

import javax.swing.*;
import javax.swing.text.Position;
import java.awt.event.KeyEvent;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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
	private Register reg;

	public ControllerMemory() {
		labelMemory = new Label();
		labelName = new Label();
		DataGrid = new GridPane();
		grideaddr = new Vector<>();
		ButtonUpdate = new Button();



	}

	/**
	 * load memory file and display its content on the window associated
	 *
	 * @param filename
	 * @param name
	 */
	public void loadMemory(String filename, String name, Register reg) {
		this.reg = reg;
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
		System.out.println("Gotten a node with lenght "+reg.getBlockLenght() +" size "+reg.getBlockSize());
		loadmyfile(filename,reg.getBlockLenght(),reg.getBlockSize());
	}

	private void loadmyfile(String path,Integer block_length, Integer block_size) {
		File file = new File(path);
		BufferedReader br;
		String line = "";
		String to_store ="";
		Integer i = 0;

		if (file.exists() && !file.isDirectory())

		{
			try {
				br = new BufferedReader(new java.io.FileReader(file));
				while ((line = br.readLine()) != null && i < block_length) {
					Label inumber = new Label(String.format("%04X", i & 0xFFFFF));
					to_store = line.replaceAll("\\.", "");
					TextField data =new TextField(to_store);
					addTextLimiter(data,block_length);
					DataGrid.add(inumber, 0, i);
					DataGrid.add(data, 1, i);
					System.out.println("Adding data #"+inumber.getText()+" -- "+data.getText());
					i++;
				}
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


	@FXML
	private void UpdateMem(ActionEvent e){
			// Button was clicked, get data from gride
			String fname = Helper_Data_Handler.createTmpFile();
			FileWriter fw;
			ArrayList<String> data = new ArrayList<>(this.reg.getBlockSize());
			ObservableList<Node> childrens = DataGrid.getChildren();
			for (Node node : childrens) {
				if (DataGrid.getColumnIndex(node) == 1) {
					System.out.println("Getting data at id " + node.getId());
					data.add(DataGrid.getRowIndex(node), Helper_Data_Handler.toWireWithDot(node.getAccessibleText(), reg.getBlockLenght()));
				}
			}
			try {
				//write to our temportaty file
				fw = new FileWriter(fname);
				for (int i = 0; i < reg.getBlockSize(); i++) {
					fw.write(data.get(i));
				}
				fw.close();
				reg.setNewdatapath(fname);

			} catch (IOException ex) {
				ex.printStackTrace();
			}

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
