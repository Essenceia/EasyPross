package Model;

import java.util.Vector;

public abstract class Node_Probe_Base extends Graph_Base implements Interface.Node_Probe_Base {
    protected Integer wire_size;
    Vector<Wire> wire;
    Boolean[] data;
    public Node_Probe_Base(int next_id,Vector<Wire> link_wire) {
        super(next_id);
        description +="Probe ";
        wire=link_wire;
        wire_size = helper.count_wire_size(link_wire);
        data = new Boolean[wire_size];

    }

    @Override
    public Integer getBitSize() {
        return wire_size;
    }

    @Override
    public Boolean[] getData() {
        return data;
    }
}
