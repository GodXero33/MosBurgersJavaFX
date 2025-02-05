package godxero.service.custom.impl;

import com.google.inject.Inject;
import godxero.dto.Customer;
import godxero.entity.CustomerEntity;
import godxero.repository.custom.CustomerRepository;
import godxero.service.custom.CustomerService;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {
	@Inject
	private CustomerRepository customerRepository;

	@Override
	public List<Customer> searchCustomerWithAny (Integer id, String name, String phone, String email) {
		final List<CustomerEntity> customerEntities = this.customerRepository.searchCustomerWithAny(id, name, phone, email);
		final List<Customer> customers = new ArrayList<>();
		final ModelMapper mapper = new ModelMapper();

		customerEntities.forEach(customerEntity -> customers.add(mapper.map(customerEntity, Customer.class)));

		return customers;
	}

	@Override
	public int addCustomer (Customer customer) {
		return this.customerRepository.addCustomer(new ModelMapper().map(customer, CustomerEntity.class));
	}
}
