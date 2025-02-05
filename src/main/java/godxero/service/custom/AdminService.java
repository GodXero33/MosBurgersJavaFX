package godxero.service.custom;

import godxero.dto.Admin;
import godxero.service.SuperService;

public interface AdminService extends SuperService {
	Admin search (String adminName);
	int getAdminNameAvailability (String adminName);
	int getEmailAvailability (String email);
	boolean addAdmin (Admin admin);
}
