package core.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
	private String employeeID;
	private String username;
	private String password;
	private String passwordEncoded;
	
}
