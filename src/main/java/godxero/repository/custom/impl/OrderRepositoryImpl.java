package godxero.repository.custom.impl;

import godxero.entity.OrderEntity;
import godxero.repository.custom.OrderRepository;

import java.util.List;

public class OrderRepositoryImpl implements OrderRepository {
	private static OrderRepositoryImpl instance;

	private OrderRepositoryImpl () {}

	public static OrderRepositoryImpl getInstance () {
		if (OrderRepositoryImpl.instance == null) OrderRepositoryImpl.instance = new OrderRepositoryImpl();

		return OrderRepositoryImpl.instance;
	}

	@Override
	public boolean save (OrderEntity entity) {
		return false;
	}

	@Override
	public OrderEntity search (Integer id) {
		return null;
	}

	@Override
	public boolean delete (Integer id) {
		return false;
	}

	@Override
	public boolean update (OrderEntity entity) {
		return false;
	}

	@Override
	public List<OrderEntity> getAll () {
		return List.of();
	}
}
