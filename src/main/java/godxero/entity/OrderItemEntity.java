package godxero.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
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
