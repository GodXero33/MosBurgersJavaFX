package godxero.service.custom.impl;

import godxero.db.DBConnection;
import godxero.dto.Order;
import godxero.dto.OrderItem;
import godxero.entity.OrderEntity;
import godxero.repository.RepositoryFactory;
import godxero.repository.custom.OrderRepository;
import godxero.service.custom.OrderService;
import godxero.util.RepositoryType;
import javafx.scene.control.Alert;
import org.modelmapper.ModelMapper;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrderServiceImpl implements OrderService {
	private static OrderServiceImpl instance;

	private final OrderRepository orderRepository = RepositoryFactory.getInstance().getRepositoryType(RepositoryType.ORDER);

	private OrderServiceImpl () {}

	public static OrderServiceImpl getInstance () {
		if (OrderServiceImpl.instance == null) OrderServiceImpl.instance = new OrderServiceImpl();
		return OrderServiceImpl.instance;
	}

	@Override
	public int placeOrder (Order order) {
		final OrderEntity orderEntity = new ModelMapper().map(order, OrderEntity.class);

		return this.orderRepository.placeOrder(orderEntity);
	}
}
