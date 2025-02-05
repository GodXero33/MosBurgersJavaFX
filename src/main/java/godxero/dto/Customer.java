package godxero.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Customer {
	private Integer id;
	private String name;
	private String phone;
	private String email;
	private String address;
}
