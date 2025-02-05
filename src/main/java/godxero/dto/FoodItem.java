package godxero.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
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
	private String category;
}
