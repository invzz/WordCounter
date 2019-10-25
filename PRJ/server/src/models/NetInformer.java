package models;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

//aggiorna la gui con informazioni relative al supporto di rete in uso (IPv4)

public class NetInformer implements Runnable {
	
	private String ip;
	private GUI gui;
	
	public NetInformer(GUI gui) {
		this.gui = gui;
	}
	
	@Override
	public void run() {
		try {
	        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
	        while (interfaces.hasMoreElements()) {
	            NetworkInterface iface = interfaces.nextElement();
	            // filters out 127.0.0.1 and inactive interfaces
	            if (iface.isLoopback() || !iface.isUp() )
	                continue;
	
	            Enumeration<InetAddress> addresses = iface.getInetAddresses();
	            while(addresses.hasMoreElements()) {
	                InetAddress addr = addresses.nextElement();
	                if(!(addr instanceof Inet4Address))
	                	continue;
	                ip = addr.getHostAddress();
	                gui.Update("Interface :" + iface.getDisplayName() + "\nAddress :" + ip);
	            }
	        }
	    } catch (SocketException e) {
	        throw new RuntimeException(e);
	    }
	}

}
