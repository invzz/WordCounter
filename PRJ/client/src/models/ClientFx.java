package models;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.application.Platform;
import fx.ClientController;
import interfaces.ServerInt;
import implementations.User;
import interfaces.ClientInt;
import interfaces.DataInt;

public class ClientFx implements ClientInt {
	private static final long serialVersionUID = 1L;
	private ClientController GUI;
	private ClientInt stub;
    private ServerInt server;
    private String currentId;
    private String serverIP;
    private static final String seclocation = "file:security.policy";
	private static final String codelocation = "file:./";
	
	public ClientFx(ClientController guiReference, String IP) {
		this.GUI = guiReference;
		this.serverIP = IP;

		System.setProperty("java.security.policy",seclocation);
		System.setProperty("java.rmi.server.codebase",codelocation);
		if(System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());
		
		try {
			System.setProperty("java.rmi.server.hostname",serverIP);
			Registry r = LocateRegistry.getRegistry(serverIP,8080);
			
			//ottiene il riferimento remoto al server
			server = (ServerInt) r.lookup("REG");
			
			// esporta il client sulla porta 0 rendendolo pronto a ricevere invocazioni (notifiche)
			stub = (ClientInt) UnicastRemoteObject.exportObject(this,0);	
		} 
		
		catch (RemoteException | NotBoundException e) {
			guiReference.log(":: Client :: Not valid address / Server Unreachable");
			
			throw new IllegalArgumentException();
			//System.out.println("Server non collegato");
			//e.printStackTrace();
		}
	}
	
	public void notifyC(String message) throws RemoteException {		// notifica il client con un messaggio dal server (invocato dal server)
		Platform.runLater(new Runnable(){
			 public void run(){
				if(message.contains("TOP") || message.contains("Occurred"))
					GUI.log(message);
				else if(message.contains("Server Closed")){
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Notification");
					alert.setHeaderText("Server Closed");
					alert.setContentText(message);
					alert.showAndWait();
					GUI.resetButtons();
				}
				else
				{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Notification");
					alert.setHeaderText("New notification from the server");
					alert.setContentText(message);
					alert.showAndWait();
					 }
				}
		});
	}

	public boolean logIn(String id) throws RemoteException {
		boolean result = server.logIn(id, stub);		// chiama la login sul server
		if (!result) return false;
		currentId = id;		// se il server risponde in modo positivo aggiorna l'utente attualmente loggato
		return true;
	}

	public boolean logOut(String id) throws RemoteException {
		if (!server.logOut(currentId, stub)) return false;		// chiama la logout sul server
		return true;
	}

	public boolean signIn(String id, String name, String surname) throws RemoteException {
		User u = new User(id,name,surname);			// crea un nuovo user con dati validi
		return server.signIn(u, stub);		// chiama la signin sul server
	}

	public boolean removeUser() throws RemoteException {		// logoff con eliminazione della sessione
		if (!server.removeUser(GUI.getCurrentId(), stub)) return false;		// chiama la removeuser sul server
		return true;
	}
	
	@Override
	public boolean send(DataInt data) throws RemoteException {
		//TODO: check with a scanner to match RegEx patterns such as [A-z][0-9]+...
		if(data.getArea().equals("") || data.getData().equals("")){		
			return false;	
		}
		return server.recv(GUI.getCurrentId(), data);
	}
	
	public boolean top(String id, String zone) throws RemoteException{
		if(zone.equals(""))
			return false;
		return server.top(id,zone);		// invia al server la zona per cui è richiesta la top3
	}
}
