package Controller;

public class Config {

    public static final String OS_SEPERATOR = System.getProperty("file.separator");
    public static final String PATH_BASE= System.getProperty("user.dir") + OS_SEPERATOR;
    public static final String PATH_FILE = PATH_BASE + "src" +System.getProperty("file.separator");
    public static final double LINUX_MOUSE_OFFSET = 115;
    public static final double WINDOWS_MOUSE_OFFSET = 109;
    /**
     * API Configurations
     */

    /** Boolean error codes */
    public static final String BOOLEAN_VALIDE = "1";
    public static final String BOOLEAN_ERROR = "0";
    public static  final String API_ERROR_BOOLEAN = "Error :: Error flag raised in responsse";

    /** Default response message lenght if incorrect lenght we have a missformed querry */
    public static  final String API_ERROR_RESPONSE_SIZE = "Error :: Missformed response, incorrect lenght";
    public static final int API_RESPONSE_LENGTH_6 = 3;
    public static final int API_RESPONSE_LENGTH_2_4_5 = 2;
    public static final int API_RESPONSE_LENGTH_1 = 2;
    public static final int API_RESPONSE_LENGTH_3 = 3;
    public static final int API_RESPONSE_LENGTH_7 = 4;


}
