package godxero.control.admin;

import godxero.db.DBConnection;
import godxero.model.Admin;
import godxero.model.AdminRole;
import javafx.scene.control.Alert;

import java.sql.*;

public class AdminController implements AdminService {
	private static AdminController instance;

	private AdminController () {}

	public static AdminController getInstance () {
		if (AdminController.instance == null) AdminController.instance = new AdminController();
		return AdminController.instance;
	}

	@Override
	public Admin getAdmin (String adminName) {
		try {
			final Connection connection = DBConnection.getInstance().getConnection();

			try (final PreparedStatement statement = connection.prepareStatement("SELECT * FROM admin WHERE name = ?")) {
				statement.setString(1, adminName);

				try (final ResultSet resultSet = statement.executeQuery()) {
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
			}

			return null;
		} catch (SQLException exception) {
			new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
			return null;
		}
	}

	@Override
	public int isAdminNameAvailable (String adminName) {
		try {
			final Connection connection = DBConnection.getInstance().getConnection();

			try (final PreparedStatement statement = connection.prepareStatement("SELECT admin_id FROM admin WHERE name = ?")) {
				statement.setString(1, adminName);

				try (final ResultSet resultSet = statement.executeQuery()) {
					if (resultSet.next()) return 1;
				}
			}

			return 0;
		} catch (SQLException exception) {
			new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
			return -1;
		}
	}

	@Override
	public int isEmailAvailable (String email) {
		try {
			final Connection connection = DBConnection.getInstance().getConnection();

			try (final PreparedStatement statement = connection.prepareStatement("SELECT admin_id FROM admin WHERE email = ?")) {
				statement.setString(1, email);

				try (final ResultSet resultSet = statement.executeQuery()) {
					if (resultSet.next()) return 1;
				}
			}

			return 0;
		} catch (SQLException exception) {
			new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
			return -1;
		}
	}

	@Override
	public boolean addAdmin (Admin admin) {
		try {
			final Connection connection = DBConnection.getInstance().getConnection();

			try (final PreparedStatement statement = connection.prepareStatement("INSERT INTO admin (name, email, dob, password) VALUES (?, ?, ?, ?)")) {
				statement.setString(1, admin.getName());
				statement.setString(2, admin.getEmail());
				statement.setString(3, admin.getDob());
				statement.setString(4, admin.getPassword());

				return statement.executeUpdate() == 1;
			}
		} catch (SQLException exception) {
			new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
		}

		return false;
	}
}
