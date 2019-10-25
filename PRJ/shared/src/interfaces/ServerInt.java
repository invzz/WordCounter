package interfaces;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import implementations.User;

public interface ServerInt extends Serializable, Remote {
	public boolean signIn(User user,ClientInt stub) throws RemoteException;  //ritorna se sei registrato o no
	public boolean logIn(String id, ClientInt stub) throws RemoteException;
	public boolean logOut(String id,ClientInt stub) throws RemoteException;
	public boolean removeUser(String id,ClientInt stub) throws RemoteException;
	
	//riceve oggetto avente interfaccia DataInt
	public boolean recv(String id, DataInt data) throws RemoteException;
	
	//top 3 nella classifica delle occorrenze delle parole date
	public boolean  top(String id, String zone) throws RemoteException;


}
