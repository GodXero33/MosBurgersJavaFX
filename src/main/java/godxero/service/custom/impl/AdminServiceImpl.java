package godxero.service.custom.impl;

import godxero.dto.Admin;
import godxero.dto.AdminRole;
import godxero.service.custom.AdminService;
import godxero.util.CrudUtil;
import javafx.scene.control.Alert;

import java.sql.*;

public class AdminServiceImpl implements AdminService {
	private static AdminServiceImpl instance;

	private AdminServiceImpl() {}

	public static AdminServiceImpl getInstance () {
		if (AdminServiceImpl.instance == null) AdminServiceImpl.instance = new AdminServiceImpl();
		return AdminServiceImpl.instance;
	}

	@Override
	public Admin getAdmin (String adminName) {
		try {
			try (final ResultSet resultSet = CrudUtil.execute("SELECT * FROM admin WHERE name = ?", adminName)) {
				if (resultSet.next()) return new Admin(
					resultSet.getInt(1),
					resultSet.getString(2),
					resultSet.getString(3),
					resultSet.getString(4),
					resultSet.getString(5),
					resultSet.getDouble(6),
					AdminRole.getRole(resultSet.getString(7)),
					resultSet.getString(8),
					resultSet.getString(9)
				);
			}
		} catch (SQLException exception) {
			new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
		}

		return null;
	}

	@Override
	public int isAdminNameAvailable (String adminName) {
		try {
			try (final ResultSet resultSet = CrudUtil.execute("SELECT admin_id FROM admin WHERE name = ?", adminName)) {
				return resultSet.next() ? 1 : 0;
			}
		} catch (SQLException exception) {
			new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
			return -1;
		}
	}

	@Override
	public int isEmailAvailable (String email) {
		try {
			try (final ResultSet resultSet = CrudUtil.execute("SELECT admin_id FROM admin WHERE email = ?", email)) {
				return resultSet.next() ? 1 : 0;
			}
		} catch (SQLException exception) {
			new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
			return -1;
		}
	}

	@Override
	public boolean addAdmin (Admin admin) {
		try {
			return (Integer) CrudUtil.execute(
				"INSERT INTO admin (name, email, dob, password) VALUES (?, ?, ?, ?)",
				admin.getName(),
				admin.getEmail(),
				admin.getDob(),
				admin.getPassword()
			) == 1;
		} catch (SQLException exception) {
			new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
		}

		return false;
	}
}
