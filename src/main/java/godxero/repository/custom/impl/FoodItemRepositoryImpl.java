package godxero.repository.custom.impl;

import godxero.dto.FoodItem;
import godxero.entity.FoodItemEntity;
import godxero.repository.custom.FoodItemRepository;
import godxero.util.CrudUtil;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
		final List<FoodItemEntity> foodItemEntities = new ArrayList<>();

		try {
			try (final ResultSet resultSet = CrudUtil.execute("SELECT item_id, name, code, price, discount, quantity, category FROM food_item")) {
				while (resultSet.next()) foodItemEntities.add(new FoodItemEntity(
					resultSet.getInt(1),
					resultSet.getString(2),
					resultSet.getString(3),
					resultSet.getDouble(4),
					resultSet.getDouble(5),
					resultSet.getInt(6),
					resultSet.getString(7)
				));
			}
		} catch (SQLException exception) {
			new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
		}

		return foodItemEntities;
	}
}
