package interfaces;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInt extends Remote,Serializable {
	
	public void notifyC(String message) throws RemoteException;
	
	public boolean logIn(String id) throws RemoteException ;
	
	public boolean logOut(String id) throws RemoteException;
	
	public boolean signIn(String id, String name, String surname) throws RemoteException;
	
	public boolean removeUser() throws RemoteException;
	
	//invia un oggetto avente interfaccia DataInt
	public boolean send(DataInt data) throws RemoteException;
	
	//metodo remoto
	public boolean top(String id, String area) throws RemoteException;
}