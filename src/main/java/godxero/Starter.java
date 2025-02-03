package godxero;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Starter extends Application {
	public static void main (String[] args) {
		Application.launch();
	}

	@Override
	public void start (Stage stage) throws IOException {
		stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("../view/login_view.fxml"))));
		stage.setResizable(false);
		stage.setTitle("Login");
		stage.show();
	}
}
