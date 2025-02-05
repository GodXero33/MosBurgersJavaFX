package godxero.repository.custom.impl;

import godxero.db.DBConnection;
import godxero.entity.CustomerEntity;
import godxero.repository.custom.CustomerRepository;
import godxero.util.CrudUtil;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepositoryImpl implements CustomerRepository {
	private static CustomerRepositoryImpl instance;

	private CustomerRepositoryImpl () {}

	public static CustomerRepositoryImpl getInstance () {
		if (CustomerRepositoryImpl.instance == null) CustomerRepositoryImpl.instance = new CustomerRepositoryImpl();

		return CustomerRepositoryImpl.instance;
	}


	@Override
	public boolean save (CustomerEntity entity) {
		try {
			return (Integer) CrudUtil.execute(
				"INSERT INTO customer (name, phone, email, address) VALUES (?, ?, ?, ?)",
				entity.getName(),
				entity.getPhone(),
				entity.getEmail(),
				entity.getAddress()
			) == 1;
		} catch (SQLException exception) {
			new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
		}

		return false;
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

			try (final PreparedStatement statement = connection.prepareStatement(queryBuilder.toString())) {
				for (int a = 0; a < params.size(); a++) statement.setObject(a + 1, params.get(a));

				try (final ResultSet resultSet = statement.executeQuery()) {
					while (resultSet.next()) customers.add(new CustomerEntity(
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
}
