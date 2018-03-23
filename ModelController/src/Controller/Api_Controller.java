package Controller;

import Model.Abstract_Classes.Global_Defines_Abstract;

import java.io.*;
import java.net.*;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.nio.file.Path;

public class Api_Controller {


	public static ServerSocket s;//server socket
	public static java.net.Socket c;//client socket
	public static DataInputStream in;//socket input
	public static DataOutputStream out;//socket output

	//Graph
	private Graph_Manager_Controller Graph;


	public Api_Controller(){
		//constructor initialise graph
		Graph_Manager_Controller Graph = new Graph_Manager_Controller();
	}

	//TODO a remplacer la ou il faut
	
	private String loadModule(String ModuleName)
	{
		String modulePath = "";
		Path currentRelativePath = Paths.get("");
		String currentAbsPath = currentRelativePath.toAbsolutePath().toString();
		modulePath = currentAbsPath+ Global_Defines_Abstract.SIMULATION_MODULE_XML_LOCATIONS+ModuleName+Global_Defines_Abstract.SIMULATION_MODULE_TYPE;
		Helper_Controller.debugMessage4("Api_Controller::loadModules module to be loaded path "+modulePath);
		this.Graph.load_new_module(modulePath);
		return modulePath;
	}
	
	private Boolean simul()
	{
		this.Graph.tick();
		//send ack when tick finished the function will exit
		return true;
	}
	
	private Vector<Boolean> getDataItem(int Id)
	{
		Vector<Boolean> dataAtID = new Vector(Arrays.asList(Graph.GetDataOnId(Id)));
		return dataAtID;
	}
	
	private Boolean changeDataItem(int Id, String changes)
	{
		boolean retVal;
		//parse string
		Helper_Controller.debugMessage4("Api_Controller::changeDataItem change string recived "+changes);
		boolean data[] = Helper_Controller.helperStringToBool(changes);
		retVal= this.Graph.SetDataOnId(Id,data);
		return retVal;
	}
	
	static Boolean reset()
	{
		return true;
	}
	
	public String getDataRegister(int Id)
	{
		return this.Graph.GetFileDataOnNode(Id);
	}
	
	private Boolean changeDataRegister(String ModuleName, String AbsolutePath, int Id)
	{
		//todo is module name the file name ?
		return this.Graph.LoadDataOnNode(Id, AbsolutePath);

	}
	
	
	
	
	
	
	
	
	
	
	/************************************************************************************************************************************/
	/**																																	*/
	/**								this function sends the return of the LoadModule function to the HMI								*/
	/**											initialization of the communication interface											*/
	/**the identifier of the operation to be performed and the absolute path to the file containing the module are sent over the channel*/
	/**																																	*/
	/************************************************************************************************************************************/
	
	void sendLoadModule(int Opcode,String pathToModule)
	{
		try {
			c = new java.net.Socket(InetAddress.getLocalHost(),84);
			
			out = new DataOutputStream(c.getOutputStream());
			out.writeInt(Opcode);
			out.flush();
			
			out.writeUTF(pathToModule);
			out.flush();
			
			c.close();			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/************************************************************************************************************************************/
	/**																																	*/
	/**									this function sends the return of the Simul function to the HMI									*/
	/**											initialization of the communication interface											*/
	/**				the identifier of the operation to be performed and the success or failure are sent over the channel				*/
	/**																																	*/
	/************************************************************************************************************************************/
	
	
	void sendSimul(int Opcode,Boolean check)
	{
		try {
			c = new java.net.Socket(InetAddress.getLocalHost(),84);
			
			out = new DataOutputStream(c.getOutputStream());
			out.writeInt(Opcode);
			out.flush();
			
			out.writeInt((check) ? 1 : 0);
			out.flush();
			
			c.close();			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/************************************************************************************************************************************/
	/**																																	*/
	/**								this function sends the return of the GetDataItem function to the HMI								*/
	/**											initialization of the communication interface											*/
	/**							the identifier of the operation to be performed, the identifier of the wire								*/
	/**							and the value of the bits of data running through the wire are sent to the HMI							*/
	/**																																	*/
	/************************************************************************************************************************************/
	
	
	void sendGetDataItem(int Opcode,int Id, Vector<Boolean>wireData)
	{
		try {
			c = new java.net.Socket(InetAddress.getLocalHost(),84);
			
			out = new DataOutputStream(c.getOutputStream());
			out.writeInt(Opcode);
			out.flush();
			
			out.writeInt(Id);
			out.flush();
			
			for(int i=0;i<wireData.size();i++)
			{
				out.writeInt((wireData.get(i)) ? 1 : 0);
				out.flush();
			}
			out.writeInt(5);
			out.flush();
			
			c.close();			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/************************************************************************************************************************************/
	/**																																	*/
	/**							this function sends the return of the ChangeDataItem function to the HMI								*/
	/**											initialization of the communication interface											*/
	/**					the identifier of the operation to be performed and the success or failure are sent to the HMI					*/
	/**																																	*/
	/************************************************************************************************************************************/
	
	
	void sendChangeDataItem(int Opcode, Boolean check)
	{
		try {
			c = new java.net.Socket(InetAddress.getLocalHost(),84);
			
			out = new DataOutputStream(c.getOutputStream());
			out.writeInt(Opcode);
			out.flush();
			
			out.writeInt((check) ? 1 : 0);
			out.flush();
			
			c.close();			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/************************************************************************************************************************************/
	/**																																	*/
	/**									this function sends the return of the Reset function to the HMI									*/
	/**											initialization of the communication interface											*/
	/**					the identifier of the operation to be performed and the success or failure are sent to the HMI					*/
	/**																																	*/
	/************************************************************************************************************************************/
	
	
	void sendReset(int Opcode, Boolean check)
	{
		try {
			c = new java.net.Socket(InetAddress.getLocalHost(),84);
			
			out = new DataOutputStream(c.getOutputStream());
			out.writeInt(Opcode);
			out.flush();
			
			out.writeInt((check) ? 1 : 0);
			out.flush();
			
			c.close();			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/************************************************************************************************************************************/
	/**																		   		  	 												*/
	/**							this function sends the return of the GetDataRegister function to the HMI								*/
	/**											initialization of the communication interface											*/
	/**			the identifier of the operation to be performed and the absolute path to the register file are sent to the HMI			*/
	/**																				 			      	 								*/
	/************************************************************************************************************************************/
	
	void sendGetDataRegister(int Opcode, String pathToRegister)
	{
		try {
			c = new java.net.Socket(InetAddress.getLocalHost(),84);
			
			out = new DataOutputStream(c.getOutputStream());
			out.writeInt(Opcode);
			out.flush();
			
			out.writeUTF(pathToRegister);
			out.flush();
			
			c.close();			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/************************************************************************************************************************************/
	/**																																	*/
	/**							this function sends the return of the ChangeDataRegister function to the HMI							*/
	/**											initialization of the communication interface											*/
	/**	the identifier of the operation to be performed, the identifier of the register and the success or failure are sent to the HMI	*/
	/**																																	*/
	/************************************************************************************************************************************/
	
	
	void sendChangeDataRegister(int Opcode, int Id, Boolean check)
	{
		try {
			c = new java.net.Socket(InetAddress.getLocalHost(),84);
			
			out = new DataOutputStream(c.getOutputStream());
			out.writeInt(Opcode);
			out.flush();
			
			out.writeInt(Id);
			out.flush();
			
			out.writeInt((check) ? 1 : 0);
			out.flush();
			
			c.close();			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/************************************************************************************************************************************/
	/**																																	*/
	/**	this function gets the data sent from the HMI and sends it to the appropriate function according to the operation identifier	*/
	/**											initialization of the communication interface											*/
	/**			according to the value of the operation identifier, the corresponding function is called with the right attributes		*/
	/**																																	*/
	/************************************************************************************************************************************/
	
	public void ApiReceiver()
	{
		try {
			s = new ServerSocket(81);
			c = s.accept();
			
			in = new DataInputStream(c.getInputStream());
			int Opcode=in.readInt();
			System.out.println(Opcode);
			
			switch(Opcode)
			{
			case 1:		String ModuleName=in.readUTF();
						System.out.println(ModuleName);
						c.close();
						s.close();
						try {
							TimeUnit.MILLISECONDS.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						String path=loadModule(ModuleName);
						sendLoadModule(Opcode,path);
						break;
			
			case 2:		c.close();
						s.close();
						try {
							TimeUnit.MILLISECONDS.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						Boolean check=simul();
						sendSimul(Opcode,check);
						break;
			
			case 3:		int Id=in.readInt();
						Helper_Controller.debugMessage4("PULLID #"+Id);
						c.close();
						s.close();
						try {
							TimeUnit.MILLISECONDS.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						Vector<Boolean> wireData=getDataItem(Id);
						sendGetDataItem(Opcode,Id,wireData);
						break;
			
			case 4:		//TODO to compleat with a string of booleans
						Id=in.readInt();
						Helper_Controller.debugMessage4("COMMITID #"+Id);
						String changes = "0.0.0.0.1.1.1.1";
						//String changes = revicedDataFromUI();

						c.close();
						s.close();
						try {
							TimeUnit.MILLISECONDS.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						check=changeDataItem(Id, changes);
						sendChangeDataItem(Opcode, check);
						break;

			case 5:		c.close();
						s.close();
						try {
							TimeUnit.MILLISECONDS.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						check=reset();
						sendReset(Opcode, check);
						break;

			case 6:		//ask for data
						Id=in.readInt();
						Helper_Controller.debugMessage4("ASKFILEDATA ID"+Id);
						c.close();
						s.close();
						try {
							TimeUnit.MILLISECONDS.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						path=getDataRegister(Id);
						sendGetDataRegister(Opcode, path);
						break;

			case 7:		//load data file
						ModuleName=in.readUTF();
						System.out.println(ModuleName);
						String AbsolutePath=in.readUTF();
						System.out.println(AbsolutePath);
						Id=in.readInt();
						Helper_Controller.debugMessage4("Load data file mondule "+ModuleName+ " path "+ AbsolutePath+" to Id "+ Id);
						c.close();
						s.close();
						try {
							TimeUnit.MILLISECONDS.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						check=changeDataRegister(ModuleName, AbsolutePath, Id);
						sendChangeDataRegister(Opcode, Id, check);
						break;
			
			default:	break;
			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
}

