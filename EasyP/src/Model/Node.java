package Model;

import java.util.List;
import java.util.Vector;
import org.jdom2.Element;

import javafx.stage.Stage;

import org.jdom2.Attribute;
import org.jdom2.DataConversionException;

public class Node<T> {
	private String type;
	private int id;
	private String name;
	private Vector<String> value;
	private int height;
	private int width;
	private int pos_x;
	private int pos_y;
	private String path;
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	private Stage stage;

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	public Node() {
		
	}
	public Node(Element e, List<Attribute> l) {
		this.path="";
		this.value = new Vector<String>();
		//this.type = e.getName();
		try {
                    	for(Attribute a : l) {
				switch(a.getName()) {
				case "id":
					this.id= a.getIntValue();
					break;
				case "name":
					this.name = ""+a.getValue();
					break;
				case "height":
					this.height= a.getIntValue();
					break;
				case "width":
					this.width= a.getIntValue();
					break;
				case "pos_x":
					this.pos_x= a.getIntValue();
					break;
				case "pos_y":
					this.pos_y= a.getIntValue();
					break;
				case "description":
					this.type = a.getValue();
					default:// Do nothing for any other attributes
						break;
				}
			}
		}
		catch (DataConversionException e1) {
			e1.printStackTrace();
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vector<String> getValue() {
		return value;
	}
	public String getValueAtIndex(int index) {
		if(index >=0 && index <value.size()) {
			return value.get(index);
		}
		return "";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getPos_x() {
		return pos_x;
	}

	public void setPos_x(int pos_x) {
		this.pos_x = pos_x;
	}

	public int getPos_y() {
		return pos_y;
	}

	public void setPos_y(int pos_y) {
		this.pos_y = pos_y;
	}

	public Vector<String> getvalue() {
		return value;
	}
	public void setValue(Vector<String> v) {
		value = v;
	}

	@Override
	public String toString() {
		return "Node [type=" + type + ", id=" + id + ", name=" + name + ", value=" + value + ", height=" + height
				+ ", width=" + width + ", pos_x=" + pos_x + ", pos_y=" + pos_y + "]\n";
	}
	
	public boolean isARegister() {
		if(this.type!=null) {
			if(this.type.equals("register")) {
				return true;
			}
		}
		return false;
	}
	public boolean isNotARegister() {
		if(!this.type.equals("register")) {
			return true;
		}
		return false;
	}

}
