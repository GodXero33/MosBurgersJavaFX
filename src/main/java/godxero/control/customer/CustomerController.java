package godxero.control.customer;

import godxero.db.DBConnection;
import godxero.model.Customer;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerController implements CustomerService {
	private static CustomerController instance;

	private CustomerController () {}

	public static CustomerController getInstance () {
		if (CustomerController.instance == null) CustomerController.instance = new CustomerController();
		return CustomerController.instance;
	}

	@Override
	public List<Customer> searchCustomerWithAny (Integer id, String name, String phone, String email) {
		final List<Customer> customers = new ArrayList<>();

		try {
			final Connection connection = DBConnection.getInstance().getConnection();
			final StringBuilder queryBuilder = new StringBuilder("SELECT * FROM customer WHERE 1 = 1");
			final List<Object> params = new ArrayList<>();

			if (id > 0) {
				queryBuilder.append(" AND customer_id = ?");
				params.add(id);
			}

			if (name != null) {
				queryBuilder.append(" AND name = ?");
				params.add(name);
			}

			if (phone != null) {
				queryBuilder.append(" AND phone = ?");
				params.add(phone);
			}

			if (email != null) {
				queryBuilder.append(" AND email = ?");
				params.add(email);
			}

			try (final PreparedStatement statement = connection.prepareStatement(queryBuilder.toString())) {
				for (int a = 0; a < params.size(); a++) statement.setObject(a + 1, params.get(a));

				try (final ResultSet resultSet = statement.executeQuery()) {
					while (resultSet.next()) customers.add(new Customer(
						resultSet.getInt(1),
						resultSet.getString(2),
						resultSet.getString(3),
						resultSet.getString(4),
						resultSet.getString(5)
					));
				}
			}
		} catch (SQLException exception) {
			new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
		}

		return customers;
	}

	@Override
	public boolean addCustomer (Customer customer) {
		try {
			final Connection connection = DBConnection.getInstance().getConnection();

			try (final PreparedStatement statement = connection.prepareStatement("INSERT INTO customer (name, phone, email, address) VALUES (?, ?, ?)")) {
				statement.setString(1, customer.getName());
				statement.setString(2, customer.getPhone());
				statement.setString(3, customer.getEmail());
				statement.setString(4, customer.getAddress());

				return statement.executeUpdate() == 1;
			}
		} catch (SQLException exception) {
			new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
		}

		return false;
	}
}
