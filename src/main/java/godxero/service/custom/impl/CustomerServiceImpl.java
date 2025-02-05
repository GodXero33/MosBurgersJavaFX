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
	private static CustomerServiceImpl instance;

	@Inject
	private CustomerRepository customerRepository;

	private CustomerServiceImpl () {}

	public static CustomerServiceImpl getInstance () {
		if (CustomerServiceImpl.instance == null) CustomerServiceImpl.instance = new CustomerServiceImpl();
		return CustomerServiceImpl.instance;
	}

	@Override
	public List<Customer> searchCustomerWithAny (Integer id, String name, String phone, String email) {
		final List<CustomerEntity> customerEntities = this.customerRepository.searchCustomerWithAny(id, name, phone, email);
		final List<Customer> customers = new ArrayList<>();
		final ModelMapper mapper = new ModelMapper();

		customerEntities.forEach(customerEntity -> customers.add(mapper.map(customerEntity, Customer.class)));

		return customers;
	}

	@Override
	public boolean addCustomer (Customer customer) {
		return this.customerRepository.save(null);
	}
}
