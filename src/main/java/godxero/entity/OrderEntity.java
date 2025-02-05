package godxero.entity;

import godxero.dto.OrderItem;
import lombok.*;
import org.modelmapper.ModelMapper;

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

	public void setOrderItems (List<OrderItem> orderItems) {
		this.orderItems = new ArrayList<>();
		final ModelMapper mapper = new ModelMapper();

		orderItems.forEach(orderItem -> this.orderItems.add(mapper.map(orderItem, OrderItemEntity.class)));
	}
}
