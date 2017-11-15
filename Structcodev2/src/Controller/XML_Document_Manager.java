package Controller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class XML_Document_Manager implements Interface.XML_Document_Manager{
    protected Document file;
    protected boolean open_no_error;
    public XML_Document_Manager(){
        open_no_error=false;
    }
    @Override
    public boolean open(String fpath) {
        try{
        File fXmlFile = new File("/Users/mkyong/staff.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();
        open_no_error = true;
        }catch (Exception e) {
            e.printStackTrace();
            open_no_error = false;
                    }
        return open_no_error;
    }

    @Override
    public boolean close() {
        open_no_error = false;
        return false;
    }

    @Override
    public NodeList getByTag(String tag) {
        NodeList node;
        if(open_no_error){

        }
        return null;
    }
}
