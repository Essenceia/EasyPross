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
    /// Getters and Setters \\\
    /**
     * getter NodeView
     * @return nodeView
     */
    public NodeView getNodeView() {
        return nodeView;
    }
    /**
     * setter NodeView
     * @param nodeView 
     */
    public void setNodeView(NodeView nodeView) {
        this.nodeView = nodeView;
    }
    /**
     * getter WireViw
     * @return wireView
     */
    public WireView getWireView() {
        return wireView;
    }
    /**
     * setter WireView
     * @param wireView 
     */
    public void setWireView(WireView wireView) {
        this.wireView = wireView;
    }
}
