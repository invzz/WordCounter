package models;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import implementations.User;
import interfaces.*;
import swing.SwingClient;

public class Client implements ClientInt {
	private SwingClient gui;
	private ServerInt server;
	private ClientInt clientStub;
	private static final String seclocation = "file:security.policy";
	private static final String codelocation = "file:./";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Client(SwingClient guiReference) {
		//security...
		System.setProperty("java.security.policy",seclocation);
		System.setProperty("java.rmi.server.codebase",codelocation);
		if(System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());
		//references..
		this.gui = guiReference; 
		
		try {
			gui.log("Trying to connect");
			//set server address
			System.setProperty("java.rmi.server.hostname",gui.getServerIP());
			
			//ottengo il registry dal server in ascolto sulla porta 8080
			gui.log("locating registry in " + gui.getServerIP() + ":"+"8080");
			Registry r = LocateRegistry.getRegistry(gui.getServerIP(),8080);
			
			gui.log("lookup for REG");
			
			//ottiene il riferimento remoto al server
			server = (ServerInt) r.lookup("REG");
			
			gui.log("exporting client");
			// esporta il client sulla porta 0 rendendolo pronto a ricevere invocazioni (notifiche)
			clientStub = (ClientInt) UnicastRemoteObject.exportObject(this,0);	
		} 
		
		catch (RemoteException | NotBoundException e) {
			guiReference.log(":: Client :: Not valid address / Server Unreachable");
			
			throw new IllegalArgumentException();
			//System.out.println("Server non collegato");
			//e.printStackTrace();
		}
	}	
	
	public Client(String ip) {
		//security...
		System.setProperty("java.security.policy",seclocation);
		System.setProperty("java.rmi.server.codebase",codelocation);
		if(System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());
		
		try {
			//gui.log("Trying to connect");
			//set server address
			System.setProperty("java.rmi.server.hostname", ip);
			
			//ottengo il registry dal server in ascolto sulla porta 8080
			//gui.log("locating registry in " + gui.getServerIP() + ":"+"8080");
			Registry r = LocateRegistry.getRegistry(ip,8080);
			
	
			//ottiene il riferimento remoto al server
			server = (ServerInt) r.lookup("REG");
			
			//gui.log("exporting client");
			// esporta il client sulla porta 0 rendendolo pronto a ricevere invocazioni (notifiche)
			clientStub = (ClientInt) UnicastRemoteObject.exportObject(this,0);	
		} 
		
		catch (RemoteException | NotBoundException e) {
			//guiReference.log(":: Client :: Not valid address / Server Unreachable");
			
			throw new IllegalArgumentException();
			//System.out.println("Server non collegato");
			//e.printStackTrace();
		}
	}	
	
	@Override
	public void notifyC(String message) throws RemoteException {
		
		System.out.println("#Server >> " + message);
		gui.log("#Server >> " + message);
		if(message.contains("Server Closed")) {
			System.out.println(message);
			gui.enabled(false);gui.repaint();
		}
	}

	@Override
	public boolean logIn(String id) throws RemoteException {
		return server.logIn(id, clientStub);
	}

	@Override
	public boolean logOut(String id) throws RemoteException {
		return server.logOut(id, clientStub);
	}

	@Override
	public boolean signIn(String id, String name, String surname) throws RemoteException {
		//TODO: check with a scanner to match RegEx patterns such as [A-z][0-9]+... 
		if (id.equals("") || name.equals("") || surname.equals("")) return false;
		User user = new User(id,name,surname);
		return  server.signIn(user, clientStub);
	}

	@Override
	public boolean removeUser() throws RemoteException {
		if (! server.removeUser(gui.getCurrentId(),clientStub)) return false;
		gui.setStatus(false) ;
		return true;
	}

	@Override
	public boolean send(DataInt data) throws RemoteException {
		//TODO: check with a scanner to match RegEx patterns such as [A-z][0-9]+...
		if(data.getArea().equals("") || data.getData().equals("")){		
			return false;	
		}
		return server.recv(gui.getCurrentId(), data);
	}

	@Override
	public boolean top(String id, String area) throws RemoteException {
		if(area.equals(""))
			return false;
		return server.top(id,area);	
	}

}
