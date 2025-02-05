package godxero.service.custom.impl;

import com.google.inject.Inject;
import godxero.dto.Admin;
import godxero.entity.AdminEntity;
import godxero.repository.custom.AdminRepository;
import godxero.service.custom.AdminService;
import org.modelmapper.ModelMapper;

public class AdminServiceImpl implements AdminService {
	@Inject
	private AdminRepository adminRepository;

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
