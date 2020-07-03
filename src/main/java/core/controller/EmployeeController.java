package core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import core.employee.Employee;
import core.employee.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping(value = "/get-all/")
	public ResponseEntity<Object> getAllEmployee()
	{
		return new ResponseEntity<> (employeeService.getAll(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/")
	public ResponseEntity<Object> getEmployeeByEmployeeID(@RequestBody String employeeID) 
	{
	    Employee employee = employeeService.findByEmployeeID(employeeID);
	    if (employee != null) 
	    {
	    	return new ResponseEntity<>(employee, HttpStatus.OK);
	    }
	    
	    return new ResponseEntity<>("Not Found Employee!", HttpStatus.NO_CONTENT);
	}
	
	@PostMapping(value = "/")
	public ResponseEntity<Object> createUser(@RequestBody Employee employee) {
		if (employeeService.addEmployee(employee)) 
	    {
	    	return new ResponseEntity<>("Employee have been added!", HttpStatus.CREATED);
	    } 
	    else 
	    {
	    	return new ResponseEntity<>("Employee already existed!", HttpStatus.BAD_REQUEST);
	    }
	}
	
	@DeleteMapping(value = "/")
	public ResponseEntity<Object> deleteEmployee(@RequestBody String employeeID)
	{
		String results = employeeService.findByEmployeeID(employeeID) == null 
				? "Employee did not exist!" : "Deleted!";
		employeeService.deleteEmployee(employeeID);
		return new ResponseEntity<>(results, HttpStatus.OK);
	}
}
