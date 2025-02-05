package godxero.repository.custom;

import godxero.entity.OrderEntity;
import godxero.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity, Integer> {
	int placeOrder (OrderEntity entity);
}
