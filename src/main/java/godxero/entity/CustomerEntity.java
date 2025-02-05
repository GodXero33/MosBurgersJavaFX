package godxero.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CustomerEntity {
	private Integer id;
	private String name;
	private String phone;
	private String email;
	private String address;
}
