package godxero.entity;

import godxero.dto.FoodItemCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class FoodItemEntity {
	private Integer id;
	private String name;
	private String code;
	private Double price;
	private Double discount;
	private Integer quantity;
	private FoodItemCategory category;
}
