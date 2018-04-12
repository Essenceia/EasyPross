package Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileReader {

	public String fileName;
	public FileReader() {
		this.fileName="";
	}
	public FileReader(String file) {
		this.fileName=file;
	}
	/**
	 * Method to read the content of a file and return it in an array of Strings
	 * @return
	 */
	public String[] readFile() {
		File file = new File(fileName);
        FileInputStream stream;
		try {
			stream = new FileInputStream(file);
			byte[] data = new byte[(int) file.length()];
	        stream.read(data);
	        stream.close();
	        
	        String contents = new String(data);
            String statements[] = contents.split("\n");
            return statements;
	        
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;
	}
	
}
