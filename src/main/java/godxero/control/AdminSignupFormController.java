package godxero.control;

import godxero.control.admin.AdminController;
import godxero.model.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.jasypt.util.text.BasicTextEncryptor;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AdminSignupFormController {
	public TextField userNameTextField;
	public PasswordField passwordField;
	public PasswordField passwordConfirmField;
	public TextField emailTextField;
	public DatePicker dobPicker;

	private boolean validateNewAdminData () {
		final String userName = this.userNameTextField.getText();
		final String password = this.passwordField.getText();
		final String confirmPassword = this.passwordConfirmField.getText();
		final String email = this.emailTextField.getText();
		final LocalDate dobValue = this.dobPicker.getValue();
		final String dob = dobValue == null ? null : dobValue.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		if (userName.isEmpty()) {
			new Alert(Alert.AlertType.WARNING, "User name can't be empty.").show();
			this.userNameTextField.requestFocus();
			return false;
		}

		if (AdminController.getInstance().isAdminNameAvailable(userName) == 1) {
			new Alert(Alert.AlertType.WARNING, "User name has already taken.").show();
			this.userNameTextField.requestFocus();
			return false;
		}

		if (password.isEmpty()) {
			new Alert(Alert.AlertType.WARNING, "Password can't be empty.").show();
			this.passwordField.requestFocus();
			return false;
		}

		if (!confirmPassword.equals(password)) {
			new Alert(Alert.AlertType.WARNING, "Confirm password must match with password.").show();
			this.passwordConfirmField.requestFocus();
			return false;
		}

		if (email.isEmpty()) {
			new Alert(Alert.AlertType.WARNING, "Email can't be empty.").show();
			this.emailTextField.requestFocus();
			return false;
		}

		if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
			new Alert(Alert.AlertType.WARNING, "Invalid email.").show();
			this.emailTextField.requestFocus();
			return false;
		}

		if (AdminController.getInstance().isEmailAvailable(email) == 1) {
			new Alert(Alert.AlertType.WARNING, "There is already a account with given email address.").show();
			this.emailTextField.requestFocus();
			return false;
		}

		if (dob == null) {
			new Alert(Alert.AlertType.WARNING, "Invalid Birth day.").show();
			this.dobPicker.requestFocus();
			return false;
		}

		final BasicTextEncryptor encryptor = new BasicTextEncryptor();
		encryptor.setPassword("12ab@3hsbv");

		if (!AdminController.getInstance().addAdmin(new Admin(null, userName, null, email, null, null, null, dob, encryptor.encrypt(password)))) {
			new Alert(Alert.AlertType.ERROR, "Failed to add admin. Please try again").show();
			return false;
		}

		return true;
	}

	private void openLoginView (Stage signUpStage) {
		try {
			// Open new stage. (Login view)
			final Stage stage = new Stage();

			stage.setTitle("Login");
			stage.setScene(new Scene(new FXMLLoader(this.getClass().getResource("../../view/login_view.fxml")).load()));
			stage.show();

			// Close this login view.
			signUpStage.close();
		} catch (IOException exception) {
			new Alert(Alert.AlertType.ERROR, "Failed to load application.").show();
			System.out.println(exception.getMessage());
		}
	}

	public void signupButtonOnAction (ActionEvent actionEvent) {
		if (this.validateNewAdminData()) {
			new Alert(Alert.AlertType.INFORMATION, "New admin added. Please login again.").showAndWait();
			this.openLoginView((Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
		}
	}
}
