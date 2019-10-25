package backend;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

import models.DataMap;

public class CallCalculator implements Callable<String> {
	
	private DataMap map;
	private String area;
	
	public CallCalculator(String area, DataMap map) {
		
		this.map = map;
		this.area = area;
	}
	
	@Override
	public String call() throws Exception {
		
		
		 
		String[] resp = new String[] {"","",""}; 
		if (map.get(area)==null) {
			System.out.println("No data");
		    return "no data";
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
					resp[i]= element.getKey() + ", Occurred:" + element.getValue();
					i++;
				} catch (Exception e) {
					e.printStackTrace();
				} 
					
        	}
			else break;
		} 
		String top = resp[0] + "-" +resp[1]+ "-" +resp[2];
		System.out.println(top);
		return top;  
	}
}



