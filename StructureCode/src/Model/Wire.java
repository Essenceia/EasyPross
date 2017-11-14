package Model;

public class Wire extends Graph_Base implements Interface.Wire{
    //internal data
    protected int data_bus_size;
    protected Boolean[] data;
    //protected Boolean active;


    public Wire(int new_id,int bus_size){
        super(new_id);
        type = 1; // les wire on pour type 0
        if(bus_size > 0)data_bus_size = bus_size;
        else {
            data_bus_size = 1;
            System.out.println("Erreur : Wire() , bus size doit etre > 0");
        }
        //descriptif
        description += "Bus de donne "+Integer.toString(data_bus_size)+"bits";
        data = new Boolean[data_bus_size];
        reset_values();
    }

    @Override
    public void reset_values() {
        super.reset_values();
        for(int i=0; i < data_bus_size ; i++){
            data[0] = false;
        }
    }

    @Override
    public int get_data_size() {
        return data_bus_size;
    }

    @Override
    public Boolean[] get_data() {
        return data;
    }

    /*@Override
    public Boolean getActive() {
        return active;
    }*/

    @Override
    public void put_data_at_index(Boolean d, int index) {
        if(index >= data_bus_size){
            System.out.println("Erreur index out of range wire");
        }else{
            data[index] =d;
        }
    }
}
