package android;

//test class per client android
public class ClientThread implements Runnable {
	private String id;
	private String name; 
	private String surname;
	private String area;
	private String msg;
	
	//passo parametro al runnable
	private Integer index; 
	
	ClientThread(Integer index){
		this.index=index;
		
		System.out.print("client thread :: ");
		id = "andres"+index.toString();
		name = "andres"+index.toString();
		surname = "coronado"+index.toString();
		area = "genova";
		msg = "In informatica, il problema del produttore-consumatore "
				+ "(conosciuto anche con il nome di problema del buffer limitato "
				+ "o bounded-buffer problem) è un esempio classico di "
				+ "sincronizzazione tra processi. Il problema descrive due processi, "
				+ "uno produttore (in inglese producer) ed uno consumatore (consumer),"
				+ " che condividono un buffer comune, di dimensione fissata. Compito "
				+ "del produttore è generare dati e depositarli nel buffer in continuo."
				+ " Contemporaneamente, il consumatore utilizzerà i dati prodotti, "
				+ "rimuovendoli di volta in volta dal buffer. Il problema è assicurare "
				+ "che il produttore non elabori nuovi dati se il buffer è pieno, "
				+ "e che il consumatore non cerchi dati se il buffer è vuoto";
				
	}
	
	@Override
	public void run() {
		
		AndroidClient client = new AndroidClient("127.0.0.1", 5005);
    	client.signIn(id, name, surname) ;
    	client.logIn(id);
    	client.sndmsg(id, area, msg);
    	client.asktop(id, area);
    	client.logOut();
    	client.removeUser();
    	System.out.println("index :: " + index.toString());
    	//System.out.println(client.top);
    	
	}

}
