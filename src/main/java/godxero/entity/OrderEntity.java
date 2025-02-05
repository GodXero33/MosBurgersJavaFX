package godxero.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderEntity {
	private Integer id;
	private Double totalAmount;
	private Double discount;
	private Double finalAmount;
	private Integer customerID;
	private Integer adminID;
	private List<OrderItemEntity> orderItems;
}
