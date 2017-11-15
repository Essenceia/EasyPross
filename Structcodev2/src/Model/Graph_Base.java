package Model;

public abstract class Graph_Base implements Interface.Grap_Base {
    //objets - default values
    protected int id=0;
    protected int type=0;
    protected String description;
    public Graph_Base(int next_id){

        id=next_id;
        description = "ID:"+Integer.toString(next_id)+" ";
    }

    public int get_id(){
        return id;
    }
    public int get_type(){
        return type;
    }
    public String getDescription() {
        return description;
    }

    public void reset_values(){
        //nothing to do here
    }
}
