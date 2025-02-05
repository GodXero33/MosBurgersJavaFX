package godxero.service.custom.impl;

import jakarta.inject.Inject;
import godxero.dto.Order;
import godxero.entity.OrderEntity;
import godxero.repository.custom.OrderRepository;
import godxero.service.custom.OrderService;
import org.modelmapper.ModelMapper;

public class OrderServiceImpl implements OrderService {
	@Inject
	private OrderRepository orderRepository;

	@Override
	public boolean placeOrder (Order order) {
		final OrderEntity orderEntity = new ModelMapper().map(order, OrderEntity.class);

		return this.orderRepository.save(orderEntity);
	}
}
