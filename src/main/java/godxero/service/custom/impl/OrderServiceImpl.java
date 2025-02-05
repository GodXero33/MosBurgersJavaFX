package godxero.service.custom.impl;

import com.google.inject.Inject;
import godxero.dto.Order;
import godxero.entity.OrderEntity;
import godxero.repository.custom.OrderRepository;
import godxero.service.custom.OrderService;
import org.modelmapper.ModelMapper;

public class OrderServiceImpl implements OrderService {
	private static OrderServiceImpl instance;

	@Inject
	private OrderRepository orderRepository;

	private OrderServiceImpl () {}

	public static OrderServiceImpl getInstance () {
		if (OrderServiceImpl.instance == null) OrderServiceImpl.instance = new OrderServiceImpl();
		return OrderServiceImpl.instance;
	}

	@Override
	public boolean placeOrder (Order order) {
		final OrderEntity orderEntity = new ModelMapper().map(order, OrderEntity.class);

		return this.orderRepository.save(orderEntity);
	}
}
