package Model.AbstractClasses;

import Model.Wire.WireModel;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.server.ExportException;
import java.util.Arrays;
import java.util.Vector;
import java.util.stream.Stream;

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
public abstract class RegisterModel_Abstract extends NodeModel_Abstract {
    /**
     * Constant values
     * <p>
     * Define the realtive paths to where the files will be stored
     * The temporary file will be stored in the systems default location for temporary
     * files.
     */

    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String WORKING_FILE_FOLDER_PATH = ".." + FILE_SEPARATOR + "Model_CPU" + FILE_SEPARATOR;

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
    //protected boolean lock;/** @deprecated lock will be replaced by the use of a temporary file */
    protected int block_size;
    /**
     * Size of one block (values) of data contained in our register
     */
    protected int block_count;/** Number of blocks (values) of data contrained in our register */
    /**
     * Contructor
     * <p>
     * Build a new object of the register model class.
     * <p>
     * As this is the class from witch all other instances of register inherite verification of the
     * existance of files as well as there initialisation are taken care of here.
     * We also create our temporary file and initialise it to the contents of our working file's data.
     * <p>
     * TODO : Verify the file has the correct number of data blocks
     *
     * @param id          - unique id of our node
     * @param type        - type of out node
     * @param description - textual descritption of our node
     * @param absPath     - absolute path to the root folder where we will be adding our working files
     * @param fname       - name of our working file in the root folder
     * @param wire_input  - wire comming in as input to our node
     * @param wire_output - wire going out as output of our node
     */
    public RegisterModel_Abstract(int id, int type, Vector<WireModel> wire_input,
                                  Vector<WireModel> wire_output, String description,
                                  String absPath, String fname,
                                  int nmblocks, int block_size) {
        super(id, type,
                "Register model, with " + nmblocks +
                        " of size " + block_size + " " + description,
                wire_input, wire_output);
        this.block_size = block_size;
        this.block_count = nmblocks;
        this.fileName = fname;
        this.absfilePath = absPath;
        String fpath = this.absfilePath + this.fileName;
        //check if files exists if not create them
        if (!(new File(fpath)).isFile()) {
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
     * readBlock
     * <p>
     * Read the value stored in a block of our data, we address them by block number.
     *
     * @param block_number - the block number we should be reading
     * @return the value of the block we read , if an error is encountered we will return only zeros
     */
    protected boolean[] readBlock(int block_number) {
        //open our working file
        Stream<String> lines;
        String line = new String();
        String fpath = this.absfilePath + this.fileName;
        try {
            lines = Files.lines(Paths.get(fpath));
            //goto line numbered block number
            line = lines.skip(block_number).findFirst().get();
        } catch (ExportException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String_to_bool(line);

    }

    /**
     * writeBlock
     *
     * @param newValues   - new value for a block to be writen in our file
     * @param blockNumber - block number we should be writing to
     */
    protected void writeBlock(boolean[] newValues, int blockNumber) {
        //todo improve
        try {
            int lineNo = blockNumber;
            String fpath = this.tmp_fileName + this.fileName;
            LineNumberReader r = new LineNumberReader(new FileReader(fpath));
            while (true) {
                String line = r.readLine();
                if (line == null) {
                    break;
                }
                if (r.getLineNumber() != lineNo) {
                    System.out.println(line);
                }
            }
        } catch (Exception e) {
            // if any error occurs
            e.printStackTrace();
        }
    }

    /**
     * creatFile
     * Creat a new working file and initialise it to empty
     */
    private void creatFile() {
        String fpath = this.absfilePath + this.fileName;
        File newfile;
        try {
            newfile = new File(fpath);
            if (newfile.createNewFile()) {
                System.out.println(fpath + " File Created");
                init_default(fpath);
            } else System.out.println("File " + fpath + " already exists");
        } catch (Exception e) {

            // if any error occurs
            e.printStackTrace();
        }
    }

    /**
     * init_default
     * Initialised to default the content of a new working file according to the
     * number of blocks and the individual block size.
     *
     * @param f file we are to initialise
     */
    private void init_default(String f) {
        String line_data, buffer;
        line_data = new String();
        buffer = new String();
        try {

            //init our line data that must be writen to our file
            for (int b = 0; b < this.block_size; b++) {
                line_data += "0 ";
            }
            for (int i = 0; i < this.block_count; i++) {
                buffer += line_data + "\n";
            }
            write_to_file(f, buffer);

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
        File tmpfile;
        try {
            tmpfile = File.createTempFile(this.absfilePath, ".txt");
            this.tmp_fileName = tmpfile.getAbsolutePath();
            System.out.println(this.tmp_fileName + " File Created");
            transfert_data(new File(this.absfilePath + this.fileName), tmpfile);

        } catch (Exception e) {
            System.out.println("File " + this.tmp_fileName + " has an creation error");
            // if any error occurs
            e.printStackTrace();
        }
    }

    /**
     * transfert_data_tmp
     * Transfers data from source file to destination file.
     */
    private void transfert_data(File source, File dest) {
        BufferedReader in;
        BufferedWriter out;
        // File fin = new File(source);
        try {
            FileInputStream fis = new FileInputStream(source);
            in = new BufferedReader(new InputStreamReader(fis));

            FileWriter fstream = new FileWriter(dest, true);
            out = new BufferedWriter(fstream);

            String aLine;
            while ((aLine = in.readLine()) != null) {
                //Process each line and add output to Dest.txt file
                out.write(aLine);
                out.newLine();
            }
            try {
                // do not forget to close the buffer reader
                in.close();
                // close buffer writer
                out.close();
            } catch (Exception ex) {
                // if any error occurs
                ex.printStackTrace();
            }
        } catch (Exception ex) {
            // if any error occurs
            ex.printStackTrace();
        }


    }

    private void write_to_file(String fdest, String data) {
        Writer writer;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fdest), "utf-8"));
            writer.write(data);
            try {
                writer.close();
            } catch (Exception ex) {
                // if any error occurs
                ex.printStackTrace();
            }
        } catch (IOException ex) {
            // if any error occurs
            ex.printStackTrace();
        }
    }

    /**
     * String_to_bool
     * <p>
     * Convert a string got from our block into a array of bool
     *
     * @param input - String read from our block in our file
     * @return - array of bool
     */
    private boolean[] String_to_bool(String input) {
        int i = 0;
        String[] splited = input.split("\\s+");
        boolean[] retarray = new boolean[this.block_size];
        if (splited.length < this.block_size) {
            System.err.println("Error : unexpected lenght of sting, block " +
                    "size should be " + this.block_size + " but length of string gotten" +
                    "is only " + splited.length);
            //fill up boolean array with false
            Arrays.fill(retarray, false);
        }
        for (String val : splited) {
            switch (val) {
                case "0":
                    retarray[i] = false;
                    break;
                case "1":
                    retarray[i] = true;
                    break;
                default:
                    System.err.println("Unexpected string value " + val);
                    retarray[i] = false;
                    break;
            }
            i++;
        }
        return retarray;
    }

}