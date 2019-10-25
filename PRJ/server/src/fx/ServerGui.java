package fx;
import javax.swing.tree.DefaultMutableTreeNode;

import implementations.User;
import interfaces.ServerInt;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.GUI;
import models.Server;

public class ServerGui extends Application implements GUI {
	
	//self referencing...
	protected ServerGui guiReference = this;
	
	//server interface
	protected ServerInt server;
	
	//handler to access FXML ids
	protected Controller controller;
    
	@Override
    public void start(Stage PrimaryStage) throws Exception {
		
		//getting loader from FXML
		FXMLLoader loader = new FXMLLoader(getClass().getResource("serverfx.fxml"));
		
		//Loading the loader
		Parent root = loader.load();
		
		//getting a controller for Update method
		controller = loader.getController();
    	
		//strarting stage
		PrimaryStage.setTitle("Word Counter Server GUI");
    	PrimaryStage.setScene(new Scene(root));
        PrimaryStage.show();
        
        //starting the server
        server = new Server(guiReference);
    }
    
	//Laucher
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    //Logging
	public void Update(String string) {
		controller.Update(string);
	}

	@Override
	public void addUsr(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rmvUsr(String user) {
		// TODO Auto-generated method stub
		
	}

	

	@SuppressWarnings("exports")
	@Override
	public DefaultMutableTreeNode findNode(DefaultMutableTreeNode root, String search) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addLocation(String area) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addWord(String token, String area,Integer occ) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearTop(String area) {
		// TODO Auto-generated method stub
		
	}
}

