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


	public Wire( List<Attribute> l) {
		super(l);
		values = new ArrayList<>();
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
