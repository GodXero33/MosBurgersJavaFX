package godxero.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@Setter
@ToString
public class OrderItem {
	private FoodItem foodItem;
	private	String name;
	private Double price;
	private Double discount;
	private Integer quantity;
	private Double total;

	public void setFoodItem (FoodItem foodItem) {
		this.foodItem = foodItem;
		this.name = this.foodItem.getName();
		this.price = this.foodItem.getPrice();
		this.discount = this.foodItem.getDiscount();
	}

	public void setQuantity (Integer quantity) {
		this.quantity = quantity;
		this.total = (this.price - this.discount) * this.quantity;
	}
}
