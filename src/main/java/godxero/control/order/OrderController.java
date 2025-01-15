package godxero.control.order;

import godxero.db.DBConnection;
import godxero.model.Order;
import godxero.model.OrderItem;
import javafx.scene.control.Alert;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrderController implements OrderService {
	private static OrderController instance;

	private OrderController () {}

	public static OrderController getInstance () {
		if (OrderController.instance == null) OrderController.instance = new OrderController();
		return OrderController.instance;
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

				try (final PreparedStatement placeOrderItemStatement = connection.prepareStatement("INSERT INTO order_item (item_id, order_id, quantity, total_price, price_per_unit) VALUE (?, ?, ?, ?, ?)")) {
					placeOrderItemStatement.setInt(2, orderID);

					for (final OrderItem orderItem : orderItems) {
						placeOrderItemStatement.setInt(1, orderItem.getFoodItem().getId());
						placeOrderItemStatement.setInt(3, orderItem.getQuantity());
						placeOrderItemStatement.setDouble(4, orderItem.getTotal());
						placeOrderItemStatement.setDouble(5, orderItem.getPrice());

						if (placeOrderItemStatement.executeUpdate() != 1) throw new SQLException("Order Item failed to insert. Order Item: " + orderItem.getFoodItem());
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
