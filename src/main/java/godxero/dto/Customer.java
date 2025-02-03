package godxero.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
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
