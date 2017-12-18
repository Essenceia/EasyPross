package Model.AbstractClasses;

public class RegisterModel_Abstract extends NodeModel_Abstract{
    /**
     * Attributes : fileName, blockSize, blockCount
     */
    protected String fileName;
    protected boolean lock;
    protected int block_size;
    protected int block_count;
    /**
     * Constructor
     * @param data_in
     * @param data_out
     * @param id
     * @param type
     * @param description 
     */
    public RegisterModel_Abstract(boolean[] data_in, boolean[] data_out, int id, int type, String description) {
        super(data_in, data_out, id, type, description);
        lock = false;
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
     * getter Lock
     * 
     * @return lock
     */
    public boolean getLock()
    {
      return lock;  
    }
    
    /**
     * Setter Lock
     * 
     * @param locked 
     */
    public void setLock(boolean locked)
    {
      lock = locked;  
    }
    
    /**
     * setter FileName
     * @param fileName 
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    //Override of interface methods of NodeInterface
    /**
     * Override action from NodeInterface
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