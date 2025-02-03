package godxero.entity;

import godxero.dto.AdminRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class AdminEntity {
	private Integer id;
	private String name;
	private String phone;
	private String email;
	private String address;
	private Double salary;
	private AdminRole role;
	private String dob;
	private String password;
}
