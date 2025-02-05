package godxero.repository.custom.impl;

import godxero.entity.AdminEntity;
import godxero.repository.custom.AdminRepository;
import godxero.util.CrudUtil;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AdminRepositoryImpl implements AdminRepository {
	@Override
	public boolean save (AdminEntity entity) {
		try {
			return (Integer) CrudUtil.execute(
				"INSERT INTO admin (name, email, dob, password) VALUES (?, ?, ?, ?)",
				entity.getName(),
				entity.getEmail(),
				entity.getDob(),
				entity.getPassword()
			) == 1;
		} catch (SQLException exception) {
			new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
		}

		return false;
	}

	private AdminEntity search (String fieldName, Object bindData) {
		try {
			final ResultSet resultSet = CrudUtil.execute(String.format("SELECT admin_id, name, phone, email, address, salary, position, dob, password FROM `admin` WHERE %s = ?", fieldName), bindData);

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

	private int getFieldAvailability (String fieldName, Object bindData) {
		try {
			final ResultSet resultSet = CrudUtil.execute(String.format("SELECT admin_id FROM admin WHERE %s = ?", fieldName), bindData);
			return resultSet.next() ? 1 : 0;
		} catch (SQLException exception) {
			new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
			return -1;
		}
	}

	@Override
	public int getAdminNameAvailability (String adminName) {
		return this.getFieldAvailability("name", adminName);
	}

	@Override
	public int getEmailAvailability (String email) {
		return this.getFieldAvailability("email", email);
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
