package godxero.repository.custom.impl;

import godxero.dto.Admin;
import godxero.entity.AdminEntity;
import godxero.repository.custom.AdminRepository;

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

	@Override
	public AdminEntity search (Integer id) {
		return null;
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
