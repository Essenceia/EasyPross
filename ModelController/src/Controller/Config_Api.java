package Controller;

public class Config_Api {
        /**
         * Ports to send recivce data on sockets : conncection to the API
         */
        public static final int CLIENT_PORT_READ = 2016;
        public static final int CLIENT_PORT_WRITE = 2017;
        public  static final String SOCKET_NAME = "Simulator_Client";

        /**
         * Opcodes of commande used when communicating between UI <--> Simulator with the API
         *
         *      UI < -- > API < -- > Simulator
         */
        public static final int OPCODE_LOADMODULE = 1;
        public static final int OPCODE_SIMU = 2;
        public static final int OPCODE_PULLID = 3;
        public static final int OPCODE_COMMITID = 4;
        public static final int OPCODE_RESET = 5;
        public static final int OPCODE_ASKDATA = 6;
        public static final int OPCODE_LOADDATA = 7;
        /**
         * Message format
         */
        public  static final int INDEX_OPCODE = 0 ;
        public static final int INDEX_MODULE_NAME = 1;
        public static final int INDEX_NODE_ID = 1;
        public static final int INDEX_NEW_VALUES_DATA = 1;
        public static final int INDEX_NEW_DATA_FILE = 2;
        public static final int INDEX_XML_SAVE_PATH = 1;
        /**
         * Default values
         */
        public static final String DEFAULT_FILE_PATH = "error_in_querry";

}
