package godxero.service.custom;

import godxero.dto.Order;
import godxero.service.SuperService;

public interface OrderService extends SuperService {
	int placeOrder(Order order);
}
