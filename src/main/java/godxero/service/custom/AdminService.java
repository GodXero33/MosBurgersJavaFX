package godxero.service.custom;

import godxero.dto.Admin;
import godxero.service.SuperService;

public interface AdminService extends SuperService {
	Admin getAdmin(String adminName);
	int isAdminNameAvailable(String adminName);
	int isEmailAvailable(String email);
	boolean addAdmin(Admin admin);
}
