package godxero;

import godxero.controller.FormControlManager;
import godxero.controller.LoginFormController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Starter extends Application {
	public static void main (String[] args) {
		Application.launch();
	}

	@Override
	public void start (Stage stage) throws IOException {
		FormControlManager.createForm(stage, "login_view",
			LoginFormController.class);
		stage.setResizable(false);
		stage.setTitle("Login");
		stage.show();
	}
}
