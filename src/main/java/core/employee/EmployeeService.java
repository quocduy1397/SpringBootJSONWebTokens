package core.employee;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import core.custom.CryptWithMD5;

@Service
public class EmployeeService {
	
	private static List<Employee> allEmployee = new ArrayList<>();
	
	static
	{
		allEmployee.add(new Employee("E01A", "admin", "12345", CryptWithMD5.cryptWithMD5("12345")));
		allEmployee.add(new Employee("E01U", "user", "12345", CryptWithMD5.cryptWithMD5("12345")));
	}
	
	public List<Employee> getAll()
	{
		return allEmployee;
	}
	
	public Employee findByEmployeeID(String employeeID)
	{
		for (Employee employee : allEmployee)
		{
			if (employee.getEmployeeID().equals(employeeID))
			{
				return employee;
			}
		}
		
		return null;
	}
	
	public Boolean addEmployee(Employee newEmployee) 
	{
		for (Employee employee : allEmployee)
		{
			if (employee.getEmployeeID().equals(newEmployee.getEmployeeID()) 
					|| StringUtils.equals(employee.getUsername(), newEmployee.getUsername()))
			{
				return false;
			}
		}
		
		allEmployee.add(newEmployee);
		return true;
	}
	
	public void deleteEmployee(String employeeID) 
	{
		allEmployee.removeIf(employee -> employee.getEmployeeID().equals(employeeID));
	}
	
	public Employee getEmployeeByUsername(String username) 
	{
	    for (Employee employee : allEmployee) 
	    {
	    	if (employee.getUsername().equals(username)) 
	    	{
	    		return employee;
	    	}
	    }
	    return null;
	}
	
	public Boolean checkLogin(Employee inputEmployee) 
	{
	    for (Employee employee : allEmployee) 
	    {
	    	if (StringUtils.equals(inputEmployee.getUsername(), employee.getUsername())
	    			&& StringUtils.equals(inputEmployee.getPassword(), employee.getPassword())) 
	    	{
	    		return true;
	    	}
	    }
	    return false;
	}
}
