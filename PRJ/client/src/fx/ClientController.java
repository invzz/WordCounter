package fx;

import java.rmi.RemoteException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import implementations.WcMessage;
import interfaces.ClientInt;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import models.ClientFx;

public class ClientController {
	 @FXML protected TextField text_ip;
	 @FXML protected Button button_setIp;
	 @FXML protected TextField text_username;
	 @FXML protected Button button_logIn;
	 @FXML protected Button button_logOut;
	 @FXML protected Button button_signOut;
	 @FXML protected TextField text_usernameReg;
	 @FXML protected TextField text_name;
	 @FXML protected TextField text_surname;
	 @FXML protected Button button_signIn;
	 @FXML protected TextField text_city;
	 @FXML protected TextArea text_message;
	 @FXML protected Button button_send;
	 @FXML protected Button button_top3;
	 @FXML protected TextArea text_results;
	 
	 ClientFx client;
	 
	 public void setIp_onPress() {
		 try {
			 String ip=getServerIP();
			 if(ip.compareTo("")!=0) {
				 client = new ClientFx(this,ip);
				 log("Server address is set to :  "+ getServerIP());
				 button_setIp.setDisable(true);		
			 }
			 else {
				 Alert alert = new Alert(AlertType.ERROR);
                 alert.setTitle("Error");
                 alert.setHeaderText("Ip Adress Error");
                 alert.setContentText("You have to fill the field.");
                 alert.showAndWait();      
                 button_signIn.setDisable(false);				 
			 }
		} catch (IllegalArgumentException e) {
			Alert alert = new Alert(AlertType.ERROR);      
            alert.setTitle("Error");
            alert.setHeaderText("Server Connection Error");
            alert.setContentText("No answer from the provided IP. Change it.");
            alert.showAndWait();
            client = null;
		}
	 }
	 
	 public void signIn_onPress() {
		try {
			 String user = getUser();
             String name = getName();
             String surname = getSurname();
             if (user.compareTo("") != 0 && name.compareTo("") != 0 && surname.compareTo("") != 0) {
            	client.signIn(user, name, surname);
     		 	button_signIn.setDisable(true);
             }            	
             else {
            	 Alert alert = new Alert(AlertType.ERROR);
                 alert.setTitle("Error");
                 alert.setHeaderText("Registration Error");
                 alert.setContentText("You have to fill all the fields.");
                 alert.showAndWait();
             }
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	 }
	 
	 public void logIn_onPress() {
		 try {
			 String user=getCurrentId();
			 if (user.compareTo("")!=0) {
				if(client.logIn(user)) {
					button_logIn.setDisable(true);
					button_signOut.setDisable(false);
					button_top3.setDisable(false);
					button_logOut.setDisable(false);
				}
			 }
			 else {
				 Alert alert = new Alert(AlertType.ERROR);
	             alert.setTitle("Error");
	             alert.setHeaderText("Login Error");
	             alert.setContentText("You have to insert a valid ID.");
	             alert.showAndWait();
			 }
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
	 public void logOut_onPress() {
		 try {
			 if(client.logOut(getCurrentId())) {
				 button_logOut.setDisable(true);
				 button_signOut.setDisable(true);
				 button_logIn.setDisable(false);
			 }
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
	 public void signOut_onPress() {
		 try {
			 if(client.removeUser()) {
				 button_signOut.setDisable(true);
				 button_signIn.setDisable(false);
			 }
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
	 public void send_onPress() {
		 try {
			 boolean result=client.send(getData());
			 if(!result) {
				 Alert alert = new Alert(AlertType.ERROR);
				 alert.setTitle("Error");
				 alert.setHeaderText("Message Error");
				 alert.setContentText("Retry.");
				 alert.showAndWait();
			 }
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
	 public void top3_onPress() {
		 try {
			 client.top(getCurrentId(), getZone());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }	 
	 
	 public void resetButtons() {
		 button_setIp.setDisable(true);
		 button_logIn.setDisable(false);
		 button_logOut.setDisable(true);
		 button_signIn.setDisable(false);
		 button_signOut.setDisable(true);
	 }	 
	 
	 public void close() {
		 try {
			 client.logOut(getCurrentId());
		} catch (RemoteException e) {
		}
	 }
	 
	//logging
	public void log(String s) {
		this.text_results.appendText(s+"\n");
	}
	
	public String getServerIP() {
		return this.text_ip.getText();
	}
	
	public String getUser() {
		return this.text_usernameReg.getText();
	}
	
	public String getName() {
		return this.text_name.getText();
	}
	
	public String getSurname() {
		return this.text_surname.getText();
	}
	
	public String getCurrentId() {
		return this.text_username.getText();
	}
	
	public String getZone() {
		return this.text_city.getText();
	}
	
	@SuppressWarnings("exports")
	public WcMessage getData() {
		return new WcMessage(text_city.getText(),text_message.getText());
	}
	
	@SuppressWarnings("exports")
	public ClientInt getClient() {
		return client;
	}
}
