package godxero.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@ToString
public class Order {
	private Integer id;
	private Double totalAmount;
	private Double discount;
	private Double finalAmount;
	private Integer customerID;
	private Integer adminID;
	private List<OrderItem> orderItems;

	public void update () {
		this.totalAmount = 0.0;
		this.discount = 0.0;
		this.finalAmount = 0.0;

		for (final OrderItem orderItem : this.orderItems) {
			final double tmpDiscount = orderItem.getDiscount() * orderItem.getQuantity();
			this.totalAmount += orderItem.getTotal();
			this.discount += tmpDiscount;
			this.finalAmount += orderItem.getTotal() - tmpDiscount;
		}
	}

	public void addOrderItem (OrderItem orderItem) {
		if (this.orderItems == null) this.orderItems = new ArrayList<>();

		this.orderItems.add(orderItem);
		this.update();
	}

	public List<OrderItem> getOrderItems () {
		if (this.orderItems == null) this.orderItems = new ArrayList<>();

		return this.orderItems;
	}

	public boolean isEmpty () {
		return this.orderItems == null || this.orderItems.isEmpty();
	}
}
