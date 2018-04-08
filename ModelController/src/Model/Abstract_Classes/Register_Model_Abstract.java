package Model.Abstract_Classes;

import Controller.Helper_Controller;
import Interface.Object_Interface;
import Model.Normal_Classes.Wire.Wire_Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.stream.Stream;

public abstract class Register_Model_Abstract extends Node_Model_Abstract implements Object_Interface {

    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String WORKING_FILE_FOLDER_PATH = ".." + FILE_SEPARATOR + "Model_CPU" + FILE_SEPARATOR;
    protected String absFilePath;
    protected String fileName;
    protected String tmpFileName;
    protected int blockSize;
    protected int blockCount;
    protected List<String> bufferFile;
    protected boolean update_file_on_change = true;
    protected Path path_to_file;
    private long last_modified_t = 0;
    protected File our_file = null;

    /**
     * @param id
     * @param type
     * @param wireInput
     * @param wireOutput
     * @param description
     * @param absPath
     * @param fileName
     * @param nmblocks
     * @param blockSize
     */
    public Register_Model_Abstract(int id, int type, Vector<Wire_Model> wireInput,
                                   Vector<Wire_Model> wireOutput, String description,
                                   String absPath, String fileName,
                                   int nmblocks, int blockSize) {
        super(id, type,
                "Register model, with " + nmblocks
                        + " of size " + blockSize + " " + description,
                wireInput, wireOutput);
        this.blockSize = blockSize;
        this.blockCount = nmblocks;
        this.fileName = fileName;
        this.absFilePath = absPath;
        String fpath = this.absFilePath + this.fileName;
        //check if files exists if not create them
        our_file = new File(fpath);
        path_to_file = Paths.get(fpath);
        if (!our_file.isFile()) {
            createFile();
        }else{
            //reload buffer form file
        }
        //createTmpFile();
    }

    /**
     * @return
     */
    public String getFileName() {
        return fileName;
    }
    public String getFilePath() {
        return this.absFilePath;
    }

    /**
     * @return
     */
    public String getTmpFileName() {
        return tmpFileName;
    }

    /**
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @param blockNumber
     * @return
     */
    protected boolean[] readBlock(int blockNumber) {
        Stream<String> lines;
        String line = new String();
        String fpath = this.absFilePath + this.fileName;
        try {
            lines = Files.lines(Paths.get(fpath));
            line = lines.skip(blockNumber).findFirst().get();
        } catch (ExportException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return StringToBool(line);
    }

    /**
     * @param newValues
     * @param blockNumber
     */
    protected void writeBlock(boolean[] newValues, int blockNumber) {
        try {
            int lineNo = blockNumber;
            String fpath = this.tmpFileName + this.fileName;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    private void createFile() {
        String filePath = this.absFilePath + this.fileName;
        File newFile;
        try {
            newFile = new File(filePath);
            if (newFile.createNewFile()) {
                System.out.println(filePath + " File Created");
                initDefault(filePath);
            } else {
                System.out.println("File " + filePath + " already exists");
            }
        } catch (IOException e) {
        }
    }

    /**
     * @param file
     */
    private void initDefault(String file) {
        Helper_Controller.debugMessage2("Register_Model_Abstract::initDefault calling to creat file "+file+" block size "+
        this.blockSize+" block count "+this.blockCount);
        String line_data;
        line_data = new String();
        this.bufferFile = new ArrayList<>();
        try {

            //init our line data that must be writen to our file
            for (int b = 0; b < this.blockSize; b++) {
                if(b!= 0 )line_data+=".";
                line_data += "0";
            }
            for (int i = 0; i < this.blockCount; i++) {
                this.bufferFile.add( line_data );
            }
            Helper_Controller.debugMessage0("Buffer "+this.bufferFile.toString());
            writeToFileAccordingToBuffer(file);

        } catch (Exception e) {
            // if any error occurs
            e.printStackTrace();
        }
    }

    /**
     *
     */
    private void createTmpFile() {
        File tmpFile;
        try {
            tmpFile = File.createTempFile(this.absFilePath, ".txt");
            this.tmpFileName = tmpFile.getAbsolutePath();
            System.out.println(this.tmpFileName + " File Created");
           // transfertData(new File(this.absFilePath + this.fileName), tmpFile);

        } catch (IOException e) {
            System.out.println("File " + this.tmpFileName + " has an creation error");
        }
    }

    /**
     * @param source
     */
    public boolean transfertData(File source) {
        boolean result = true;
        BufferedReader in;
        BufferedWriter out;
        String dest = this.absFilePath + this.fileName;
        //check if file is diffrent to the currently loaded file
        if(!source.getAbsolutePath().equals(dest)) {

            Helper_Controller.debugMessage3("Register_Model_Abstract::transfertData transfering data" +
                    "from source "+source.getAbsolutePath()+" to "+dest);
            try {
                FileInputStream fis = new FileInputStream(source);
                in = new BufferedReader(new InputStreamReader(fis));

                FileWriter fstream = new FileWriter(dest, false);
                out = new BufferedWriter(fstream);

                String aLine;
                Helper_Controller.debugMessage3("Writing to file :");
                while ((aLine = in.readLine()) != null) {
                    Helper_Controller.debugMessage3(aLine);
                    out.write(aLine);
                    out.newLine();
                }
                try {
                    in.close();
                    out.close();

            } catch (IOException ex) {
                    ex.printStackTrace();
                    result = false;
                }
            } catch (IOException ex) {
                Helper_Controller.errorMessage("File not found");
                ex.printStackTrace();
                result = false;
            }
        }
        return result;
    }



    /**
     * @param input
     * @return
     */
    private boolean[] StringToBool(String input) {
        int i = 0;
        String[] splited = input.split("\\.");
        boolean[] retarray = new boolean[this.blockSize];
        if (splited.length < this.blockSize) {
            Helper_Controller.errorMessage("Error : unexpected lenght of sting, block "
                    + "size should be " + this.blockSize + " but length of string gotten"
                    + "is only " + splited.length);
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

    /**
     * @param input
     * @return
     */
    private String boolToString(boolean[] input) {
        int i = 0;
        String nv_string = "";
        boolean to_write[] = new boolean[this.blockSize];
        if (input.length != this.blockSize) {
            Helper_Controller.errorMessage("Error : unexpected lenght of sting, block "
                    + "size should be " + this.blockSize + " but length of boolean gotten"
                    + " is " + input.length);
            Arrays.fill(input, false);
        } else {
            to_write = input.clone();
        }
        for (boolean val : to_write) {
            if(i!= 0 & i!= (to_write.length ))nv_string+=".";
            if (!val) {
                nv_string += "0";
            } else {
                nv_string += "1";
            }
            i++;

        }//nv_string+="\n";
        return nv_string;
    }

    /**
     * @param line_index
     * @return
     */
    protected boolean[] readFile(int line_index) {
        Helper_Controller.debugMessage3("Register_Model_Abstract::readFile read line at index "+line_index);
        boolean return_value[] = new boolean[this.blockSize];
        String line;
        line_index = checkLineValide(line_index);
        if (this.bufferFile == null) {
            reloadFileBuffer();
        }
        if (this.bufferFile != null) {
            line = this.bufferFile.get(line_index);
            return_value = StringToBool(line);

        }
        String debugMessag = "Register_Model_Abstract::readFile return boolean values : ";
        for(boolean debugBool : return_value){
            debugMessag += debugBool+" ";
        }
        Helper_Controller.debugMessage3(debugMessag);
        return return_value;
    }

    /**
     * @param fdest
     */
    private void writeToFileAccordingToBuffer(String fdest) {
        Helper_Controller.debugMessage0("Register_Model_Abstract::writeToFileAccordingToBuffer startint write\n data "+this.bufferFile.toString());
        Writer writer;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fdest), "utf-8"));
            for (String towrite : this.bufferFile) {
                writer.write(towrite+"\n");
                Helper_Controller.debugMessage0("writing ::"+towrite+";");
            }
            try {
                writer.close();
            } catch (IOException ex) {
                // if any error occurs
                ex.printStackTrace();
            }
        } catch (IOException ex) {
            // if any error occurs
            ex.printStackTrace();
        }
        Helper_Controller.debugMessage0("Register_Model_Abstract::writeToFileAccordingToBuffer finished");

    }

    protected void writeToFile(int line_index,boolean[] value){
        Helper_Controller.debugMessage0("Register_Model_Abstract::writeToFile called");
        int return_value = 0;
        String line;
        line = boolToString(value);
        line_index = checkLineValide(line_index);
        if(this.bufferFile == null){
            reloadFileBuffer();
        }
        if(this.bufferFile != null){
            //update a line in a buffer
            //check if it is diffrent
            if(!this.bufferFile.get(line_index).equals(line)) {
                //we found a diffrence so we update our buffer value
                Helper_Controller.debugMessage0("Register_Model_Abstract::writeToFile update buffer value line "+line );
                this.bufferFile.set(line_index, line);
                //we write the diffrence to our file
                if(update_file_on_change){
                    //time stamp will be automaticaly set
                    writeToFileAccordingToBuffer(this.absFilePath + this.fileName);
                }

            }
        }
        Helper_Controller.debugMessage2("Register_Model_Abstract::writeToFile end");

    }

    /**
     *
     */
    public void reloadFileBuffer() {
        Helper_Controller.debugMessage0("Register_Model_Abstract::reloadFileBuffer called");
        if (this.last_modified_t != this.our_file.lastModified()) {
            try {
                Helper_Controller.debugMessage2("Register_Model_Abstract::reloadFileBuffer modifing internal values");
                this.last_modified_t = this.our_file.lastModified(); //unpading saved timestamp on file
                this.bufferFile = new ArrayList<>(Files.readAllLines(this.path_to_file, StandardCharsets.UTF_8));
                Helper_Controller.debugMessage0("Buffer read from file "+this.bufferFile.toString());

            } catch (IOException e) {
                System.out.println("Can't creat read buffer on file");
                e.printStackTrace();
            }
        }
        Helper_Controller.debugMessage0("Register_Model_Abstract::reloadFileBuffer finised");

    }

    /**
     * @param line_index
     * @return
     */
    private int checkLineValide(int line_index) {
        if (line_index > this.blockCount) {
            System.out.println("Error :: index of line is out of bounds got " + line_index + " max expected value " + this.blockSize);
            return line_index % this.blockSize;
        }
        return line_index;
    }

    private void writeChangesToFile() {
        throw new UnsupportedOperationException("Not supported yet.");

    }

    public int getBlockCount(){
        return this.blockCount;
    }
    public int getBlockSize(){
        return this.blockSize;
    }

    /**
     * resterValues
     *
     * Will not be resetting the values in the files only the currently buffered data and will be
     * forcing a re-reading of all data in the files.
     */
    @Override
    public void resetValues() {
        //invalude timestamp
        last_modified_t = 0;
        //force update of internal buffer
        reloadFileBuffer();
    }
}
