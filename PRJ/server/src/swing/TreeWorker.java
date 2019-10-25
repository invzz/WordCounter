package swing;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.SwingWorker;

import models.DataMap;
import models.GUI;


//aggiorna la treeview con le top3 per ogni area
public class TreeWorker extends SwingWorker<Boolean, String>{

	private DataMap map;
	private GUI gui;
	private String area;
	public TreeWorker(GUI gui, DataMap map, String area){
		
		this.map=map;
		this.gui = gui;
		this.area=area;
		
	}
		
	@Override
	protected Boolean doInBackground() throws Exception {
		//listing
		List<Map.Entry<String, Integer> > list = 
				new LinkedList<Map.Entry<String, Integer>> (map.get(area).entrySet()); 
		
		//sorting
		Collections.sort(list, new Comparator<Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});
		
		//top3
		Integer i =0;
		for (Map.Entry<String, Integer> element : list) { 
			if (i<3) {
				try {
					synchronized(i){
					gui.addWord(element.getKey(), area,element.getValue());
					i++;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
        	}
			else break;
		}
		return true;
	}

}
