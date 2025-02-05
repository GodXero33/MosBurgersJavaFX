package godxero.service.custom;

import godxero.dto.Order;
import godxero.service.SuperService;

public interface OrderService extends SuperService {
	boolean placeOrder (Order order);
}
