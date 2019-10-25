package android;

import static java.util.Objects.requireNonNull;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import implementations.User;
import implementations.WcMessage;
import models.Server;

public class Connector implements Runnable {

	
	protected Socket clientSocket ;
	protected Server serverRMI;
	
	public Connector(Socket clientSocket, Server serverRMI) {
	this.clientSocket = requireNonNull(clientSocket);
	this.serverRMI = requireNonNull(serverRMI) ;
	
	}

	@Override
	public void run() {
		try (
				PrintWriter output =  
					new PrintWriter( clientSocket.getOutputStream() , true );
				
				BufferedReader input = 
					new BufferedReader (new InputStreamReader (clientSocket.getInputStream()));
				
				Socket client = clientSocket
			)
		{
			String command = input.readLine();
			
			switch(command){
			case "signin":{
				manageSignIn(output,input);
				break;
				}
			case "remove":{
				manageRemove(output,input);
				break;
				}
			case "login" :{
				manageLogIn(output,input);
				break;
				}
			case "logout":{
				manageLogOut(output,input);
				break;
				}
			case "send":{
				manageSend(output,input);
				break;
				}
			case "top":{
				manageTop(output,input);
				break;
				
			}
			default: {
				output.println("FAIL");
			}
			
			
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
	
	
	/*
	
	//DELETE template app di layout di delzanno
	private void manageAction (String action, PrintWriter output , BufferedReader input) throws IOException{
		output.println("OK");
		String id = input.readLine();
		boolean result = serverRMI.doAction(id,action);
		if(result) output.println("OK");
		else output.println("FAIL");
	}
	*/
	
	private void manageSignIn (PrintWriter output , BufferedReader input) throws IOException{
		output.println("OK");
		
		String id = input.readLine();
		String name = input.readLine();
		String surname = input.readLine();
		User user=new User(id, name, surname);
		boolean result = serverRMI.signIn(user, null);
		
		if(result) {
			output.println("OK");
		}
		else output.println("FAIL");
		
	}
	
	private void manageRemove (PrintWriter output , BufferedReader input) throws IOException{
		output.println("OK");
		String id = input.readLine();
		boolean result = serverRMI.removeUser(id, null);
		if(result) {
			output.println("OK");
		
		}			
		else output.println("FAIL");
		
	}
	
	private void manageLogIn (PrintWriter output , BufferedReader input) throws IOException{
		output.println("OK");
		String id = input.readLine();
		boolean result = serverRMI.logIn(id, null);
		if(result) {
			//serverRMI.getGUI().Update("android user "+id+": has logged in");
			output.println("OK");
		}
		else  output.println("FAIL");
		
	}
	
	private void manageLogOut (PrintWriter output , BufferedReader input) throws IOException{
		output.println("OK");
		output.println("OK");
		String id = input.readLine();
		boolean result = serverRMI.logOut(id, null);
		if(result) {
			//serverRMI.getGUI().Update("android user:"+id+" has logged out");
			output.println("OK");
		}
		else output.println("FAIL");
		
	}
	
	//send
	private void manageSend(PrintWriter output, BufferedReader input) throws IOException{
		output.println("OK");
		String id = input.readLine();
		output.println("OK");
		String area = input.readLine();
		output.println("OK");
		String data = input.readLine();
		WcMessage msg = new WcMessage(area,data);
		boolean result = serverRMI.recv(id,msg);
		if(result)
			output.println("OK");
		else
			output.println("FAIL");
		return;
	}

	//top
	private void manageTop(PrintWriter output, BufferedReader input) throws IOException{
		
		output.println("OK");
		String id = input.readLine();
		
		output.println("OK");
		String area = input.readLine();
		
		String result = serverRMI.top(id,area,1);
		output.println(result);	
		return;
	}

	
}