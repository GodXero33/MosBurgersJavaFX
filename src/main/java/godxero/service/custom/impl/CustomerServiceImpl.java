package godxero.service.custom.impl;

import godxero.db.DBConnection;
import godxero.dto.Customer;
import godxero.repository.RepositoryFactory;
import godxero.repository.custom.CustomerRepository;
import godxero.service.custom.CustomerService;
import godxero.util.RepositoryType;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {
	private static CustomerServiceImpl instance;

	private CustomerRepository customerRepository = RepositoryFactory.getInstance().getRepositoryType(RepositoryType.CUSTOMER);

	private CustomerServiceImpl() {}

	public static CustomerServiceImpl getInstance () {
		if (CustomerServiceImpl.instance == null) CustomerServiceImpl.instance = new CustomerServiceImpl();
		return CustomerServiceImpl.instance;
	}

	@Override
	public List<Customer> searchCustomerWithAny (Integer id, String name, String phone, String email) {
		final List<Customer> customers = new ArrayList<>();

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
		return this.customerRepository.save(null);
	}
}
