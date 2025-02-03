package godxero.controller;

import godxero.service.ServiceFactory;
import godxero.service.custom.AdminService;
import godxero.dto.Admin;
import godxero.util.ServiceType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jasypt.util.text.BasicTextEncryptor;

import java.io.IOException;

public class LoginFormController {
	@FXML
	public TextField userNameTextField;
	@FXML
	public TextField passwordTextField;
	@FXML
	public PasswordField passwordPasswordField;

	private Admin loadedAdminDetail;
	private final AdminService adminService = ServiceFactory.getInstance().getServiceType(ServiceType.ADMIN);

	public void loginButtonOnAction (ActionEvent actionEvent) {
		final String userName = this.userNameTextField.getText();
		final String password = this.passwordTextField.getText();

		if (userName.isEmpty()) {
			new Alert(Alert.AlertType.WARNING, "User name is Empty.").show();
			return;
		}

		if (password.isEmpty()) {
			new Alert(Alert.AlertType.WARNING, "Password is Empty.").show();
			return;
		}

		if (this.loadedAdminDetail == null) this.loadedAdminDetail = this.adminService.getAdmin(userName);

		if (this.loadedAdminDetail == null) {
			new Alert(Alert.AlertType.WARNING, "User name is not found.").show();
			return;
		}

		final BasicTextEncryptor encryptor = new BasicTextEncryptor();
		encryptor.setPassword("12ab@3hsbv");

		if (!encryptor.decrypt(this.loadedAdminDetail.getPassword()).equals(password)) {
			new Alert(Alert.AlertType.WARNING, "Wrong password.").show();
			return;
		}

		try {
			// Open new stage. (Main application view)
			final Stage stage = new Stage();
			final FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../../view/main_window_view.fxml"));

			stage.setTitle("Mos Burgers");
			stage.setScene(new Scene(loader.load()));
			stage.setResizable(false);

			((MainWindowFormController) loader.getController()).setAdmin(this.loadedAdminDetail);

			stage.show();

			// Close this login view.
			((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
		} catch (IOException exception) {
			new Alert(Alert.AlertType.ERROR, "Failed to load application.").show();
			System.out.println(exception.getMessage());
		}
	}

	public void showPasswordCheckBoxOnAction (ActionEvent actionEvent) {
		final boolean isChecked = ((CheckBox) actionEvent.getTarget()).isSelected();

		this.passwordPasswordField.setVisible(!isChecked);
		this.passwordTextField.setVisible(isChecked);
	}

	public void passwordPasswordFieldOnKeyReleased (KeyEvent keyEvent) {
		this.passwordTextField.setText(((PasswordField) keyEvent.getTarget()).getText());
	}

	public void passwordTextFieldOnKeyReleased (KeyEvent keyEvent) {
		this.passwordPasswordField.setText(((TextField) keyEvent.getTarget()).getText());
	}

	public void signupButtonOnAction (ActionEvent actionEvent) {
		try {
			// Open new stage. (Sign up view)
			final Stage stage = new Stage();

			stage.setTitle("Sign Up");
			stage.setScene(new Scene(new FXMLLoader(this.getClass().getResource("../../view/admin_signup_view.fxml")).load()));
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
			stage.showAndWait();
		} catch (IOException exception) {
			new Alert(Alert.AlertType.ERROR, "Failed to load application.").show();
			System.out.println(exception.getMessage());
		}
	}

	public void userNameTextFieldOnKeyPressed (KeyEvent keyEvent) {
		this.loadedAdminDetail = null;
	}
}
