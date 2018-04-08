package Controller;

import java.io.*;
import java.net.*;
import java.util.Vector;

public class API_IHM {
	public final int RECIEVER_PORT = 89;
	public final int SENDER_PORT = 90;
	public API_IHM() {}
	

	
	/********************************************************************************************************************/
	/**																													*/
	/**					s is the socket that acts as a server to get the returns from the simulator						*/
	/**				c is the client that acts as a client and sends the necessary arguments to the simulator			*/
	/**							in is the input stream through which the data can be read								*/
	/**							out is the output stream through which the data can be sent								*/
	/**																													*/
	/********************************************************************************************************************/
	
	
	 class answer
	 {
		 public String path;
		 public int check;
		 public Vector<Boolean> changes;
		 public int id;
		 
		 public answer()
		 {
			 path="";
			 check=-1;
			 changes = new Vector<Boolean>();
			 id=-1;
		 }
		 
	 }
	
	private ServerSocket s;
	private Socket c;
	private DataInputStream in;
	private DataOutputStream out;
	public answer a;
	
	
	/********************************************************************************************************************/
	/**																													*/
	/**			this function gets the returns from the simulator and saves it in other classes' attributes				*/
	/**									initialization of the communication interface									*/
	/**			according to the value of the operation identifier, the corresponding data is saved in attributes		*/
	/**																													*/
	/********************************************************************************************************************/
	
 	public void receive()
	{
		try {
			s = new ServerSocket(SENDER_PORT);
			c=s.accept();
			
			in = new DataInputStream(c.getInputStream());
			int Opcode = in.readInt();
			
			switch(Opcode)
			{
			case 1:		String path = in.readUTF();
						a.path=path;
						c.close();
						s.close();
						break;
			
			case 2:		int check = in.readInt();
						a.check=check;
						c.close();
						s.close();
						break;
			
			case 3:		check = in.readInt();
						a.check=check;
						Vector<Boolean> changes = new Vector<Boolean>();
						int buff=in.readInt();
						while(buff<2)
						{
							if(buff==0)	changes.add(false);
							if(buff==1)	changes.add(true);
							buff=in.readInt();
						}
						a.changes=changes;
						c.close();
						s.close();
						break;
			
			case 4:		check = in.readInt();
						a.check=check;
						c.close();
						s.close();
						break;
			
			case 5:		check = in.readInt();
						a.check=check;
						c.close();
						s.close();
						break;
			
			case 6:		path = in.readUTF();
						a.path=path;
						c.close();
						s.close();
						break;
			
			case 7:		check = in.readInt();
						a.check=check;
						int id = in.readInt();
						a.id=id;
						c.close();
						s.close();
						break;
			
			default:	break;
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/********************************************************************************************************************/
	/**																													*/
	/**		this function sends the necessary arguments to the simulator to load the selected module in the interface	*/
	/**									initialization of the communication interface									*/
	/**			the identifier of the operation to be performed and the module name are sent over the channel			*/
	/**																													*/
	/********************************************************************************************************************/
	
	public void loadModuleSimul(int Opcode, String ModuleName) {
		try {
			 c = new Socket(InetAddress.getLocalHost(),RECIEVER_PORT);	

			out=new DataOutputStream(c.getOutputStream());
			out.writeInt(Opcode);
			out.flush();
			
			out.writeUTF(ModuleName);
			out.flush();
			
			c.close();
			receive();
		}catch (UnknownHostException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/********************************************************************************************************************/
	/**																													*/
	/**				this function sends the necessary arguments to the simulator to launch the simulation				*/
	/**									initialization of the communication interface									*/
	/**						the identifier of the operation to be performed is sent over the channel					*/
	/**																													*/
	/********************************************************************************************************************/
	public void simulSimul(int Opcode) {
		try {
			c = new Socket(InetAddress.getLocalHost(),RECIEVER_PORT);
			
			out = new DataOutputStream(c.getOutputStream());
			out.writeInt(Opcode);
			out.flush();
			
			c.close();
			receive();
		}catch(UnknownHostException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/********************************************************************************************************************/
	/**																													*/
	/**		this function sends the necessary arguments to the simulator to get the bits of data running through a wire	*/
	/**									initialization of the communication interface									*/
	/**	the identifier of the operation to be performed and the identifier of the wire are sent over the channel		*/
	/**																													*/
	/********************************************************************************************************************/
	
	
	public void getDataItemSimul(int Opcode, int IdWire) {
		try {
			c = new Socket(InetAddress.getLocalHost(),RECIEVER_PORT);
			
			out = new DataOutputStream(c.getOutputStream());
			out.writeInt(Opcode);
			out.flush();
			
			out.writeInt(IdWire);
			out.flush();
			
			c.close();
			receive();
		}catch(UnknownHostException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/********************************************************************************************************************/
	/**																													*/
	/**this function sends the necessary arguments to the simulator to change the bits of data running through a wire	*/
	/**									initialization of the communication interface									*/
	/**					the identifier of the operation to be performed, the identifier of the wire						*/
	/**									and the new data are sent over the channel										*/
	/**																													*/
	/********************************************************************************************************************/
	
	
	public void changeDataItemSimul(int Opcode, int IdWire, Vector<Boolean> DataWire) {
		try {
			 c = new Socket(InetAddress.getLocalHost(),RECIEVER_PORT);	
			String send ="";	
				out = new DataOutputStream(c.getOutputStream());
				out.writeInt(Opcode);
				out.flush();
				
				out.writeInt(IdWire);
				out.flush();
				
			for(int i=0;i<DataWire.size();i++)
			{
				if(i!=0) send+=".";
				int temp=(DataWire.get(i)) ? 1 : 0;
				send=send+ (char) temp + ".";
			}
			out.writeUTF(send);
			out.flush();
			
			
			 c.close();
			receive();
		}catch (UnknownHostException e) {
			
			e.printStackTrace();
		}catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	/********************************************************************************************************************/
	/**																													*/
	/**			this function sends the necessary arguments to the simulator to reset the module settings				*/
	/**								initialization of the communication interface										*/
	/**					the identifier of the operation to be performed is sent over the channel						*/
	/**																													*/
	/********************************************************************************************************************/
	
	
	public void resetSimul(int Opcode){
		try {
			c = new Socket(InetAddress.getLocalHost(),RECIEVER_PORT);
			
			out = new DataOutputStream(c.getOutputStream());
			out.writeInt(Opcode);
			out.flush();
			
			c.close();
			receive();
		}catch(UnknownHostException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/********************************************************************************************************************/
	/**																		   		  	 								*/
	/**this function sends the necessary arguments to the simulator to request a file with the contents of a register	*/
	/**								initialization of the communication interface										*/
	/**the identifier of the operation to be performed and the identifier of the register are sent over the channel		*/
	/**																 			      	 								*/
	/********************************************************************************************************************/
	public  void getDataRegisterSimul(int Opcode, int IdRegister){
		try {
			c = new Socket(InetAddress.getLocalHost(),RECIEVER_PORT);
			
			out = new DataOutputStream(c.getOutputStream());
			out.writeInt(Opcode);
			out.flush();
			
			out.writeInt(IdRegister);
			out.flush();
			
			c.close();
			receive();
		}catch(UnknownHostException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/********************************************************************************************************************/
	/**																													*/
	/**			this function sends the necessary arguments to the simulator to change the content of a register		*/
	/**									initialization of the communication interface									*/
	/**						the identifier of the operation to be performed, the path to the buffer,					*/
	/**			the absolute path to the register file and the register identifier are sent over the channel			*/
	/**																													*/
	/********************************************************************************************************************/
	
	public  void changeDataRegisterSimul(int Opcode, String BufferPath, String AbsolutePath, int IdRegister)
	{
		try {
			c = new Socket(InetAddress.getLocalHost(),RECIEVER_PORT);
			
			out = new DataOutputStream(c.getOutputStream());
			out.writeInt(Opcode);
			out.flush();
			
			out.writeUTF(BufferPath);
			out.flush();
			
			out.writeUTF(AbsolutePath);
			out.flush();
			
			out.writeInt(IdRegister);
			out.flush();
			
			c.close();
			receive();
		}catch(UnknownHostException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/********************************************************************************************************************/
	/**																													*/
	/**						this function chooses what action to take according to the operation asked					*/
	/**					selection of the data to be sent according to the operation code sent in parameter				*/
	/**					the arguments to be sent to the simulator are forwarded to the appropriate function				*/
	/**																													*/
	/********************************************************************************************************************/
	
	
	public void APISender(int input, String target,int Objcode,String NameOrPath, String AbsolutePath, Vector<Boolean> NewValue) {
		a = new answer(); 
			switch(input)
			{
			case 1: if(target=="s")	loadModuleSimul(input,NameOrPath);
					break;
			
			case 2: if(target=="s")	simulSimul(input);
					break;
			
			case 3: if(target=="s")	getDataItemSimul(input,Objcode);
					break;
			
			case 4: if(target=="s")	changeDataItemSimul(input,Objcode,NewValue);
					break;
			
			case 5: if(target=="s")	resetSimul(input);
					break;
			
			case 6: if(target=="s")	getDataRegisterSimul(input,Objcode);
					break;
			
			case 7: if(target=="s")	changeDataRegisterSimul(input,NameOrPath,AbsolutePath,Objcode);
					break;
			
			default:break;
			}
	}
}

