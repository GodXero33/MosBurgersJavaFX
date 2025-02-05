package godxero.service.custom;

import godxero.dto.FoodItem;
import godxero.service.SuperService;

import java.util.List;

public interface FoodItemService extends SuperService {
	List<FoodItem> getAll ();
}
