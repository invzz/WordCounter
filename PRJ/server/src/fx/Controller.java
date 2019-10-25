package fx;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;


public class Controller {
	
	@FXML
	private TextArea logger;
	
	@FXML
	private Button button;
	
	@FXML
	protected void handleSubmitButtonAction(ActionEvent event) {
		Update("hello world");
	}
	
	//metodo per loggare su GUI nella textArea
	public void Update(String string){
		Runnable log = new Runnable(){
			@Override
			public void run(){
				logger.appendText(string + "\n");
			}
		};
		Platform.runLater(log);
	}
}
