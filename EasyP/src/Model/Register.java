package Model;

import org.jdom2.Attribute;
import org.jdom2.Element;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class Register<T> extends Node {

    private String path;
    private Vector<String> buffer;


    public Register(List<Attribute> l) {
        super(l);
        path = "";
        buffer = new Vector<>();
    }


    public Iterator<Wire> get_connected() {
        return this.wires.iterator();
    }


    public boolean isARegister() {
        return true;
    }


}
