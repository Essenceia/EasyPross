package easypross.View;

public class ModuleView {
    public NodeView nodeView;
    public WireView wireView;
    /**
     * Constructor
     * @param nodeView
     * @param wireView 
     */
    public ModuleView(NodeView nodeView, WireView wireView) {
        this.nodeView = nodeView;
        this.wireView = wireView;
    }
    

}
