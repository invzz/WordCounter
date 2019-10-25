package models;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import android.AndroidServer;
import backend.Calculator;
import backend.CallCalculator;
import backend.Parser;
import implementations.User;
import interfaces.ClientInt;
import interfaces.DataInt;
import interfaces.ServerInt;


public class Server  implements ServerInt {

	private static final long serialVersionUID = 1L;

	// set policy location...
	private static final String seclocation = "file:security.policy";
	private static final String codelocation = "file:./";

	//TODO thread pool per le richieste
	private ThreadPoolExecutor tpool;

	//stub dei client rmi
	HashMap<String,ClientInt> stubUsers;  

	//sessioni
	ConcurrentHashMap<String,Session> sessionMap;  

	Random random;

	//protected: 

	//Gui
	protected GUI gui;

	//data
	DataMap dataMap;

	private AndroidServer aserver;

	// Public:

	//Costruttore swing
	public Server(GUI ui) {

		dataMap=new DataMap();
		gui = ui;
		stubUsers = new HashMap<String,ClientInt>();   
		sessionMap = new ConcurrentHashMap<String,Session>();

		try {

			// set rmi properties and sec policy
			System.setProperty("java.security.policy",seclocation);  //+ seclocation);
			System.setProperty("java.rmi.server.codebase",codelocation); //" + codelocation);
			if(System.getSecurityManager() == null) 
				System.setSecurityManager(new SecurityManager());
			//indirizzo locale
			System.setProperty("java.rmi.server.hostname","localhost");
			//rmi
			Registry r = null;
			try {
				r = LocateRegistry.createRegistry(8080);
			} 
			//Oops...??
			catch (RemoteException e) {
				r = LocateRegistry.getRegistry(8080);
			}
			ServerInt stubRequest = (ServerInt) UnicastRemoteObject.exportObject(this,0);
			r.rebind("REG", stubRequest); 
			//ready

			gui.Update("Server Listening on port 8080");
			new NetInformer(gui).run();
			//Thread Pool Executor
			tpool = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);

			//android server
			aserver = new AndroidServer(5005,this);
			(new Thread(){
				public void run(){
					aserver.runWebServer();
				}
			}).start();
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
	}

	//SignIn:
	@Override
	public boolean signIn(User user,ClientInt stub) throws RemoteException {
		String id = user.getId();	
		Session session = new Session(user);
		
		synchronized(session){
		if(sessionMap.putIfAbsent(id, session) == null) {
			
			if(stub != null) {
				gui.Update("User "+id+" added" );
				stub.notifyC(user.getId() + " Added to Server");
			}
			gui.addUsr(user);
			return true;
			}
		if (stub!=null)
		stub.notifyC( session.user.getId() + " has already Signed In");
		}
		
		gui.Update("Utente "+ id +" non inserito");
		
		return false;
	}

	//LogIn:
	@Override
	public boolean logIn(String id, ClientInt stub) throws RemoteException {
		Session userSession = searchSession(id);
		
		if (userSession == null) {
			
			if(stub!=null) {
				stub.notifyC("user not registered");
				return false;
			}
			
		}
	
		synchronized (userSession){ 			                        
			if (userSession.status == true) { 
				doAction(userSession.user.getId(), " has already loggedIn, nothing to do");
				if(stub!=null)
					stub.notifyC( userSession.user.getId() + " has already logged In");
				return false;
			}
			userSession.status = true;
		}	

		 synchronized (stubUsers) {
			 if (stub != null) stub.notifyC( " Welcome " + id + "!" );
			doAction(id, " has logged In");
			stubUsers.put(id, stub);
		}
		return true;		
	}

	//LogOut:
	@Override
	public boolean logOut(String id, ClientInt stub) throws RemoteException {
		Session userSession = searchSession(id);
		if (userSession == null) {
			doAction(id,"No Active sessions");
			return false;
		}
		synchronized(userSession){ 
			if (userSession.status == false) return false;
			userSession.status = false;
		}
		synchronized (stubUsers){ 
			stubUsers.remove(id);
		}
		synchronized(userSession) {
			if (stub!=null) stub.notifyC( userSession.user.getId() + " Logged Out");
			doAction(userSession.user.getId(), " Logged Out");
		}
		return true;
	}

	//Remove user:
	@Override
	public boolean removeUser(String id, ClientInt stub) throws RemoteException {
		Session userSession = searchSession(id);
		
		if (userSession == null) return false;
		if (userSession.status == false) return false; 
		
		boolean result = (sessionMap.remove(id) != null);
		
		if (result){
			synchronized (stubUsers){ 
				doAction(userSession.user.getId(), " Removed");
				gui.rmvUsr(id);
				if(stub!=null)
					stub.notifyC("User removed");
			}
			return true;
		}
		
		doAction(userSession.user.getId(), "Nothing to do");
		if(stub!=null)
		stub.notifyC("Nothing to do");
		return false ;
	}

	//Receive Data
	@Override
	public boolean recv(String id, DataInt data) throws RemoteException {
		if( searchSession(id) != null) {

			//creating parser object (implements Runnable)
			Parser parser = new Parser(dataMap,data,gui);

			//logging on Gui

			doAction(id,"@ sending");		

			//enqueuing new parser thread in tpool		
			tpool.execute(parser);

			//message has been received
			return true;
		}

		//no active session
		return false;
	}

	//Restituisce la top 3 per area 
	@Override
	public synchronized boolean top(String id, String area) throws RemoteException {
		if( searchSession(id) != null) {	
			ClientInt stub = stubUsers.get(id);
			Calculator calcThread = new Calculator(area, stub, dataMap);

			String action =  "requested top 3" ;
			doAction(id,":" + action);	
			if(stub!=null) {
				//executing new calc thread 		
				tpool.execute(calcThread);
				return true;
		
			}
			
		}
		return false;
	}

	public synchronized String top(String id, String area,Integer opt) {
		if( searchSession(id) != null) {	
			CallCalculator calcThread = new CallCalculator(area,dataMap);
			Future<String> res = tpool.submit(calcThread);
			try {
				return res.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	//private:

	// Get Session id
	private Session searchSession(String id) {
		return sessionMap.get(id);
	} 


	public GUI getGUI() {
		return this.gui;
	} 	


	@SuppressWarnings("unused")
	private synchronized void printSession() {
		for (String key : sessionMap.keySet()) {
			gui.Update("\nUtente: " + key);
		}
	}

	// Close All Sessions (non synchronized: chiamata all'interno della exit  synchronized)
	private void closeAllSessionsOpened() {
		for (String key : sessionMap.keySet()) {
			Session currentSession = sessionMap.get(key);

			// Blocco Syncronized per evitare race conditions
			synchronized (currentSession){ 
				if (currentSession.status == true) {

					currentSession.status = false;
					ClientInt stub = stubUsers.get(key);
					
					try {
						if( stub != null) stub.notifyC("Server Closed");
					} 

					catch (RemoteException e) {
						e.printStackTrace();
					}
					stubUsers.remove(key);
				}
			}
		}
	}


	public DataMap getData() {
		return this.dataMap;
	}

	// Do Action - Action Logger
	public synchronized boolean doAction(String id,String action)  {
		Session userSession = searchSession(id);
		if (userSession == null) return false;
		gui.Update(id+": "+action);
		return true;
	}


	// exit - chiude le sessioni aperte ed il server
	public synchronized void exit(Registry r, Server s) {
		try {
			closeAllSessionsOpened();
			r.unbind("REG");
			UnicastRemoteObject.unexportObject(s,true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}



}   