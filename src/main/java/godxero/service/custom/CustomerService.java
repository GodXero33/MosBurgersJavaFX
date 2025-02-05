package godxero.service.custom;

import godxero.dto.Customer;
import godxero.service.SuperService;

import java.util.List;

public interface CustomerService extends SuperService {
	List<Customer> searchCustomerWithAny (Integer id, String name, String phone, String email);
	int addCustomer (Customer customer);
}
