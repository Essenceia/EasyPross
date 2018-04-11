package Controller;

import java.io.*;
import java.net.*;
import java.util.Vector;

public class API_IHM {
    final int SENDER_PORT = 2016;
    final int RECIVER_PORT = 2017;

    static java.net.Socket c_write;//client socket
    static java.net.Socket c_read;//client socket
    static BufferedWriter outputLine;//socket output
    static BufferedReader inputLine;
    private ServerSocket s_write;
    private ServerSocket s_read;

    answer a;

    public API_IHM() {
        try {
            System.out.println("Trying to open new sockets: waiting for simulator to be launched");
            s_write = new ServerSocket(SENDER_PORT);
            s_read = new ServerSocket(RECIVER_PORT);
            c_write = s_write.accept();
            c_read = s_read.accept();


            System.out.println("Port oppened on server write" + c_write.toString() + " read " + c_read.toString());

            outputLine = new BufferedWriter(new OutputStreamWriter(c_write.getOutputStream(), "UTF-8"));

            inputLine = new BufferedReader(new InputStreamReader(new DataInputStream(c_read.getInputStream())));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /********************************************************************************************************************/
    /**                                                                                                                    */
    /**                    s is the socket that acts as a server to get the returns from the simulator						*/
    /**                c is the client that acts as a client and sends the necessary arguments to the simulator			*/
    /**                            in is the input stream through which the data can be read								*/
    /**                            out is the output stream through which the data can be sent								*/
    /**                                                                                                                    */
    /********************************************************************************************************************/


    class answer {
        public String path;
        public int check;
        public Vector<Data_Tuple> changes;
        public int id;

        public answer() {
            path = "";
            check = -1;
            changes = new Vector<>();
            id = -1;
        }

    }


    /********************************************************************************************************************/
    /**                                                                                                                    */
    /**            this function gets the returns from the simulator and saves it in other classes' attributes				*/
    /**                                    initialization of the communication interface									*/
    /**            according to the value of the operation identifier, the corresponding data is saved in attributes		*/
    /**                                                                                                                    */
    /********************************************************************************************************************/

    public void receive() {
        try {
            String nvmsg[];
            String tmp;
            tmp = inputLine.readLine();
            System.out.println("Recived :: " + tmp);
            nvmsg = tmp.split(" ");
            int Opcode = Integer.parseInt(nvmsg[0]);
            a.check = 0; /** By defult we have detected an error */
            switch (Opcode) {
                case 1:
                    if (nvmsg.length == Config.API_RESPONSE_LENGTH_1) {
                        String path = nvmsg[1];
                        a.path = path;
                        System.out.print(" loaded module " + path);
                    } else {
                        System.out.println("1:"+Config.API_ERROR_RESPONSE_SIZE);
                    }
                    break;

                case 2:
                case 4:
                case 5:
                    if (nvmsg.length == Config.API_RESPONSE_LENGTH_2_4_5) {
                        if (nvmsg[1].equals(Config.BOOLEAN_ERROR)) {
                            System.out.println("2,4,5:"+Config.API_ERROR_BOOLEAN);
                        }
                        a.check = Integer.parseInt(nvmsg[1]);
                        System.out.println("Opcode result " + Opcode + " " + a.check);
                    } else {
                        System.out.println("2,4,5:"+Config.API_ERROR_RESPONSE_SIZE);
                    }
                    break;
                case 3:
                    if(nvmsg.length == Config.API_RESPONSE_LENGTH_3) {
                        System.out.println("Comapring data flag '"+nvmsg[2]+"' expected '"+Config.BOOLEAN_VALIDE+"'"+" result of" +
                                "equql is "+nvmsg[2].equals(Config.BOOLEAN_VALIDE));
                        if(nvmsg[2].equals(Config.BOOLEAN_VALIDE)) {
                            //parse changed values
                            a.check = 1;
                            a.changes = Helper_Data_Handler.parseIdAndDataFromString(nvmsg[1]);
                        }else{
                            System.out.println("3:"+Config.API_ERROR_BOOLEAN);
                        }
                    }else{
                        System.out.println("3:"+Config.API_ERROR_RESPONSE_SIZE);
                    }
                    break;

                case 6:
                    if (nvmsg.length == Config.API_RESPONSE_LENGTH_6) {
                        //check boolean
                        if (nvmsg[2].equals(Config.BOOLEAN_VALIDE)) {
                            a.path = nvmsg[1];
                            a.check = 1;
                            System.out.println("ASKFILEDTA path to file " + a.path);
                        } else {
                            System.out.println("6:"+Config.API_ERROR_BOOLEAN);
                        }
                    } else {
                        System.out.println("6:"+Config.API_ERROR_RESPONSE_SIZE);
                    }
                    break;

                case 7:
                    if (nvmsg.length == Config.API_RESPONSE_LENGTH_7) {
                        if (nvmsg[3].equals(Config.BOOLEAN_VALIDE)) {
                            System.out.println("LOADDARAFIEL #" + nvmsg[1] + " " + nvmsg[2] + " " + nvmsg[3]);
                            a.path = nvmsg[2];
                            a.id = Integer.parseInt(nvmsg[1]);
                            a.check = Integer.parseInt((nvmsg[3]));
                        } else {
                            System.out.println("7:"+Config.API_ERROR_BOOLEAN);
                        }
                    } else {
                        System.out.println("7:"+Config.API_ERROR_RESPONSE_SIZE);
                    }
                    break;
                case 8:
                    System.out.println("DEBUGXMLSAVE " + nvmsg[1]);
                    a.path = nvmsg[1];
                    break;
                default:
                    System.out.println("Error : undefined opcode " + Opcode);
                    break;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /********************************************************************************************************************/
    /**                                                                                                                    */
    /**        this function sends the necessary arguments to the simulator to load the selected module in the interface	*/
    /**                                    initialization of the communication interface									*/
    /**            the identifier of the operation to be performed and the module name are sent over the channel			*/
    /**                                                                                                                    */
    /********************************************************************************************************************/

    public void loadModuleSimul(int Opcode, String ModuleName) {
        try {

            outputLine.write(Opcode + " " + ModuleName + "\n");
            System.out.println("Load module sent " + outputLine.toString());
            outputLine.flush();
            receive();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /********************************************************************************************************************/
    /**                                                                                                                    */
    /**                this function sends the necessary arguments to the simulator to launch the simulation				*/
    /**                                    initialization of the communication interface									*/
    /**                        the identifier of the operation to be performed is sent over the channel					*/
    /**                                                                                                                    */
    /********************************************************************************************************************/

    private void simulSimul() {
        try {
            System.out.println("Sending simulation commande to server");
            outputLine.write(2 + "\n");
            outputLine.flush();

            receive();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /********************************************************************************************************************/
    /**                                                                                                                    */
    /**        this function sends the necessary arguments to the simulator to get the bits of data running through a wire	*/
    /**                                    initialization of the communication interface									*/
    /**    the identifier of the operation to be performed and the identifier of the wire are sent over the channel		*/
    /**                                                                                                                    */
    /********************************************************************************************************************/


  /* public void getDataItemSimul(int Opcode, int IdWire) {
        try {
            c = new Socket(InetAddress.getLocalHost(), RECIEVER_PORT);

            out = new DataOutputStream(c.getOutputStream());
            out.writeInt(Opcode);
            out.flush();

            out.writeInt(IdWire);
            out.flush();

            c.close();
            receive();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    private void getDataItemSimul(Integer Opcode, String Idwire) {
        try {
            String nvmsg = Opcode.toString() + " ";
            nvmsg += Idwire;
            System.out.println("Message sent to simulator ::" + nvmsg);

            outputLine.write(nvmsg + "\n");
            outputLine.flush();

            receive();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /********************************************************************************************************************/
    /**                                                                                                                    */
    /**this function sends the necessary arguments to the simulator to change the bits of data running through a wire	*/
    /**                                    initialization of the communication interface									*/
    /**                    the identifier of the operation to be performed, the identifier of the wire						*/
    /**                                    and the new data are sent over the channel										*/
    /**                                                                                                                    */
    /********************************************************************************************************************/

    private void changeDataItemSimul(int Opcode, Vector<Data_Tuple> newData) {
        try {
            String msg = Opcode + " ";
            int i = 0;
            for (Data_Tuple data : newData
                    ) {
                msg += data.getId() + ":" + data.getStringValues();
                if (i != newData.size() - 1) msg += ",";
                i++;
            }
            msg += "\n";


            outputLine.write(msg);
            outputLine.flush();
            System.out.println("changeDataItemSimul sent ::" + msg);

            receive();
        } catch (UnknownHostException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
    /********************************************************************************************************************/
    /**                                                                                                                    */
    /**            this function sends the necessary arguments to the simulator to reset the module settings				*/
    /**                                initialization of the communication interface										*/
    /**                    the identifier of the operation to be performed is sent over the channel						*/
    /**                                                                                                                    */
    /********************************************************************************************************************/


   /* public void resetSimul(int Opcode) {
        try {
            c = new Socket(InetAddress.getLocalHost(), RECIEVER_PORT);

            out = new DataOutputStream(c.getOutputStream());
            out.writeInt(Opcode);
            out.flush();

            c.close();
            receive();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    public void resetSimul(int Opcode) {
        try {

            outputLine.write(Opcode + "\n");
            outputLine.flush();

            receive();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /********************************************************************************************************************/
    /**                                                                                                                    */
    /**this function sends the necessary arguments to the simulator to request a file with the contents of a register	*/
    /**                                initialization of the communication interface										*/
    /**the identifier of the operation to be performed and the identifier of the register are sent over the channel		*/
    /**                                                                                                                    */
    /********************************************************************************************************************/
    public void getDataRegisterSimul(int Opcode, int IdRegister) {
        try {
            String msg = Opcode + " " + IdRegister + "\n";
            outputLine.write(msg);
            outputLine.flush();

            System.out.println("Get data register simulation message sent " + msg);

            receive();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /********************************************************************************************************************/
    /**                                                                                                                    */
    /**            this function sends the necessary arguments to the simulator to change the content of a register		*/
    /**                                    initialization of the communication interface									*/
    /**                        the identifier of the operation to be performed, the path to the buffer,					*/
    /**            the absolute path to the register file and the register identifier are sent over the channel			*/
    /**                                                                                                                    */
    /********************************************************************************************************************/

    public void changeDataRegisterSimul(int Opcode, String NameOrPath, Integer Objcode) {
        try {
            String msg = Opcode + " " + Objcode.toString() + " " + NameOrPath + "\n";
            outputLine.write(msg);
            outputLine.flush();
            System.out.println("Load file " + NameOrPath + "to node with id " + Objcode + "\n" +
                    "message sent ::" + msg);
            receive();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendShutdown(int Opcode) {
        try {
            String msg = Opcode + "\n";
            outputLine.write(msg);
            outputLine.flush();

            System.out.println("Sending shutdown message " + msg);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void debugSaveCurrentXML(int Opcode, String path) {
        try {
            String msg = Opcode + " " + path + "\n";
            outputLine.write(msg);
            outputLine.flush();

            System.out.println("Debug save current xml to file message sent " + msg);

            receive();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /********************************************************************************************************************/
    /**                                                                                                                    */
    /**                        this function chooses what action to take according to the operation asked					*/
    /**                    selection of the data to be sent according to the operation code sent in parameter				*/
    /**                    the arguments to be sent to the simulator are forwarded to the appropriate function				*/
    /**                                                                                                                    */

    public void APISender(int input, String target, int Objcode, String NameOrPath, String NewValue, Vector<Data_Tuple> ToSet) {
        a = new answer();
        System.out.println("API sender called ");
        if (target.equals("s")) {
            switch (input) {
                case 0: //quit
                    sendShutdown(input);
                    break;
                case 1:
                    loadModuleSimul(input, NameOrPath);
                    break;

                case 2:
                    simulSimul();
                    break;

                case 3:
                    getDataItemSimul(input, NewValue);
                    break;

                case 4:
                    changeDataItemSimul(input, ToSet);
                    break;

                case 5:
                    resetSimul(input);
                    break;

                case 6:
                    getDataRegisterSimul(input, Objcode);
                    break;

                case 7:
                    changeDataRegisterSimul(input, NameOrPath, Objcode);
                    break;

                case 8: //ask to save current graph to xml
                    debugSaveCurrentXML(input, NameOrPath);
                    break;

                default:
                    break;
            }
        } else {
            System.out.println("Error::unsupported target to sent commande to");
        }
    }

    protected void finalize() throws Throwable {
        try {
            s_write.close();        // close soket
        } finally {
            super.finalize();
        }
    }
}

