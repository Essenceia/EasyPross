package Model;

import javafx.stage.Stage;
import org.jdom2.Attribute;
import org.jdom2.Element;

import javax.management.AttributeList;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class Register<T> extends Node {

    private String path;
    private ArrayList<String> buffer;


    public Register(List<Attribute> l) {
        super(l);
        path = "";
        buffer = new ArrayList<>();
    }


    public Iterator<Wire> get_connected() {
        return this.wires.iterator();
    }


    public boolean isARegister() {
        return true;
    }

    public ArrayList<String> getBuffer() {
        return buffer;
    }

    public boolean loadDataFromFile(String path) {
        boolean sucess = false;
        File file = new File(path);
        this.path = path;
        BufferedReader br;
        String line = "";
        buffer.clear();

        if (file.exists() && !file.isDirectory()) {
            file.setReadOnly();
            try {
                //todo not necessary
                br = new BufferedReader(new FileReader(file));
                while ((line = br.readLine()) != null) {
                    buffer.add(line);
                }
                br.close();
                sucess = true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return sucess;
    }

    public String getPath() {
        return path;
    }
    public void setPath(String p){
        path = p;
    }

    public Integer getBlockLenght() {
        return this.blockLenght;
    }
    public Integer getBlockSize(){
        return blockSize;
    }
}
