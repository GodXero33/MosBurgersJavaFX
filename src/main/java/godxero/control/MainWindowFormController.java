package godxero.control;

import godxero.control.foodItem.FoodItemController;
import godxero.control.order.OrderController;
import godxero.model.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindowFormController implements Initializable {
	@FXML
	public TableView<FoodItem> placeOrderItemsListTable;
	@FXML
	public TableColumn<FoodItem, String> placeOrderItemsListItemsColumn;
	@FXML
	public Label placeOrderItemNameLabel;
	@FXML
	public Label placeOrderItemPriceLabel;
	@FXML
	public Label placeOrderItemDiscountLabel;
	@FXML
	public Label placeOrderItemCategoryLabel;
	@FXML
	public TextField placeOrderItemQuantityTextField;
	@FXML
	public TextField placeOrderSearchItemTextField;
	@FXML
	public Label placeOrderTotalAmountLabel;
	@FXML
	public Label placeOrderCustomerLabel;
	@FXML
	public Label mainDateLabel;
	@FXML
	public Label mainTimeLabel;
	@FXML
	public Label adminNameLabel;
	@FXML
	public Label placeOrderCartTotalLabel;
	@FXML
	public TableView<OrderItem> placeOrderCartTable;
	@FXML
	public TableColumn<OrderItem, String> placeOrderCartTableItemColumn;
	@FXML
	public TableColumn<OrderItem, String> placeOrderCartTablePriceColumn;
	@FXML
	public TableColumn<OrderItem, String> placeOrderCartTableDiscountColumn;
	@FXML
	public TableColumn<OrderItem, String> placeOrderCartTableQuantityColumn;
	@FXML
	public TableColumn<OrderItem, String> placeOrderCartTableTotalColumn;

	private List<FoodItem> placeOrderFoodItemsList;
	private FoodItem placeOrderSelectedFoodItem;
	private Order currentOrder;
	private int adminID;
	private int customerID;

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		this.currentOrder = new Order(); // Make an empty order
		this.adminID = -1;
		this.customerID = -1;

		this.setupPlaceOrderItemsListTable();
		this.setupPlaceOrderCartTable();
		this.startDateAndTimeTimer();
	}

	private void setupPlaceOrderItemsListTable () {
		this.placeOrderItemsListItemsColumn.setCellValueFactory(new PropertyValueFactory<>("name")); // Assign name field of the 'FoodItem' class to the 'Food Item' column in the items table.
		this.updatePlaceOrderItemsTable();

		// Add click event to rows in items table to update item details section.
		this.placeOrderItemsListTable.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldFoodItem, newFoodItem) -> {
			if (newFoodItem != null) this.updateFoodItemDetails(newFoodItem);
		}));
	}

	private void setupPlaceOrderCartTable () {
		this.placeOrderCartTableItemColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		this.placeOrderCartTablePriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		this.placeOrderCartTableDiscountColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));
		this.placeOrderCartTableQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		this.placeOrderCartTableTotalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));

		this.placeOrderCartTable.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldOrderItem, newOrderItem) -> {
			if (newOrderItem != null) this.updateOrderItemDetails(newOrderItem);
		}));
	}

	private void startDateAndTimeTimer () {
		final Timeline timeline = new Timeline(
			new KeyFrame(Duration.ZERO, e -> {
				final LocalTime now = LocalTime.now();
				final LocalDate today = LocalDate.now();

				this.mainDateLabel.setText(today.getYear() + "/" + today.getMonthValue() + "/" + today.getDayOfMonth());
				this.mainTimeLabel.setText(now.getHour() + " : " + now.getMinute() + " : " + now.getSecond());
			}),
			new KeyFrame(Duration.seconds(1))
		);

		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}

	public void setAdmin (Admin admin) {
		this.adminID = admin.getId();

		this.currentOrder.setAdminID(this.adminID);
		this.adminNameLabel.setText("Admin - " + admin.getName());
	}

	public void setCustomer (Customer customer) {
		this.customerID = customer.getId();

		this.currentOrder.setCustomerID(this.customerID);
		this.placeOrderCustomerLabel.setText("Customer - " + customer.getName());
	}

	private double calculateTotalAmount () {
		if (this.placeOrderSelectedFoodItem == null) return 0.0;

		final String quantityStr = this.placeOrderItemQuantityTextField.getText();

		if (!quantityStr.matches("^\\d+$")) return 0.0;

		return Integer.parseInt(quantityStr) * this.placeOrderSelectedFoodItem.getPrice();
	}

	private void updateFoodItemDetails (FoodItem foodItem) {
		this.placeOrderItemNameLabel.setText(foodItem.getName());
		this.placeOrderItemPriceLabel.setText(foodItem.getPrice() + " Rs");
		this.placeOrderItemDiscountLabel.setText(foodItem.getDiscount() + " Rs");
		this.placeOrderItemCategoryLabel.setText(foodItem.getCategory().toString());
		this.placeOrderTotalAmountLabel.setText(String.format("%.2f Rs", this.calculateTotalAmount())); // Update total amount text field after updating the item details section.

		this.placeOrderSelectedFoodItem = foodItem;
	}

	private void updateOrderItemDetails (OrderItem orderItem) {
		final FoodItem foodItem = orderItem.getFoodItem();
		this.placeOrderSelectedFoodItem = foodItem;

		this.placeOrderItemNameLabel.setText(foodItem.getName());
		this.placeOrderItemPriceLabel.setText(foodItem.getPrice() + " Rs");
		this.placeOrderItemDiscountLabel.setText(foodItem.getDiscount() + " Rs");
		this.placeOrderItemCategoryLabel.setText(foodItem.getCategory().toString());
		this.placeOrderItemQuantityTextField.setText(orderItem.getQuantity().toString());
		this.placeOrderTotalAmountLabel.setText(String.format("%.2f Rs", this.calculateTotalAmount())); // Update total amount text field after updating the item details section.
	}

	private void updatePlaceOrderItemsTable (List<FoodItem> foodItemsList) {
		this.placeOrderItemsListTable.setItems(FXCollections.observableArrayList(foodItemsList));
	}

	private void updatePlaceOrderItemsTable () {
		this.placeOrderFoodItemsList = FoodItemController.getInstance().getAllFoodItems();
		this.updatePlaceOrderItemsTable(this.placeOrderFoodItemsList);
	}

	private void updatePlaceOrderItemsTable (String filterName) {
		final List<FoodItem> filteredFoodItems = new ArrayList<>();

		for (final FoodItem foodItem : this.placeOrderFoodItemsList) if (foodItem.getName().toLowerCase().contains(filterName.toLowerCase().trim())) filteredFoodItems.add(foodItem); // Filter all matching food items from the 'foodItems' field.

		this.updatePlaceOrderItemsTable(filteredFoodItems);
	}

	private void updatePlaceOrderCartTable () {
		this.placeOrderCartTable.setItems(FXCollections.observableArrayList(this.currentOrder.getOrderItems()));
	}

	private void clearPlaceOrderCurrentSelectedItem () {
		this.placeOrderSelectedFoodItem = null;
		final String zeroPrice = "0.0 Rs";

		this.placeOrderItemNameLabel.setText("");
		this.placeOrderItemPriceLabel.setText(zeroPrice);
		this.placeOrderItemDiscountLabel.setText(zeroPrice);
		this.placeOrderItemCategoryLabel.setText("");
		this.placeOrderItemQuantityTextField.setText("");
		this.placeOrderTotalAmountLabel.setText(zeroPrice);
		this.placeOrderItemsListTable.getSelectionModel().clearSelection();
		this.placeOrderCartTable.getSelectionModel().clearSelection();
		this.placeOrderCartTotalLabel.setText("Total - " + (this.currentOrder == null || this.currentOrder.getFinalAmount() == null || this.currentOrder.isEmpty() ? "0.0" : this.currentOrder.getFinalAmount()) + " Rs");
	}

	@FXML
	public void placeOrderSearchItemTextFieldOnKeyReleased (KeyEvent keyEvent) {
		// Filter food items and update the food items table while inserting text.
		this.updatePlaceOrderItemsTable(this.placeOrderSearchItemTextField.getText());
	}

	@FXML
	public void placeOrderClearSearchButtonOnAction (ActionEvent actionEvent) {
		if (this.placeOrderSearchItemTextField.getText().isEmpty()) return;

		// Reset search results and view full list in the table.
		this.placeOrderSearchItemTextField.clear();
		this.updatePlaceOrderItemsTable(this.placeOrderFoodItemsList);
	}

	@FXML
	public void placeOrderRefreshItemsButtonOnAction (ActionEvent actionEvent) {
		// Retrieve food items from the database again and update the items table. If there is any text already in the search text field, then update table with filtered food items.
		this.placeOrderFoodItemsList = FoodItemController.getInstance().getAllFoodItems();
		final String filterName = this.placeOrderSearchItemTextField.getText();

		if (filterName.isEmpty()) {
			this.updatePlaceOrderItemsTable();
		} else {
			this.updatePlaceOrderItemsTable(filterName);
		}
	}

	@FXML
	public void placeOrderItemAddButtonOnAction (ActionEvent actionEvent) {
		if (this.placeOrderSelectedFoodItem == null) {
			new Alert(Alert.AlertType.WARNING, "No food item has selected").show();
			return;
		}

		final String quantityStr = this.placeOrderItemQuantityTextField.getText();

		if (!quantityStr.matches("^\\d+$") || Integer.parseInt(quantityStr) == 0) {
			new Alert(Alert.AlertType.WARNING, "Invalid Quantity.").show();
			return;
		}

		final List<OrderItem> orderItems = this.currentOrder.getOrderItems();
		final int len = orderItems.size();

		for (int a = 0; a < len; a++) {
			final OrderItem orderItem = orderItems.get(a);

			if (orderItem.getFoodItem() == this.placeOrderSelectedFoodItem) {
				orderItem.setQuantity(Integer.parseInt(quantityStr));
				this.placeOrderCartTable.getItems().set(a, orderItem);
				this.clearPlaceOrderCurrentSelectedItem();
				return;
			}
		}

		final OrderItem orderItem = new OrderItem();

		orderItem.setFoodItem(this.placeOrderSelectedFoodItem);
		orderItem.setQuantity(Integer.parseInt(quantityStr));
		this.currentOrder.addOrderItem(orderItem);
		this.updatePlaceOrderCartTable();
		this.clearPlaceOrderCurrentSelectedItem();
	}

	@FXML
	public void placeOrderItemQuantityTextFieldOnKeyReleased (KeyEvent keyEvent) {
		this.placeOrderTotalAmountLabel.setText(String.format("%.2f Rs", this.calculateTotalAmount()));
	}

	@FXML
	public void placeOrderCustomerChangeButtonOnAction (ActionEvent actionEvent) {
		try {
			final Stage stage = new Stage();
			final FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../../view/change_customer_view.fxml"));

			stage.setTitle("Change Customer");
			stage.setScene(new Scene(loader.load()));
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());

			((ChangeCustomerFormController) loader.getController()).setParent(this);

			stage.showAndWait();
		} catch (IOException exception) {
			new Alert(Alert.AlertType.ERROR, "Failed to load application.").show();
			System.out.println(exception.getMessage());
		}
	}

	@FXML
	public void placeOrderItemRemoveButtonOnAction (ActionEvent actionEvent) {
		final OrderItem selectedOrderItem = this.placeOrderCartTable.getSelectionModel().getSelectedItem();

		if (selectedOrderItem == null) {
			new Alert(Alert.AlertType.WARNING, "No item selected from the cart.").show();
			return;
		}

		this.currentOrder.getOrderItems().remove(selectedOrderItem);
		this.updatePlaceOrderCartTable();
		this.clearPlaceOrderCurrentSelectedItem();
	}

	@FXML
	public void placeOrderOrderPlaceButtonOnAction (ActionEvent actionEvent) {
		if (this.customerID == -1) {
			new Alert(Alert.AlertType.WARNING, "Please select a customer first.").show();
			return;
		}

		if (this.currentOrder.isEmpty()) {
			new Alert(Alert.AlertType.WARNING, "There is nothing in cart to place an order.").show();
			return;
		}

		if (OrderController.getInstance().placeOrder(this.currentOrder) == 1) {
			new Alert(Alert.AlertType.INFORMATION, "Order placed.").show();
			this.currentOrder = new Order();
			this.customerID = -1;

			this.placeOrderCustomerLabel.setText("");
			this.currentOrder.setAdminID(this.adminID);
			this.updatePlaceOrderCartTable();
			this.clearPlaceOrderCurrentSelectedItem();
		} else {
			new Alert(Alert.AlertType.ERROR, "Failed to place order. Try Again.").show();
		}
	}
}
