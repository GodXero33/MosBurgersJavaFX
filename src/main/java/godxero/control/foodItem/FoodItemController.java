package godxero.control.foodItem;

import godxero.db.DBConnection;
import godxero.model.FoodItem;
import godxero.model.FoodItemCategory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FoodItemController implements FoodItemService {
	private static FoodItemController instance;

	private FoodItemController () {}

	public static FoodItemController getInstance () {
		if (FoodItemController.instance == null) FoodItemController.instance = new FoodItemController();
		return FoodItemController.instance;
	}

	@Override
	public List<FoodItem> getAllFoodItems () {
		final List<FoodItem> foodItems = new ArrayList<>();

		try {
			final Connection connection = DBConnection.getInstance().getConnection();

			try (final Statement statement = connection.createStatement()) {

				try (final ResultSet resultSet = statement.executeQuery("SELECT * FROM food_item")) {
					while (resultSet.next()) foodItems.add(new FoodItem(
						resultSet.getInt(1),
						resultSet.getString(2),
						resultSet.getString(3),
						resultSet.getDouble(4),
						resultSet.getDouble(5),
						FoodItemCategory.getCategory(resultSet.getString(6))
					));
				}
			}
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}

		return foodItems;
	}
}
