package godxero;

import com.google.inject.Guice;
import com.google.inject.Injector;
import godxero.controller.LoginFormController;
import godxero.util.AppModule;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Starter extends Application {
	public static final Injector injector = Guice.createInjector(new AppModule());

	public static void main (String[] args) {
		Application.launch();
	}

	@Override
	public void start (Stage stage) throws IOException {
		final FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/login_view.fxml"));

		loader.setController(Starter.injector.getInstance(LoginFormController.class));
		stage.setScene(new Scene(loader.load()));
		stage.setResizable(false);
		stage.setTitle("Login");
		stage.show();
	}
}
