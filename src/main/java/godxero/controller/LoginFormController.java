package godxero.controller;

import com.google.inject.Inject;
import godxero.service.custom.AdminService;
import godxero.dto.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
	@Inject
	private AdminService adminService;

	@FXML
	public void loginButtonOnAction (ActionEvent actionEvent) {
		final String userName = this.userNameTextField.getText();
		final String password = this.passwordTextField.getText();

		if (userName.isEmpty()) {
			new Alert(Alert.AlertType.WARNING, "User name is Empty.").show();
			this.userNameTextField.requestFocus();
			return;
		}

		if (password.isEmpty()) {
			new Alert(Alert.AlertType.WARNING, "Password is Empty.").show();
			(this.passwordPasswordField.isVisible() ? this.passwordPasswordField : this.passwordTextField).requestFocus();
			return;
		}

		if (this.loadedAdminDetail == null) this.loadedAdminDetail = this.adminService.search(userName);

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
			final FXMLLoader loader = FormControlManager.createForm(stage, "main_window_view", MainWindowFormController.class);

			stage.setTitle("Mos Burgers");
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

	@FXML
	public void showPasswordCheckBoxOnAction (ActionEvent actionEvent) {
		final boolean isChecked = ((CheckBox) actionEvent.getTarget()).isSelected();

		this.passwordPasswordField.setVisible(!isChecked);
		this.passwordTextField.setVisible(isChecked);
	}

	@FXML
	public void passwordPasswordFieldOnKeyReleased (KeyEvent keyEvent) {
		this.passwordTextField.setText(((PasswordField) keyEvent.getTarget()).getText());
	}

	@FXML
	public void passwordTextFieldOnKeyReleased (KeyEvent keyEvent) {
		this.passwordPasswordField.setText(((TextField) keyEvent.getTarget()).getText());
	}

	@FXML
	public void signupButtonOnAction (ActionEvent actionEvent) {
		try {
			// Open new stage. (Sign up view)
			final Stage stage = new Stage();

			FormControlManager.createForm(stage, "admin_signup_view", AdminSignupFormController.class);
			stage.setTitle("Sign Up");
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
			stage.showAndWait();
		} catch (IOException exception) {
			new Alert(Alert.AlertType.ERROR, "Failed to load application.").show();
			System.out.println(exception.getMessage());
		}
	}

	@FXML
	public void userNameTextFieldOnKeyPressed (KeyEvent keyEvent) {
		this.loadedAdminDetail = null;
	}
}
