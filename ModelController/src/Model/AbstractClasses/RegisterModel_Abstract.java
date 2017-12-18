package Model.AbstractClasses;
import java.io.File;
/**
 * Constant values
 * <p>
 * Define the realtive paths to where the files will be stored
 * The temporary file will be stored in the systems default location for temporary
 * files.
 */

public static final String FILE_SEPARATOR = System.getProperty("file.separator");
public static final String WORKING_FILE_FOLDER_PATH=".."+FILE_SEPARATOR+"Model_CPU"+FILE_SEPARATOR;
/**
 * RegisterModel_Abstract RegisterModel_Abstract.java "Model/AbstractClasses/RegisterModel_Abstract.java"
 * <p>
 * Model for managing
 * \details This probe is the entry point for the graph, the data is recived by the API and transfered to the connected
 * lines.
 * <p>
 * The file contains the data of our register model, the data is organised by
 * blocks of a specific size. Here we consider that one unit of data is symbolised
 * by an bit ( Boolean ).So if we consider that a block  size is of 8 then eatch value
 * contained in our register is of 8 bits. Then if our block count is of 24 then this means
 * that we have 24 values. So going back to our previous example this would mean that we
 * have 24 values of 8 bits each. Our register would have a memory with a capacity of
 * 8*24 = 192 bits.
 * <p>
 * File note :
 * Each bit of data is separated by a ' ' and every value is on a seperate line.
 * If no data has been set on that specific line then the file is initialised with all zeros.
 * If not file is found a new file will be created an initialised with all zeros.
 */
public class RegisterModel_Abstract extends NodeModel_Abstract {
    /**
     * Attributes : fileName, tmp_fileName, blockSize, blockCount
     */
    protected String absfilePath;
    /**
     * Absolute path to where our working file is stored
     */
    protected String fileName;
    /**
     * Name of the file that contains the data of our object
     */
    protected String tmp_fileName;
    /**
     * Name of the temporary file that we have given to the API so it can be modified
     * by an exterior source.
     */
    protected File data;
    /**
     * Working directory file that contains our data
     */
    //protected boolean lock;/** @deprecated lock will be replaced by the use of a temporary file */
    protected int block_size;
    /**
     * Size of one block (values) of data contained in our register
     */
    protected int block_count;/** Number of blocks (values) of data contrained in our register */
    /**
     * Constructor
     *
     * @param data_in
     * @param data_out
     * @param id
     * @param type
     * @param description
     */
    public RegisterModel_Abstract(boolean[] data_in, boolean[] data_out, int id, int type, String description, String absPath, String fname) {
        super(data_in, data_out, id, type, description);
        this.fileName = fname;
        this.absfilePath = absPath;
        //check if files exists if not create them
        if (File(this.absfilePath + this.fileName).isFile() == false) {
            creatFile();
        }
        /*temporary files should never exist and thus have to be created with eatch new object
        initialisation
        */
        creatTmpFile();
    }
    // Getters and Setters \\

    /**
     * getter FileName
     *
     * @return fileName
     * <p>
     * Normaly outside functions should not have acces to the file that contains our
     * original working data. Then sould be modifing only our temporary file and when
     * we recive the signal ( and then only ) should be load the data from the temporary
     * file to our working file.
     */
    protected String getFileName() {
        return fileName;
    }

    /**
     * getter Tmp_fileName
     *
     * @return tmp_fileName
     * <p>
     * Gives a temporary file for modification to be send to the API so that it can be freely modified
     * by other sources.
     * If the file doesn't already exist create it by coping the content of our working
     * file into a new file. This new file will be stored in our temporary repository.
     */
    public String getTmp_fileName() {
        return tmp_fileName;
    }

    /**
     * setter FileName
     *
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
    public void action() {

    }

    /**
     * readBlock
     *
     * @param block
     * @return
     */
    protected boolean[] readBlock(boolean[] block) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * writeBlock
     *
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
     * creatFile
     * Creat a new working file and initialise it to empty
     */
    private void creatFile() {
        try{
            this.data = new File(this.absfilePath+this.fileName);
            if(this.data.createNewFile()){
                System.out.println(this.absfilePath+this.fileName+" File Created");
                init_default(this.data);
            }else System.out.println("File "+this.absfilePath+this.fileName+" already exists");
        }catch(Exception e) {

            // if any error occurs
            e.printStackTrace();
        }
    }

    /**
     * init_default
     * Initialised to default the content of a new working file according to the
     * number of blocks and the individual block size.
     * @param f file we are to initialise
     */
    private void init_default(File f) {
        String line_data, buffer;
        try {

            //init our line data that must be writen to our file
            for (int b = 0; b < this.block_size; b++) {
                line_data += "0 ";
            }
            for (int i = 0; i < this.block_count; i++) {
                buffer += line_data + "\n";
            }
            f.write(buffer);

        } catch (Exception e) {
            // if any error occurs
            e.printStackTrace();
        }
    }

    /**
     * creatTmpFile
     * Create a new temporary file and initialise it's contents to the current
     * contents of our working file.
     */
    private void creatTmpFile() {
        File tmpfile = NULL;
        try{
            if(tmpfile.createTempFile(this.absfilePath,".txt")){
                this.tmp_fileName = tmpfile.getAbsolutePath();
                System.out.println(this.tmp_fileName+" File Created");
                transfert_data(this.data,tmpfile);
            }else System.out.println("File "+this.tmp_fileName+" has an creation error");
        }catch(Exception e) {

            // if any error occurs
            e.printStackTrace();
        }
    }
    /**
     * transfert_data_tmp
     * Transfers data from source file to destination file.
     */
    void transfert_data(File source,File dest){
        //TODO
    }
}