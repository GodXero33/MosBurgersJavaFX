package godxero.repository.custom.impl;

import godxero.dto.FoodItem;
import godxero.entity.FoodItemEntity;
import godxero.repository.custom.FoodItemRepository;

import java.util.List;

public class FoodItemRepositoryImpl implements FoodItemRepository {
	private static FoodItemRepositoryImpl instance;

	private FoodItemRepositoryImpl () {}

	public static FoodItemRepositoryImpl getInstance () {
		if (FoodItemRepositoryImpl.instance == null) FoodItemRepositoryImpl.instance = new FoodItemRepositoryImpl();

		return FoodItemRepositoryImpl.instance;
	}

	@Override
	public boolean save (FoodItemEntity entity) {
		return false;
	}

	@Override
	public FoodItemEntity search (Integer id) {
		return null;
	}

	@Override
	public boolean delete (Integer id) {
		return false;
	}

	@Override
	public boolean update (FoodItemEntity entity) {
		return false;
	}

	@Override
	public List<FoodItemEntity> getAll () {
		return List.of();
	}
}
