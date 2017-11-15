package easypross.Controller;

public class GraphManagerController implements easypross.Interface.GraphManagerInterface{
    /**
     * Constructor
     */
    public GraphManagerController() {
        //this.documentManager(); -> Possible or not ?
    }
    /// Override methods \\\
    /// Delete execption thrown! \\\
    /**
     * Override of tick from GraphManagerInterface
     */
    @Override
    public void tick() { // Call action() of every Node stored
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * Override of documentManager from GraphManagerInterface
     */
    @Override
    public void documentManager() {// create DocumentManager instance -> To call in class constructor ? 
                                    // DocumentManager is a singleton
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * Override of creategraph from GraphManagerInterface
     */
    @Override
    public void createGraph() { // Create graph
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * Override of callAPI from GraphManagerInterface
     */
    @Override
    public void callAPI() { // Communication with API
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
