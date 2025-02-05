package godxero.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
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
	private String category;
}
