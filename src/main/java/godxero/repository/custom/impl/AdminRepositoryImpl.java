package godxero.repository.custom.impl;

import godxero.entity.AdminEntity;
import godxero.repository.custom.AdminRepository;
import godxero.util.CrudUtil;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AdminRepositoryImpl implements AdminRepository {
	private static AdminRepositoryImpl instance;

	private AdminRepositoryImpl () {}

	public static AdminRepositoryImpl getInstance () {
		if (AdminRepositoryImpl.instance == null) AdminRepositoryImpl.instance = new AdminRepositoryImpl();

		return AdminRepositoryImpl.instance;
	}

	@Override
	public boolean save (AdminEntity entity) {
		return false;
	}

	private AdminEntity search (String fieldName, Object data) {
		try {
			final ResultSet resultSet = CrudUtil.execute(String.format("SELECT admin_id, name, phone, email, address, salary, position, dob, password FROM `admin` WHERE %s = ?", fieldName), data);

			if (resultSet.next()) return new AdminEntity(
				resultSet.getInt(1),
				resultSet.getString(2),
				resultSet.getString(3),
				resultSet.getString(4),
				resultSet.getString(5),
				resultSet.getDouble(6),
				resultSet.getString(7),
				resultSet.getString(8),
				resultSet.getString(9)
			);
		} catch (SQLException exception) {
			new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
		}

		return null;
	}

	@Override
	public AdminEntity search (Integer id) {
		return this.search("admin_id", id);
	}

	@Override
	public AdminEntity search (String adminName) {
		return this.search("name", adminName);
	}

	@Override
	public boolean delete (Integer id) {
		return false;
	}

	@Override
	public boolean update (AdminEntity entity) {
		return false;
	}

	@Override
	public List<AdminEntity> getAll () {
		return List.of();
	}
}
