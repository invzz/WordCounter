package swing;

import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import implementations.WcMessage;

public class Connector extends SwingWorker<Boolean, Void> {
	
	private SwingClient gui;
	private String command;
	private Boolean result;

	public Connector(SwingClient gui, String command) {
		this.gui = gui;
		this.command = command;
	}
	

	@Override
	protected Boolean doInBackground() throws Exception {
		switch(command){
		case "signIn": 
			return gui.getClient().signIn(gui.getUserId(), gui.getNameTxt(), gui.getSurnameTxt());
		case "remove": 
			return gui.getClient().removeUser();
		case "logIn":  
			return gui.getClient().logIn(gui.getCurrentId());
		case "logOut": 
			return gui.getClient().logOut(gui.getCurrentId());
		case "send": 
			WcMessage message = new WcMessage(gui.textArea.getText(),gui.textData.getText());
			return gui.getClient().send(message);
		case "top":
			return gui.getClient().top(gui.getCurrentId(),gui.textArea.getText().toLowerCase());
		default: throw new AssertionError();
		}
	}
	@Override
	protected void done() {
		try {
			 result = get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			gui.log("Server non raggiungiblie...");
			e.printStackTrace();
			return;
		}
		switch(command){
		case "signIn": 
			break;
		case "remove": if(result) {gui.enabled(false);}
			break;
		case "logIn":  if(result) {gui.enabled(true);}
			break;
		case "logOut": if(result) {gui.enabled(false);}
			break;
		case "send": 
			break;
		case "top":
			break;
		default: throw new AssertionError();
		}
	}
}
