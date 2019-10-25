package models;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import interfaces.DataInt;

public class DataMap {
	
	private ConcurrentHashMap<String,ConcurrentHashMap<String,Integer>> map; 
	
	public DataMap() {
		map = new ConcurrentHashMap<String,ConcurrentHashMap<String,Integer>>();
	}

	public void put(DataInt data) {
		map.putIfAbsent(data.getArea(),(new ConcurrentHashMap<String,Integer>()));
	}
	
	//getter dell'hashmap di tutte le parole per area
	public ConcurrentHashMap<String,Integer> get(String key) {
		return map.get(key);
	}
	
	//getter di una occorrenza
	public int get(String area, String word) {
		if (map.get(area).containsKey(word)) {
			return map.get(area).get(word);
		}
		return 0;
	}
	
	//aggiorna occorrenza 
	public synchronized void update(String area, String word) {
		map.putIfAbsent(area,new ConcurrentHashMap<String,Integer>());	
		if (get(area,word)==0) {
			System.out.print("HashMap : new token! - ");
			map.get(area).put(word, 0);
		}
		map.get(area).put(word, (get(area,word))+1);
		System.out.print("update in " + area + " - " + word + ", occurred " +  "" + get(area,word) + "\n" );
	}
	
	//restituisce vero se in una certa area esiste una certa parola
	public boolean containsKey(String area, String word) {
		if (get(area,word)==0) { return false; }
		return true;
	}
	
	public synchronized ConcurrentHashMap<String,ConcurrentHashMap<String,Integer>> getMap(){
		return this.map;
	}
	
	public synchronized List<String> getKeys() {
		return new LinkedList<String>(map.keySet());
	}
}
