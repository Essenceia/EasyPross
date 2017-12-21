import java.io.*;
import java.net.*;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class Client 
{
	public static ServerSocket s;//server socket
	public static java.net.Socket c;//client socket
	public static DataInputStream in;//socket input
	public static DataOutputStream out;//socket output

	
	static String loadModule(String ModuleName)
	{
		return "C:OR";
	}
	
	static Boolean simul()
	{
		return true;
	}
	
	static Vector<Boolean> getDataItem(int Id)
	{
		Vector<Boolean> test = new Vector<Boolean>();
		test.add(true);
		test.add(false);
		return test;
	}
	
	static Boolean changeDataItem(int Id, Vector<Boolean> changes)
	{
		return true;
	}
	
	static Boolean reset()
	{
		return true;
	}
	
	static String getDataRegister(int Id)
	{
		return "C:register";
	}
	
	static Boolean changeDataRegister(String ModuleName, String AbsolutePath, int Id)
	{
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	/************************************************************************************************************************************/
	/**																																	*/
	/**								this function sends the return of the LoadModule function to the HMI								*/
	/**											initialization of the communication interface											*/
	/**the identifier of the operation to be performed and the absolute path to the file containing the module are sent over the channel*/
	/**																																	*/
	/************************************************************************************************************************************/
	
	static void sendLoadModule(int Opcode,String pathToModule)
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
	
	
	static void sendSimul(int Opcode,Boolean check)
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
	
	
	static void sendGetDataItem(int Opcode,int Id, Vector<Boolean>wireData)
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
	
	
	static void sendChangeDataItem(int Opcode, Boolean check)
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
	
	
	static void sendReset(int Opcode, Boolean check)
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
	
	static void sendGetDataRegister(int Opcode, String pathToRegister)
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
	
	
	static void sendChangeDataRegister(int Opcode, int Id, Boolean check)
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
	
	public static void main(String[] zero)
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
						System.out.println(Id);
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
			
			case 4:		Id=in.readInt();
						System.out.println(Id);
						Vector<Boolean> changes = new Vector<Boolean>();
						int buff=in.readInt();
						System.out.println(buff);
						while(buff<2)
						{
							if(buff==0)	changes.add(false);
							if(buff==1)	changes.add(true);
							buff=in.readInt();
							System.out.println(buff);
						}
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

			case 6:		Id=in.readInt();
						System.out.println(Id);
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

			case 7:		ModuleName=in.readUTF();
						System.out.println(ModuleName);
						String AbsolutePath=in.readUTF();
						System.out.println(AbsolutePath);
						Id=in.readInt();
						System.out.println(Id);
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





