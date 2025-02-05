package godxero.service.custom.impl;

import godxero.dto.Admin;
import godxero.entity.AdminEntity;
import godxero.repository.RepositoryFactory;
import godxero.repository.custom.AdminRepository;
import godxero.service.custom.AdminService;
import godxero.util.RepositoryType;
import org.modelmapper.ModelMapper;

public class AdminServiceImpl implements AdminService {
	private static AdminServiceImpl instance;

	private final AdminRepository adminRepository = RepositoryFactory.getInstance().getRepositoryType(RepositoryType.ADMIN);

	private AdminServiceImpl () {}

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
	public int getAdminNameAvailability (String adminName) {
		return this.adminRepository.getAdminNameAvailability(adminName);
	}

	@Override
	public int getEmailAvailability (String email) {
		return this.adminRepository.getEmailAvailability(email);
	}

	@Override
	public boolean addAdmin (Admin admin) {
		return this.adminRepository.save(new ModelMapper().map(admin, AdminEntity.class));
	}
}
