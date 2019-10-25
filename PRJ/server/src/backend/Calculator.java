package backend;

import java.rmi.RemoteException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import interfaces.ClientInt;
import models.DataMap;


//nb callable
public class Calculator implements Runnable{
	
	private ClientInt client;
	private DataMap map;
	private String area;
	
	public Calculator(String area,ClientInt client,DataMap map) {
		this.client	 = Objects.requireNonNull(client);
		this.map = Objects.requireNonNull(map,"Empty hasmap");
		this.area = Objects.requireNonNull(area);
	}
	
	
	@Override
	public void run() {
		try {
			client.notifyC("TOP 3 - Area : " + area);
			if (map.get(area)==null) {
				client.notifyC("No data");
			 return;
			}
		} catch (RemoteException e) {
			
			e.printStackTrace();
		}
		
		
		List<Map.Entry<String, Integer> > list = 
				new LinkedList<Map.Entry<String, Integer>> (map.get(area).entrySet()); 
		
		//sorting with comparator
		Collections.sort(list, new Comparator<Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});
		
		int i =0;
		// string
		for (Map.Entry<String, Integer> element : list) { 
			if (i<3) {
				try {
					client.notifyC(element.getKey() + ", Occurred:" + element.getValue() );
					i++;
				} catch (RemoteException e) {
					e.printStackTrace();
				} 
					
        	}
			else break;
		} 
	}
}

	