package godxero.repository.custom.impl;

import godxero.db.DBConnection;
import godxero.entity.CustomerEntity;
import godxero.repository.custom.CustomerRepository;
import godxero.util.CrudUtil;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepositoryImpl implements CustomerRepository {
	@Override
	public boolean save (CustomerEntity entity) {
		return false;
	}

	@Override
	public int addCustomer (CustomerEntity entity) {
		try {
			final Connection connection = DBConnection.getInstance().getConnection();
			final PreparedStatement addCustomerStatement = connection.prepareStatement("INSERT INTO customer (name, phone, email, address) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			addCustomerStatement.setString(1, entity.getName());
			addCustomerStatement.setString(2, entity.getPhone());
			addCustomerStatement.setString(3, entity.getEmail());
			addCustomerStatement.setString(4, entity.getAddress());

			final int affectedRows = addCustomerStatement.executeUpdate();

			if (affectedRows == 0) throw new SQLException("Insert new customer failed. no rows affected.");

			final ResultSet generatedKeys = addCustomerStatement.getGeneratedKeys();

			if (generatedKeys.next()) return generatedKeys.getInt(1);
		} catch (SQLException exception) {
			new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
		}

		return 0;
	}

	@Override
	public CustomerEntity search (Integer id) {
		return null;
	}

	@Override
	public boolean delete (Integer id) {
		return false;
	}

	@Override
	public boolean update (CustomerEntity entity) {
		return false;
	}

	@Override
	public List<CustomerEntity> getAll () {
		return List.of();
	}

	@Override
	public List<CustomerEntity> searchCustomerWithAny (Integer id, String name, String phone, String email) {
		final List<CustomerEntity> customers = new ArrayList<>();

		try {
			final Connection connection = DBConnection.getInstance().getConnection();
			final StringBuilder queryBuilder = new StringBuilder("SELECT * FROM customer WHERE 1 = 2");
			final List<Object> params = new ArrayList<>();

			if (id > 0) {
				queryBuilder.append(" OR customer_id = ?");
				params.add(id);
			}

			if (name != null) {
				queryBuilder.append(" OR name LIKE ?");
				params.add("%" + name + "%");
			}

			if (phone != null) {
				queryBuilder.append(" OR phone = ?");
				params.add(phone);
			}

			if (email != null) {
				queryBuilder.append(" OR email = ?");
				params.add(email);
			}

			final PreparedStatement statement = connection.prepareStatement(queryBuilder.toString());

			for (int a = 0; a < params.size(); a++) statement.setObject(a + 1, params.get(a));

			final ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) customers.add(new CustomerEntity(
				resultSet.getInt(1),
				resultSet.getString(2),
				resultSet.getString(3),
				resultSet.getString(4),
				resultSet.getString(5)
			));
		} catch (SQLException exception) {
			new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
		}

		return customers;
	}
}
