package core.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRole {
	private String employeeID;
	private String httpMethod;
	private String urlAPI;
}
