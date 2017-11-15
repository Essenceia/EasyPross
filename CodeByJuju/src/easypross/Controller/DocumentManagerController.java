package easypross.Controller;

public class DocumentManagerController implements easypross.Interface.DocumentManagerInterface{

    private DocumentManagerController() {
    }
    /**
     * Override of open method from DocumentManagerInterface
     * @param fileName 
     */
    @Override
    public void open(String fileName){
    }
    /**
     * Override of close from DocumentManagerInterface
     * @param fileName
     * @return succes or fail
     */
    @Override
    public boolean close(String fileName){
        return true; // To confirm that the closing succeded -> can be changed if needed
    }
    /**
     * Override of readLine from DocumentManager
     * @param fileName
     * @return 
     */
    @Override
    public String readLine(String fileName){
        return ""; // To return the line read
    }
}
