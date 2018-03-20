package easypross.Model.AbstractClasses;

public class RegisterModel_Abstract extends NodeModel_Abstract{
    /**
     * Attributes : fileName, blockSize, blockCount
     */
    protected String fileName;
    protected int block_size;
    protected int block_count;
    /**
     * Constructor
     * @param lock
     * @param data_in
     * @param data_out
     * @param id
     * @param type
     * @param description 
     */
    public RegisterModel_Abstract(String lock, boolean[] data_in, boolean[] data_out, int id, int type, String description) {
        super(lock, data_in, data_out, id, type, description);
    }
    // Getters and Setters \\
    /**
     * getter FileName
     * @return fileName
     */
    public String getFileName() {
        return fileName;
    }
    /**
     * setter FileName
     * @param fileName 
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    //Override of interface methods of Node_Interface
    /**
     * Override action from Node_Interface
     */
    @Override
    public void action(){
        
    }
    /**
     * readBlock
     * @param block
     * @return 
     */
    protected boolean[] readBlock(boolean[] block) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * writeBlock
     * @param newValues
     * @param blockNumber 
     */
    protected void writeBlock(boolean[] newValues, int blockNumber) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * openFile
     */
    protected void openFile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * closeFile 
     */
    protected void closeFile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * checkLockOnFile
     * @return boolean
     */
    protected boolean checkLockOnFile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}