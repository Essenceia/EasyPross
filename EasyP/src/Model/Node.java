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
    protected Integer blockSize = 1;
    protected Integer blockLenght = 1;


    public Node(List<Attribute> l) {

        super(l);
        try {
            for (Attribute a : l) {
                switch (a.getName()) {
                    case "memory_block_size":
                        this.blockLenght = a.getIntValue();

                        break;
                    case "memory_number_block":
                        this.blockSize = a.getIntValue();
                        break;
                    default:// Do nothing for any other attributes
                        break;
                }
            }
        } catch (DataConversionException e1) {
            e1.printStackTrace();

        }
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
