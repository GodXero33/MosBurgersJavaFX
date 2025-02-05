package godxero.repository.custom.impl;

import godxero.entity.CustomerEntity;
import godxero.repository.custom.CustomerRepository;
import godxero.util.CrudUtil;
import javafx.scene.control.Alert;

import java.sql.SQLException;
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
}
