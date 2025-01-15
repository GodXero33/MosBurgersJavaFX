package godxero.control.admin;

import godxero.model.Admin;

public interface AdminService {
	Admin getAdmin(String adminName);
	int isAdminNameAvailable(String adminName);
	int isEmailAvailable(String email);
	boolean addAdmin(Admin admin);
}
