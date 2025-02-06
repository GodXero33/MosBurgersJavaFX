package godxero.controller;

import com.google.inject.Guice;
import com.google.inject.Injector;
import godxero.Main;
import godxero.util.AppModule;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FormControlManager {
	private FormControlManager () {}

	public static final Injector injector = Guice.createInjector(new AppModule());

	public static FXMLLoader createForm (Stage stage, String url, Class<?> cls) throws IOException {
		final FXMLLoader loader = new FXMLLoader(Main.class.getResource(String.format("../view/%s.fxml", url)));

		loader.setController(FormControlManager.injector.getInstance(cls));
		stage.setScene(new Scene(loader.load()));

		return loader;
	}
}
