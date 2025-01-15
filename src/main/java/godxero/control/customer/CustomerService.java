package godxero.control.customer;

import godxero.model.Customer;

import java.util.List;

public interface CustomerService {
	List<Customer> searchCustomerWithAny(Integer id, String name, String phone, String email);
	boolean addCustomer(Customer customer);
}
