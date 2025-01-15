package godxero.control;

import godxero.control.customer.CustomerController;
import godxero.model.Customer;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;

public class AddCustomerFormController {
	public TextField customerNameTextField;
	public TextField customerPhoneTextField;
	public TextField customerEmailTextField;
	public TextField customerAddressTextField;

	@Setter
	private ChangeCustomerFormController parent;

	public void customerAddButtonOnAction (ActionEvent actionEvent) {
		final String name = this.customerNameTextField.getText();
		final String phone = this.customerPhoneTextField.getText();
		final String email = this.customerEmailTextField.getText();
		final String address = this.customerAddressTextField.getText();

		if (name.isEmpty()) {
			new Alert(Alert.AlertType.WARNING, "Customer name can't be empty.").show();
			this.customerNameTextField.requestFocus();
			return;
		}

		if (phone.isEmpty()) {
			new Alert(Alert.AlertType.WARNING, "Customer phone can't be empty.").show();
			this.customerPhoneTextField.requestFocus();
			return;
		}

		if (!email.isEmpty() && !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
			new Alert(Alert.AlertType.WARNING, "Invalid Email.").show();
			this.customerEmailTextField.requestFocus();
			return;
		}

		final Customer newCustomer = new Customer(null, name, phone, email, address);

		if (CustomerController.getInstance().addCustomer(newCustomer)) {
			new Alert(Alert.AlertType.INFORMATION, "New Customer added.").showAndWait();
			this.parent.setNewCustomer(newCustomer);
			((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
		} else {
			new Alert(Alert.AlertType.ERROR, "Failed to add new customer. Try again.").show();
		}
	}
}
