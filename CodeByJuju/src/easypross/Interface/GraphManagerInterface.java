package easypross.Interface;

public interface GraphManagerInterface {
    public void tick(); // Call action() of every Node stored
    public void documentManager(); // create DocumentManager instance -> To call in class constructor ? 
                                    // DocumentManager is a singleton
    public void createGraph(); // Create the graph
    public void callAPI(); // Communication with API
}
