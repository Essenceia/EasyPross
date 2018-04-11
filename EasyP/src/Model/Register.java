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
    private String newdatapath;
    private boolean dataChanged;


    public Register(List<Attribute> l) {
        super(l);
        path = "";
        newdatapath = "";
        dataChanged = false;
    }


    public Iterator<Wire> get_connected() {
        return this.wires.iterator();
    }


    public boolean isARegister() {
        return true;
    }

    public String getNewdatapath() {
        dataChanged = false;
        return newdatapath;
    }
    public void setNewdatapath(String p){
        newdatapath = p;
        dataChanged = true;
    }
    public void setPath(String p){
        path = p;
    }
    public String getPath(){
        return path;
    }
    public boolean isDataChanged(){
        return dataChanged;
    }

    public Integer getBlockLenght() {
        return this.blockLenght;
    }
    public Integer getBlockSize(){
        return blockSize;
    }
}
