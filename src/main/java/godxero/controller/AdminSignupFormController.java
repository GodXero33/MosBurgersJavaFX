package godxero.controller;

import com.google.inject.Inject;
import godxero.service.custom.AdminService;
import godxero.dto.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.jasypt.util.text.BasicTextEncryptor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AdminSignupFormController {
	@FXML
	public TextField userNameTextField;
	@FXML
	public PasswordField passwordField;
	@FXML
	public PasswordField passwordConfirmField;
	@FXML
	public TextField emailTextField;
	@FXML
	public DatePicker dobPicker;

	@Inject
	private AdminService adminService;

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

		final int adminNameAvailability = this.adminService.getAdminNameAvailability(userName);

		if (adminNameAvailability == 1) {
			new Alert(Alert.AlertType.WARNING, "User name has already taken.").show();
			this.userNameTextField.requestFocus();
			return false;
		}

		if (adminNameAvailability == -1) {
			new Alert(Alert.AlertType.ERROR, "Something went wrong. Please try again.").show();
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

		final int emailAvailability = this.adminService.getEmailAvailability(email);

		if (emailAvailability == 1) {
			new Alert(Alert.AlertType.WARNING, "There is already a account with given email address.").show();
			this.emailTextField.requestFocus();
			return false;
		}

		if (emailAvailability == -1) {
			new Alert(Alert.AlertType.ERROR, "Something went wrong. Please try again.").show();
			return false;
		}

		if (dob == null) {
			new Alert(Alert.AlertType.WARNING, "Invalid Birth day.").show();
			this.dobPicker.requestFocus();
			return false;
		}

		final BasicTextEncryptor encryptor = new BasicTextEncryptor();

		encryptor.setPassword("12ab@3hsbv");

		if (!this.adminService.addAdmin(new Admin(null, userName, null, email, null, null, null, dob, encryptor.encrypt(password)))) {
			new Alert(Alert.AlertType.ERROR, "Failed to add admin. Please try again").show();
			return false;
		}

		return true;
	}

	@FXML
	public void signupButtonOnAction (ActionEvent actionEvent) {
		if (this.validateNewAdminData()) {
			new Alert(Alert.AlertType.INFORMATION, "New admin added. Please login again.").showAndWait();
			((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
		}
	}
}
