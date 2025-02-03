package godxero.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@NoArgsConstructor
@Setter
@ToString
public class OrderItemEntity {
	private FoodItemEntity foodItem;
	private	String name;
	private Double price;
	private Double discount;
	private Integer quantity;
	private Double total;
}
