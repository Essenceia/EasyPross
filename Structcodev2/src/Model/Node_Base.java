package Model;

import java.util.Vector;

public abstract class Node_Base extends Graph_Base implements Interface.Node_Base {
    //data
    //Wires in and out
    protected Vector<Wire> Wire_in;
    protected Vector<Wire> Wire_out;
    //data of wire
    protected Boolean[] data_in;
    protected Boolean[] data_out;
    protected Boolean[] buffer;
    protected int data_in_size;
    protected int data_out_size;

    public Node_Base(int id, Vector<Wire> wire_input, Vector<Wire> wire_output){
        super(id);
        //descriptif
        description += "Noeud ";
        //taille des bus de donne entrant et sortant
        data_in_size = helper.count_wire_size(wire_input);
        data_out_size = helper.count_wire_size(wire_output);
        data_in = new Boolean[data_in_size];
        data_out = new  Boolean[data_out_size];
        //donnes des bus ! l'ordre sera conserver
        Wire_in = wire_input;
        Wire_out = wire_output;


    }

    @Override
    public void get_incomming_data() {
        //recuperer tout les donne vendant et la placer dans les data_in
        data_in= helper.put_wire_data_in_buffer(Wire_in,data_in);
        data_out= helper.put_wire_data_in_buffer(Wire_out,data_out);

    }

    @Override
    public void put_outputing_data() {
        //mettre les donnes dans les wire en sortie
        helper.put_wire_data_in_buffer(Wire_out,data_out);
    }
    
    public Vector<Wire> getWireIn()
    {
       return Wire_in; 
    }
    
    public Vector<Wire> getWireOut()
    {
       return Wire_out; 
    }
}
