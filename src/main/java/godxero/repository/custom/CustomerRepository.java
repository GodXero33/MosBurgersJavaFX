package godxero.repository.custom;

import godxero.entity.CustomerEntity;
import godxero.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<CustomerEntity, Integer> {
	List<CustomerEntity> searchCustomerWithAny (Integer id, String name, String phone, String email);
}
