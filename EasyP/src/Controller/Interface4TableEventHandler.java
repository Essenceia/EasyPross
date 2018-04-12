package Controller;

import javafx.scene.control.TableColumn.CellEditEvent;

public interface Interface4TableEventHandler {
	//public void action();
	public void setOnEditCommit(CellEditEvent<?,?> event);

}
