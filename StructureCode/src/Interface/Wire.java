package Interface;

import java.util.Vector;

public interface Wire {
    Boolean[] get_data();
    int get_data_size();
    String getDescription();
   // Boolean getActive();
    void put_data_at_index(Boolean data,int index);
}
