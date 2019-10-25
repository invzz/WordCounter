package fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

import interfaces.ClientInt;

public class ClientFxGui extends Application {
	
	//self referencing
	protected ClientFxGui guiReference = this;
	
	//client interface
	protected ClientInt client;
	
	//handler to access FXML
	protected ClientController controller;
	
	private Stage primaryStage;
	private AnchorPane rootLayout;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			this.primaryStage=primaryStage;
			this.primaryStage.setTitle("Word counter Client GUI");
			primaryStage.setOnCloseRequest(event -> {
			    controller.close();
			    System.exit(0);
			});
			//getting loader from FXML
			FXMLLoader loader = new FXMLLoader(getClass().getResource("clientfx.fxml"));
			
			//loading the loader
			rootLayout = (AnchorPane) loader.load();
			
			controller=loader.getController();
			
			//starting stage
			this.primaryStage.setScene(new Scene(rootLayout));
			this.primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Stage getPrimaryStage() {
        return primaryStage;
    }
	
	//Launcher
	public static void main(String[] args) {
		launch(args);
	}	
	
}
