package core.employee;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class EmployeeRoleService {
	
	private static List<EmployeeRole> allEmployeeRole = new ArrayList<>();
	
	static
	{
		allEmployeeRole.add(new EmployeeRole("E01A", "GET", "/employee/get-all/"));
		allEmployeeRole.add(new EmployeeRole("E01A", "POST", "/employee/"));
		allEmployeeRole.add(new EmployeeRole("E01A", "DELETE", "/employee/"));
		allEmployeeRole.add(new EmployeeRole("E01A", "GET", "/employee/"));
		allEmployeeRole.add(new EmployeeRole("E01U", "GET", "/employee/get-all/"));
		allEmployeeRole.add(new EmployeeRole("E01U", "GET", "/employee/"));
	}
	
	public List<EmployeeRole> getEmployeeRole(String employeeID)
	{
		return allEmployeeRole.stream()
				.filter(employee -> employee.getEmployeeID().equals(employeeID)).collect(Collectors.toList());
	}
}
