package godxero.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
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
	private String role;
	private String dob;
	private String password;
}
