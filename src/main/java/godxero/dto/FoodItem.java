package godxero.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class FoodItem {
	private Integer id;
	private String name;
	private String code;
	private Double price;
	private Double discount;
	private Integer quantity;
	private FoodItemCategory category;
}
