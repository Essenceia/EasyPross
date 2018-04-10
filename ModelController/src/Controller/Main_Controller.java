package Controller;
import Model.Normal_Classes.Probe.Probe_End_Model;
import Model.Normal_Classes.Probe.Probe_Start_Model;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main_Controller {

    public static void main(String[] args) {

        Api_Controller api = new Api_Controller();

        while(!api.shutDown){
            api.ApiReceiver();
        }



    }
   
}
