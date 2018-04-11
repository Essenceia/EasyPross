package Model;

import javafx.stage.Stage;
import org.jdom2.Attribute;
import org.jdom2.DataConversionException;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Wire extends Object{

	private ArrayList<String> values;
	boolean hasAllValues ;


	public Wire( List<Attribute> l) {
		super(l);
		values = new ArrayList<>();
		hasAllValues = false;
	}

	public void updateAttributes(List<Attribute> l){

	    if(!hasAllValues) {

            try {

                for (Attribute a : l) {
                    switch (a.getName()) {
                        case "name":
                            this.name = "" + a.getValue();
                            break;
                        case "height":
                            this.height = a.getIntValue();
                            break;
                        case "width":
                            this.width = a.getIntValue();
                            break;
                        case "pos_x":
                            this.pos_x = a.getIntValue();
                            break;
                        case "pos_y":
                            this.pos_y = a.getIntValue();
                            break;
                        case "description":
                            this.type = a.getValue();
                        default:// Do nothing for any other attributes
                            break;
                    }
                }
            } catch (DataConversionException e1) {
                e1.printStackTrace();
            }

            hasAllValues = true;
        }
    }


	public ArrayList<String> getValue() {
		return values;
	}
	public String getLast() {
		return values.get(values.size()-1) ;
	}


	public void addValue(String v) {
		values.add(v);
	}

}
