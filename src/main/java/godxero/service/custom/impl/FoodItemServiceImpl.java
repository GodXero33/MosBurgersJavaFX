package godxero.service.custom.impl;

import godxero.dto.FoodItem;
import godxero.service.custom.FoodItemService;
import godxero.util.CrudUtil;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FoodItemServiceImpl implements FoodItemService {
	private static FoodItemServiceImpl instance;

	private FoodItemServiceImpl() {}

	public static FoodItemServiceImpl getInstance () {
		if (FoodItemServiceImpl.instance == null) FoodItemServiceImpl.instance = new FoodItemServiceImpl();
		return FoodItemServiceImpl.instance;
	}

	@Override
	public List<FoodItem> getAllFoodItems () {
		final List<FoodItem> foodItems = new ArrayList<>();

		try {
			try (final ResultSet resultSet = CrudUtil.execute("SELECT item_id, name, code, price, discount, quantity, category FROM food_item")) {
				while (resultSet.next()) foodItems.add(new FoodItem(
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

		return foodItems;
	}
}
