package backend;

import java.util.Scanner;

import interfaces.DataInt;
import models.DataMap;
import models.GUI;
import swing.TreeWorker;

public class Parser implements Runnable {
	
	private final String delimiter = "[^A-Za-z0-9]+"; 
	private Scanner scanner;
	private  DataMap map;
	private DataInt data;
	private GUI gui;
	
	
	public Parser(DataMap map, DataInt data, GUI gui){
		this.map = map;
		this.data = data;
		this.gui=gui;
	}
	
	@Override
	public void run() {
		String area = data.getArea().toLowerCase();
		gui.addLocation(area);
		
		scanner = new Scanner(data.getData());
		scanner.useDelimiter(delimiter);
		while(scanner.hasNext()) {
			String token = scanner.next().toLowerCase();
			gui.Update("Parsing :" + token );
			
			map.update(area, token);
		}
		(new TreeWorker(gui,map,area)).execute();
		scanner.close();
	}
}
