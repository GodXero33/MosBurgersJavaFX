package godxero.service.custom.impl;

import godxero.dto.Admin;
import godxero.entity.AdminEntity;
import godxero.repository.RepositoryFactory;
import godxero.repository.custom.AdminRepository;
import godxero.service.custom.AdminService;
import godxero.util.CrudUtil;
import godxero.util.RepositoryType;
import javafx.scene.control.Alert;
import org.modelmapper.ModelMapper;

import java.sql.*;

public class AdminServiceImpl implements AdminService {
	private static AdminServiceImpl instance;

	private final AdminRepository adminRepository = RepositoryFactory.getInstance().getRepositoryType(RepositoryType.ADMIN);

	private AdminServiceImpl() {}

	public static AdminServiceImpl getInstance () {
		if (AdminServiceImpl.instance == null) AdminServiceImpl.instance = new AdminServiceImpl();
		return AdminServiceImpl.instance;
	}

	@Override
	public Admin search (String adminName) {
		final AdminEntity adminEntity = this.adminRepository.search(adminName);

		if (adminEntity == null) return null;

		return new ModelMapper().map(adminEntity, Admin.class);
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
