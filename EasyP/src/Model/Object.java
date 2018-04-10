package Model;

import javafx.stage.Stage;
import org.jdom2.Attribute;
import org.jdom2.DataConversionException;
import org.jdom2.Element;

import java.util.List;
import java.util.Vector;

public class Object {
    protected String type;
    protected int id;
    protected String name;
    protected int height;
    protected int width;
    protected int pos_x;
    protected int pos_y;
    protected Stage stage;

    /*	public Object(int id, String name , String type, int height, int width, int pos_x, int pos_y) {
            this.id = id;
            this.name = name;
            this.type = type;
            this.height = height;
            this.width = width;
            this.pos_x = pos_x;
            this.pos_y = pos_y;
        }*/
    public Object(List<Attribute> l) {
        //this.type = e.getName();
        try {
            for (Attribute a : l) {
                switch (a.getName()) {
                    case "id":
                        this.id = a.getIntValue();
                        break;
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
    }

    public Stage getStage(){
        return stage;
    }
    public void setStage(Stage nv){
        stage = nv;
    }
    public Integer getId() {
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

    public String getName() {
        return name;
    }

    public void setPos_y(int pos_y) {
        this.pos_y = pos_y;
    }


    @Override
    public String toString() {
        return "Node [type=" + type + ", id=" + id + ", name=" + name + ", height=" + height
                + ", width=" + width + ", pos_x=" + pos_x + ", pos_y=" + pos_y + "]\n";
    }

}
