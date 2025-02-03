package godxero.service.custom.impl;

import godxero.db.DBConnection;
import godxero.dto.Order;
import godxero.dto.OrderItem;
import godxero.service.custom.OrderService;
import javafx.scene.control.Alert;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrderServiceImpl implements OrderService {
	private static OrderServiceImpl instance;

	private OrderServiceImpl() {}

	public static OrderServiceImpl getInstance () {
		if (OrderServiceImpl.instance == null) OrderServiceImpl.instance = new OrderServiceImpl();
		return OrderServiceImpl.instance;
	}

	@Override
	public int placeOrder (Order order) {
		try {
			final Connection connection = DBConnection.getInstance().getConnection();

			connection.setAutoCommit(false);

			try (final PreparedStatement placeOrderStatement = connection.prepareStatement("INSERT INTO mos_order (place_date, total_amount, discount, final_amount, customer_id, admin_id) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
				placeOrderStatement.setString(1, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				placeOrderStatement.setDouble(2, order.getTotalAmount());
				placeOrderStatement.setDouble(3, order.getDiscount());
				placeOrderStatement.setDouble(4, order.getFinalAmount());
				placeOrderStatement.setInt(5, order.getCustomerID());
				placeOrderStatement.setInt(6, order.getAdminID());

				final int affectedRows = placeOrderStatement.executeUpdate();

				if (affectedRows == 0) throw new SQLException("Insert new order failed. no rows affected.");

				int orderID = -1;

				try (final ResultSet generatedKeys = placeOrderStatement.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						orderID = generatedKeys.getInt(1);
					} else {
						throw new SQLException("Creating order failed, no ID obtained.");
					}
				}

				final List<OrderItem> orderItems = order.getOrderItems();

				try (
					final PreparedStatement placeOrderItemStatement = connection.prepareStatement("INSERT INTO order_item (item_id, order_id, quantity, total_price, price_per_unit) VALUE (?, ?, ?, ?, ?)");
					final PreparedStatement updateFoodItemQuantityStatement = connection.prepareStatement("UPDATE food_item SET quantity = quantity - ? WHERE item_id = ?")
					) {
					placeOrderItemStatement.setInt(2, orderID);

					for (final OrderItem orderItem : orderItems) {
						placeOrderItemStatement.setInt(1, orderItem.getFoodItem().getId());
						placeOrderItemStatement.setInt(3, orderItem.getQuantity());
						placeOrderItemStatement.setDouble(4, orderItem.getTotal());
						placeOrderItemStatement.setDouble(5, orderItem.getPrice());

						updateFoodItemQuantityStatement.setInt(1, orderItem.getQuantity());
						updateFoodItemQuantityStatement.setInt(2, orderItem.getFoodItem().getId());

						if (placeOrderItemStatement.executeUpdate() != 1) throw new SQLException("Order Item failed to insert. Order Item: " + orderItem.getFoodItem());

						if (updateFoodItemQuantityStatement.executeUpdate() != 1) throw new SQLException("Failed to update quantity of a food item. Food Item: " + orderItem.getFoodItem().getId());
					}

					return 1;
				}
			}
		} catch (SQLException exception) {
			try {
				DBConnection.getInstance().getConnection().rollback();
			} catch (SQLException rollbackException) {
				new Alert(Alert.AlertType.ERROR, rollbackException.getMessage()).show();
			}

			new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
		} finally {
			try {
				DBConnection.getInstance().getConnection().setAutoCommit(true);
			} catch (SQLException exception) {
				new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
			}
		}

		return 0;
	}
}
