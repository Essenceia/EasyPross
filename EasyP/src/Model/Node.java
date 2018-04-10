package Model;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.jdom2.Element;

import javafx.stage.Stage;

import org.jdom2.Attribute;
import org.jdom2.DataConversionException;

public class Node<T> extends Object {

    protected Vector<Wire> wires;


    public Node(List<Attribute> l) {
        super(l);
        //will attache connections later
        wires = new Vector<>();
    }

    public void add(Wire attach) {
        wires.add(attach);
    }

    public Iterator<Wire> get_connected() {
        return this.wires.iterator();
    }


    public boolean isARegister() {
        return false;
    }


}
