package Controller;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
public final class Document_Manager {
    private static int line_at=0;

    private static String fileName;
    private static List<String> lines;
    private Document_Manager(){};
    public static void open(String f_abs_path) {

        fileName = f_abs_path;
        lines = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {

            //br returns as stream and convert it into a List
            line_at=0;
            lines= br.lines().collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public static String getNextLine(){
        if(hasNextLine()){
            return lines.get(line_at++);
        }else{
            System.out.println("Error - document is closed");
            return "";
        }
    }
    public static Boolean hasNextLine(){
        return ((line_at < lines.size())? true:false);
    }
    public static void to_begining(){
        line_at = 0;
    }
}
