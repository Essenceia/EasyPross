package Controller;

import Model.Abstract_Classes.Global_Defines_Abstract;

import java.io.*;
import java.net.*;
import java.nio.file.Paths;
import java.util.Vector;
import java.nio.file.Path;

public class Api_Controller {


    java.net.Socket c_read;//client socket
    java.net.Socket c_write;//client socket
    DataInputStream in;//socket input
    BufferedReader inputLine;
    DataOutputStream out;//socket output
    BufferedWriter outputLine;

    //Graph
    private Graph_Manager_Controller Graph;

    //shutdown message has been recived
    public boolean shutdown;


    public Api_Controller() {
        this.shutdown = false;
        //constructor initialise graph
        this.Graph = new Graph_Manager_Controller();
        //for debug
        //Graph.load_new_module("XML_tests/testcomplexePcProgDecode.xml");

        try {
            //s= new ServerSocket(Config_Api.SERVER_PORT);
            c_read = new java.net.Socket(InetAddress.getLocalHost(), Config_Api.CLIENT_PORT_READ);
            c_write = new java.net.Socket(InetAddress.getLocalHost(), Config_Api.CLIENT_PORT_WRITE);
            Helper_Controller.debugMessage4("Communication port opened with API read " + c_read.toString() +
                    " write " + c_write.toString());
            out = new DataOutputStream(c_write.getOutputStream());

            in = new DataInputStream(c_read.getInputStream());
            inputLine = new BufferedReader(new InputStreamReader(in));
            outputLine = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String loadModule(String ModuleName) {
        String modulePath = "";
        Path currentRelativePath = Paths.get("");
        String currentAbsPath = currentRelativePath.toAbsolutePath().toString();
        modulePath = currentAbsPath + Global_Defines_Abstract.SIMULATION_MODULE_XML_LOCATIONS + ModuleName + Global_Defines_Abstract.SIMULATION_MODULE_TYPE;
        Helper_Controller.debugMessage4("Api_Controller::loadModules module to be loaded path " + modulePath);
        this.Graph.load_new_module(modulePath);
        return modulePath;
    }

    private Boolean simul() {
        this.Graph.tick();
        //send ack when tick finished the function will exit
        return true;
    }

    private boolean[] getDataItem(int Id) {
        return Graph.GetDataOnId(Id);
    }

    private Boolean changeDataItem(Vector<Data_Tuple> newData) {
        boolean retVal=true;
        //parse string
        Helper_Controller.debugMessage4("Api_Controller::changeDataItem change string recived :");
        Helper_Data_Handler.printDataTupleArray(newData);
        for (Data_Tuple data:newData
             ) {
            this.Graph.SetDataOnId(data.getId(),data.getboolValues());
        }
        return retVal;
    }

    private Boolean reset() {
        this.Graph.resetGraphValues();
        return true;
    }

    public String getDataRegister(int Id) {
        return this.Graph.GetFileDataOnNode(Id);
    }

    private Boolean changeDataRegister( String AbsolutePath, int Id) {

        return this.Graph.LoadDataOnNode(Id, AbsolutePath);

    }


    /************************************************************************************************************************************/
    /**                                                                                                                                    */
    /**                                this function sends the return of the LoadModule function to the HMI								*/
    /**                                            initialization of the communication interface											*/
    /**the identifier of the operation to be performed and the absolute path to the file containing the module are sent over the channel*/
    /**                                                                                                                                    */
    /************************************************************************************************************************************/

    void sendLoadModule(int Opcode, String pathToModule) {
        try {

            outputLine.write(Opcode + " " + pathToModule);
            outputLine.newLine();
            outputLine.flush();

            Helper_Controller.debugMessage4("test ::" + (outputLine.toString()));
            Helper_Controller.debugMessage4("Send module load finished to ui");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /************************************************************************************************************************************/
    /**                                                                                                                                    */
    /**                                    this function sends the return of the Simul function to the HMI									*/
    /**                                            initialization of the communication interface											*/
    /**                the identifier of the operation to be performed and the success or failure are sent over the channel				*/
    /**                                                                                                                                    */
    /************************************************************************************************************************************/


    void sendSimul(int Opcode, Boolean check) {
        try {
            Integer i = (check) ? 1 : 0;
            outputLine.write(Opcode+" "+i.toString()+"\n");
            outputLine.flush();
            Helper_Controller.debugMessage4("Send simulation finished status "+i.toString());

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /************************************************************************************************************************************/
    /**                                                                                                                                    */
    /**                                this function sends the return of the GetDataItem function to the HMI								*/
    /**                                            initialization of the communication interface											*/
    /**                            the identifier of the operation to be performed, the identifier of the wire								*/
    /**                            and the value of the bits of data running through the wire are sent to the HMI							*/
    /**                                                                                                                                    */
    /************************************************************************************************************************************/


    private void sendGetDataItem(Integer Opcode, Vector<Data_Tuple> data, Boolean check) {
        try {
            String sendmsg = Opcode.toString()+" ";
            sendmsg+= Helper_Data_Handler.Parse_to_String(data)+" ";
            if(check.equals(Boolean.TRUE))sendmsg+="1";
            else sendmsg+="0";
            sendmsg+="\n";
            outputLine.write(sendmsg);
            System.out.println("SendDataItem sent to UI :"+sendmsg);
            outputLine.flush();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /************************************************************************************************************************************/
    /**                                                                                                                                    */
    /**                            this function sends the return of the ChangeDataItem function to the HMI								*/
    /**                                            initialization of the communication interface											*/
    /**                    the identifier of the operation to be performed and the success or failure are sent to the HMI					*/
    /**                                                                                                                                    */
    /************************************************************************************************************************************/


    void sendChangeDataItem(int Opcode, Boolean check) {
        try {
            String msg = Opcode+" ";
            if(check)msg+="1";
            else msg+="0";
            outputLine.write(msg+"\n");
            outputLine.flush();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void sendChangeDataFile(int Opcode, Boolean check, String path , int objectId) {
        try {
            String msg = Opcode+" "+objectId+" "+path+" ";

            if(check)msg+="1";
            else msg+="0";
            outputLine.write(msg+"\n");
            outputLine.flush();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /************************************************************************************************************************************/
    /**                                                                                                                                    */
    /**                                    this function sends the return of the Reset function to the HMI									*/
    /**                                            initialization of the communication interface											*/
    /**                    the identifier of the operation to be performed and the success or failure are sent to the HMI					*/
    /**                                                                                                                                    */
    /************************************************************************************************************************************/


    void sendReset(int Opcode, Boolean check) {
        try {
            String msg= Opcode+" ";
            if(check)msg+="1";
            else msg+="0";
            outputLine.write(msg+"\n");
            outputLine.flush();
            System.out.println("Reset sent with status "+check+" message "+msg);


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /************************************************************************************************************************************/
    /**                                                                                                                                    */
    /**                            this function sends the return of the GetDataRegister function to the HMI								*/
    /**                                            initialization of the communication interface											*/
    /**            the identifier of the operation to be performed and the absolute path to the register file are sent to the HMI			*/
    /**                                                                                                                                    */
    /************************************************************************************************************************************/

    void sendGetDataPath(int Opcode, String pathToRegister) {

        try {
            String msg="";
            Integer check= 1;
            if(pathToRegister.equals(Config_Api.DEFAULT_FILE_PATH))check = 0;
            msg+=Opcode+" "+pathToRegister+" "+check.toString()+"\n";
            outputLine.write(msg);
            outputLine.flush();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /************************************************************************************************************************************/
    /**                                                                                                                                    */
    /**    this function gets the data sent from the HMI and sends it to the appropriate function according to the operation identifier	*/
    /**                                            initialization of the communication interface											*/
    /**            according to the value of the operation identifier, the corresponding function is called with the right attributes		*/
    /**                                                                                                                                    */
    /************************************************************************************************************************************/

    public void ApiReceiver() {
        try {

            Boolean check=true;
            String path;
            Integer Id;
            String recivedMessage = inputLine.readLine();
            Helper_Controller.debugMessage4("Recived ::" + recivedMessage);
            String message[] = recivedMessage.split(" ");
            int Opcode = Integer.parseInt(message[Config_Api.INDEX_OPCODE]);

            switch (Opcode) {
                case 0 :
                    //shutdown message recives
                    this.shutdown = true;
                    Helper_Controller.debugMessage2("Shutdown message recived");
                    break;

                case 1:
                    if(message.length==2) {
                        String ModuleName = message[Config_Api.INDEX_MODULE_NAME];
                        Helper_Controller.debugMessage4("module name recived " + ModuleName);


                        path = loadModule(ModuleName);
                        sendLoadModule(Opcode, path);
                    }else{
                        Helper_Controller.errorMessage("Unexpecte length on opcode 1 gottent "+message.length);
                    }
                    break;

                case 2:
                    Helper_Controller.debugMessage4("Simulation commande recived");
                    check = simul();
                    sendSimul(Opcode, check);
                    break;

                case 3:
                    Vector<Data_Tuple> vecdata = new Vector<>();
                    boolean[] boolvals;
                   Vector<Integer> ids =
                           Helper_Data_Handler.parseIdString(message[Config_Api.INDEX_NODE_ID]);
                    Helper_Controller.debugMessage4("PULLID #" + ids.toString());
                    for (Integer thisId: ids
                         ) {
                        boolvals= getDataItem(thisId);
                        if(boolvals.length!= 0) {
                            vecdata.add(new Data_Tuple(thisId, boolvals));
                        }else{
                            Helper_Controller.errorMessage("Error in Id detected");
                            check = false;
                        }
                    }
                    sendGetDataItem(Opcode, vecdata,check);
                    break;

                case 4:
                    Vector<Data_Tuple> newData = Helper_Data_Handler.parseIdAndDataFromString(
                            message[Config_Api.INDEX_NEW_VALUES_DATA]);

                    Helper_Controller.debugMessage4("COMMITID #" + message[Config_Api.INDEX_NEW_VALUES_DATA]);

                    check = changeDataItem(newData);
                    sendChangeDataItem(Opcode, check);
                    break;

              case 5:

                    check = reset();
                    sendReset(Opcode, check);
                    break;

               case 6:        //ask for data
                    Id = Integer.parseInt(message[Config_Api.INDEX_NODE_ID]);
                    Helper_Controller.debugMessage4("ASKFILEDATA ID#" + Id);

                    path = getDataRegister(Id);
                    sendGetDataPath(Opcode, path);
                    break;
                case 7: //load data from file to id
                    Id = Integer.parseInt(message[Config_Api.INDEX_NODE_ID]);
                    path = message[Config_Api.INDEX_NEW_DATA_FILE];
                    check =changeDataRegister(path,Id);
                    sendChangeDataFile(7,check,path,Id);
                    break;
                case 8:  //debug save current grpah to xml
                    path = message[Config_Api.INDEX_XML_SAVE_PATH];
                    Graph.save_module(path);
                    sendGetDataPath(Opcode,path);
                    break;



                default:Helper_Controller.errorMessage("Unexpected opcode recived "+Opcode);
                    break;
            }

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    protected void finalize() throws Throwable {
        try {
            in.close();
            out.close();
            c_read.close();       // close sokets files
            c_write.close();       // close sokets files
        } finally {
            super.finalize();
        }
    }

}

