package godxero.service.custom.impl;

import com.google.inject.Inject;
import godxero.dto.FoodItem;
import godxero.entity.FoodItemEntity;
import godxero.repository.custom.FoodItemRepository;
import godxero.service.custom.FoodItemService;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class FoodItemServiceImpl implements FoodItemService {
	private static FoodItemServiceImpl instance;

	@Inject
	private FoodItemRepository foodItemRepository;

	private FoodItemServiceImpl () {}

	public static FoodItemServiceImpl getInstance () {
		if (FoodItemServiceImpl.instance == null) FoodItemServiceImpl.instance = new FoodItemServiceImpl();
		return FoodItemServiceImpl.instance;
	}

	@Override
	public List<FoodItem> getAll () {
		final List<FoodItemEntity> foodItemEntities = this.foodItemRepository.getAll();
		final List<FoodItem> foodItems = new ArrayList<>();
		final ModelMapper mapper = new ModelMapper();

		foodItemEntities.forEach(foodItemEntity -> foodItems.add(mapper.map(foodItemEntity, FoodItem.class)));

		return foodItems;
	}
}
