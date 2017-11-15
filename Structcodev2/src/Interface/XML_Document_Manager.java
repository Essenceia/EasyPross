package Interface;

import org.w3c.dom.NodeList;

public interface XML_Document_Manager {
    boolean open(String fpath);
    boolean close();
    NodeList getByTag(String tag);
}
