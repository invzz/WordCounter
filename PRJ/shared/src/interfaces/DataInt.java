package interfaces;
import java.io.Serializable;

//interfaccia wrapper per i dati che il client passa al server

public interface DataInt extends Serializable{
	//testo da analizzare
	public String getData();
	
	//area 
	public String getArea();
}
