package godxero.controller;

import com.google.inject.Inject;
import godxero.service.custom.CustomerService;
import godxero.dto.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ChangeCustomerFormController implements Initializable {
	@FXML
	public TextField customerIDTextField;
	@FXML
	public TextField customerNameTextField;
	@FXML
	public TextField customerPhoneTextField;
	@FXML
	public TextField customerEmailTextField;
	@FXML
	public TableView<Customer> customersListTable;
	@FXML
	public TableColumn<Customer, String> customersListIDColumn;
	@FXML
	public TableColumn<Customer, String> customersListNameColumn;
	@FXML
	public TableColumn<Customer, String> customersListPhoneColumn;
	@FXML
	public TableColumn<Customer, String> customersListEmailColumn;
	@FXML
	public TableColumn<Customer, String> customersListAddressColumn;
	@FXML
	public Button customerSelectButton;

	@Setter
	private MainWindowFormController parent;
	@Inject
	private CustomerService customerService;

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		this.customersListIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.customersListNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		this.customersListPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
		this.customersListEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
		this.customersListAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

		this.customersListTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> this.customerSelectButton.setDisable(newValue == null));
	}

	public void customerSelectButtonOnAction (ActionEvent actionEvent) {
		final Customer selectedCustomer = this.customersListTable.getSelectionModel().getSelectedItem();

		if (selectedCustomer == null) {
			new Alert(Alert.AlertType.WARNING, "No customer has selected.").show();
			return;
		}

		this.parent.setCustomer(selectedCustomer);
		((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
	}

	public void setNewCustomer (Customer customer) {
		this.parent.setCustomer(customer);

		this.customerNameTextField.setText(customer.getName());
		this.customerPhoneTextField.setText(customer.getPhone());
		this.customerEmailTextField.setText(customer.getEmail());

		final ObservableList<Customer> observableCustomers = FXCollections.observableArrayList();
		observableCustomers.add(customer);

		this.customersListTable.setItems(observableCustomers);
		this.customerSelectButton.setDisable(false);
		this.customersListTable.getSelectionModel().select(0);
	}

	public void customerSearchButtonOnAction (ActionEvent actionEvent) {
		String idStr = this.customerIDTextField.getText();
		String name = this.customerNameTextField.getText();
		String phone = this.customerPhoneTextField.getText();
		String email = this.customerEmailTextField.getText();

		final List<Customer> customers = this.customerService.searchCustomerWithAny(
			idStr.matches("^\\d+$") ? Integer.parseInt(idStr) : -1,
			name.isEmpty() ? null : name,
			phone.isEmpty() ? null : phone,
			email.isEmpty() ? null : email
		);
		final ObservableList<Customer> observableCustomers = FXCollections.observableArrayList(customers);

		this.customersListTable.setItems(observableCustomers);
		this.customerSelectButton.setDisable(customers.isEmpty());
	}

	public void addCustomerButtonOnAction (ActionEvent actionEvent) {
		try {
			final Stage stage = new Stage();
			final FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../../view/add_customer_view.fxml"));

			stage.setTitle("Add Customer");
			stage.setScene(new Scene(loader.load()));
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());

			((AddCustomerFormController) loader.getController()).setParent(this);

			stage.showAndWait();
		} catch (IOException exception) {
			new Alert(Alert.AlertType.ERROR, "Failed to load application.").show();
			System.out.println(exception.getMessage());
		}
	}
}
