package Interface;

public interface Graph_Manager_Interface {

    public void tick(); // Call action() of every Node stored

    public void createGraph(String path_to_file); // Create the graph

    public void callAPI(); // Communication with API
}
