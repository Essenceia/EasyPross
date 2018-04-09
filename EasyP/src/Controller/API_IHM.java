package Controller;

import jdk.internal.org.objectweb.asm.Opcodes;

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
            switch (Opcode) {
                case 1:
                    String path = nvmsg[1];
                    a.path = path;
                    System.out.print(" loaded module " + path);
                    break;

                case 2:
                case 4:
                case 5:
                    a.check = Integer.parseInt(nvmsg[1]);
                    System.out.println("Opcode result " + Opcode + " " + a.check);

                    break;
                case 3:
                    //parse changed values
                    a.changes= Helper_Data_Handler.parseIdAndDataFromString(nvmsg[1]);

                    break;

                case 6:
                    path = nvmsg[1]; /** < Sa vas planter ici car le simulateur ne vas pas repondre par autres chose que un gros echeque
                    Sur le module bateau charger id 1 n'est pas un registre mais un wire pour continuer les tests if faudrait un model
                    plus simple et unifier. Bref, comportement tout a fait normale. En revanche la il est 3 tres tot donc
                    je rajoute l'unification des donnees + style de xml a ma todo liste et je ne suis plus la ^^
                    A demain */
                    a.path = path;
                    System.out.println("ASKFILEDTA path to file " + path);

                    break;

                case 7:
                    System.out.println("LOADDARAFIEL #" + nvmsg[1] + " " + nvmsg[2] + " " + nvmsg[3]);
                    a.path = nvmsg[2];
                    a.id = Integer.parseInt(nvmsg[1]);
                    a.check = Integer.parseInt((nvmsg[3]));

                    break;
                case 8:
                    System.out.println("DEBUGXMLSAVE "+nvmsg[1]);
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
    private void getDataItemSimul(Integer Opcode, Vector<Integer> Idwire) {
        try {
            String nvmsg = Opcode.toString() + " ";
            nvmsg += Helper_Data_Handler.creatIdString(Idwire);
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
    /*public void getDataRegisterSimul(int Opcode, int IdRegister) {
        try {
            c = new Socket(InetAddress.getLocalHost(), RECIEVER_PORT);

            out = new DataOutputStream(c.getOutputStream());
            out.writeInt(Opcode);
            out.flush();

            out.writeInt(IdRegister);
            out.flush();

            c.close();
            receive();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
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

   /* public void changeDataRegisterSimul(int Opcode, String BufferPath, String AbsolutePath, int IdRegister) {
        try {
            c = new Socket(InetAddress.getLocalHost(), RECIEVER_PORT);

            out = new DataOutputStream(c.getOutputStream());
            out.writeInt(Opcode);
            out.flush();

            out.writeUTF(BufferPath);
            out.flush();

            out.writeUTF(AbsolutePath);
            out.flush();

            out.writeInt(IdRegister);
            out.flush();

            c.close();
            receive();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
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
    /********************************************************************************************************************/

/*
    public void APISender(int input, String target, int Objcode, String NameOrPath, String AbsolutePath, Vector<Boolean> NewValue) {
        a = new answer();
        switch (input) {
            case 1:
                if (target == "s") loadModuleSimul(input, NameOrPath);
                break;

            case 2:
                if (target == "s") simulSimul(input);
                break;

            case 3:
                if (target == "s") getDataItemSimul(input, Objcode);
                break;

            case 4:
                if (target == "s") changeDataItemSimul(input, Objcode, NewValue);
                break;

            case 5:
                if (target == "s") resetSimul(input);
                break;

            case 6:
                if (target == "s") getDataRegisterSimul(input, Objcode);
                break;

            case 7:
                if (target == "s") changeDataRegisterSimul(input, NameOrPath, AbsolutePath, Objcode);
                break;

            default:
                break;
        }
    }*/
    public void APISender(int input, String target, int Objcode, String NameOrPath, Vector<Integer> NewValue, Vector<Data_Tuple> ToSet) {
        a = new answer();
        System.out.println("API sender called ");
        if(target.equals("s")) {
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
        }else{
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

