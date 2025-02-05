package godxero.controller;

import com.google.inject.Guice;
import com.google.inject.Injector;
import godxero.util.AppModule;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class FormControlManager {
	private FormControlManager () {}

	public static final Injector injector = Guice.createInjector(new AppModule());

	public static FXMLLoader createForm (Stage stage, URL url, Class<?> cls) throws IOException {
		final FXMLLoader loader = new FXMLLoader(url);

		loader.setController(FormControlManager.injector.getInstance(cls));
		stage.setScene(new Scene(loader.load()));

		return loader;
	}
}
