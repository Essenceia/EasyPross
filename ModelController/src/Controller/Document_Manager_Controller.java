package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class Document_Manager_Controller implements Interface.Document_Manager_Interface {

    private static int line_at = 0;
    private static String fileName;
    private static List<String> lines;

    private Document_Manager_Controller() {
    }

    /**
     * Override of open method from Document_Manager_Interface
     *
     * @param fileName
     */
    //@Override
    public static void open(String fileName) {
        //this.fileName = fileName;
        lines = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
            //br returns as stream and convert it into a List
            line_at = 0;
            lines = br.lines().collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Override of close from Document_Manager_Interface
     *
     * @param fileName
     * @return succes or fail
     */
    @Override
    public boolean close(String fileName) {
        return true; // To confirm that the closing succeded -> can be changed if needed
    }

    /**
     * Override of readLine from DocumentManager Not overrided because cannot
     * have static interface
     *
     * @return
     */
    //@Override
    public static String readLine() {
        if (hasNextLine()) {
            return lines.get(line_at++);
        } else {
            System.out.println("Error - document is closed");
            return "";
        }
    }

    public static Boolean hasNextLine() {
        return ((line_at < lines.size()));
    }

    public static void to_begining() {
        line_at = 0;
    }
}
