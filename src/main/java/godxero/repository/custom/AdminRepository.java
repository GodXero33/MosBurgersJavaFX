package godxero.repository.custom;

import godxero.entity.AdminEntity;
import godxero.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<AdminEntity, Integer> {
	AdminEntity search (String adminName);
	int getAdminNameAvailability (String adminName);
	int getEmailAvailability (String email);
}
