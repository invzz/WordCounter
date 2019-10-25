package android;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import models.Server;

public class AndroidServer {

	private volatile boolean stop;
	private  int numPort ;
	protected ServerSocket listener ;
	protected ExecutorService serverPool;
	protected Server serverRMI;
	
	public AndroidServer(int numPort , Server serverRMI){
		
		//set protected variables
		this.serverRMI = serverRMI;
		this.numPort = numPort;
	
		//executor service for worker threads
		serverPool = Executors.newFixedThreadPool(5);
		
		//listener listening on port numport
		try {
			listener = new ServerSocket(this.numPort);
			listener.setSoTimeout(5000);
		} catch (IOException e) {
				e.printStackTrace();
		}
	}
	
	//main server loop
	public void runWebServer(){
		serverRMI.getGUI().Update("AndroidServer listening on port " + numPort);
		while(!stop){
			Socket clientSocket = null;
			try {
				clientSocket = listener.accept();
				this.serverPool.execute( new Connector(clientSocket , serverRMI));
				}
			catch(SocketTimeoutException e) {  }
			catch (IOException e) { throw new RuntimeException("Errore", e);}
		}
		closeServer();
		serverRMI.getGUI().Update("AndroidServer closed");
	}
	
	public void closeServer() {
		try {
			listener.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		serverPool.shutdown(); 
		try {
			if (!serverPool.awaitTermination(60, TimeUnit.SECONDS)) {
				serverPool.shutdownNow();
				if (!serverPool.awaitTermination(60, TimeUnit.SECONDS))
					serverRMI.getGUI().Update("Warning: ThreadPool still runing");
			}
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}	
	}
	
	public void stopServer(){
		stop = true;
	}

}
