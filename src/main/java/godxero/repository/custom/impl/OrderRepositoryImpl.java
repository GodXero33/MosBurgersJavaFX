package godxero.repository.custom.impl;

import godxero.util.DBConnection;
import godxero.entity.OrderEntity;
import godxero.entity.OrderItemEntity;
import godxero.repository.custom.OrderRepository;
import javafx.scene.control.Alert;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrderRepositoryImpl implements OrderRepository {
	@Override
	public boolean save (OrderEntity entity) {
		try {
			final Connection connection = DBConnection.getInstance().getConnection();

			connection.setAutoCommit(false);

			final PreparedStatement placeOrderStatement = connection.prepareStatement("INSERT INTO mos_order (place_date, total_amount, discount, final_amount, customer_id, admin_id) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			placeOrderStatement.setString(1, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			placeOrderStatement.setDouble(2, entity.getTotalAmount());
			placeOrderStatement.setDouble(3, entity.getDiscount());
			placeOrderStatement.setDouble(4, entity.getFinalAmount());
			placeOrderStatement.setInt(5, entity.getCustomerID());
			placeOrderStatement.setInt(6, entity.getAdminID());

			final int affectedRows = placeOrderStatement.executeUpdate();

			if (affectedRows == 0) throw new SQLException("Insert new order failed. no rows affected.");

			int orderID;
			final ResultSet generatedKeys = placeOrderStatement.getGeneratedKeys();

			if (generatedKeys.next()) {
				orderID = generatedKeys.getInt(1);
			} else {
				throw new SQLException("Creating order failed, no ID obtained.");
			}

			final List<OrderItemEntity> orderItems = entity.getOrderItems();
			final PreparedStatement placeOrderItemStatement = connection.prepareStatement("INSERT INTO order_item (item_id, order_id, quantity, total_price, price_per_unit) VALUE (?, ?, ?, ?, ?)");
			final PreparedStatement updateFoodItemQuantityStatement = connection.prepareStatement("UPDATE food_item SET quantity = quantity - ? WHERE item_id = ?");

			placeOrderItemStatement.setInt(2, orderID);

			for (final OrderItemEntity orderItemEntity : orderItems) {
				placeOrderItemStatement.setInt(1, orderItemEntity.getFoodItem().getId());
				placeOrderItemStatement.setInt(3, orderItemEntity.getQuantity());
				placeOrderItemStatement.setDouble(4, orderItemEntity.getTotal());
				placeOrderItemStatement.setDouble(5, orderItemEntity.getPrice());

				updateFoodItemQuantityStatement.setInt(1, orderItemEntity.getQuantity());
				updateFoodItemQuantityStatement.setInt(2, orderItemEntity.getFoodItem().getId());

				if (placeOrderItemStatement.executeUpdate() != 1) throw new SQLException("Order Item failed to insert. Order Item: " + orderItemEntity.getFoodItem());

				if (updateFoodItemQuantityStatement.executeUpdate() != 1) throw new SQLException("Failed to update quantity of a food item. Food Item: " + orderItemEntity.getFoodItem().getId());
			}

			return true;
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

		return false;
	}

	@Override
	public OrderEntity search (Integer id) {
		return null;
	}

	@Override
	public boolean delete (Integer id) {
		return false;
	}

	@Override
	public boolean update (OrderEntity entity) {
		return false;
	}

	@Override
	public List<OrderEntity> getAll () {
		return List.of();
	}
}
