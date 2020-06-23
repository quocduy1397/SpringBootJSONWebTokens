package core.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import core.employee.Employee;
import core.employee.EmployeeJSONWebTokensService;
import core.employee.EmployeeService;

@RestController
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private EmployeeJSONWebTokensService jwtService;
	
	@PostMapping(value = "/")
	public ResponseEntity<Object> login(HttpServletRequest request, @RequestBody Employee employee)
	{
		String result = "";
	    HttpStatus httpStatus = null;
	    try 
	    {
	    	if (employeeService.checkLogin(employee)) 
	    	{
	    		result = jwtService.generateTokenLogin(employee.getUsername(), employee.getPassword());
	    		httpStatus = HttpStatus.OK;
	    	} 
	    	else 
	    	{
	    		result = "Wrong username and password";
	    		httpStatus = HttpStatus.BAD_REQUEST;
	    	}
	    } 
	    catch (Exception ex) 
	    {
	    	result = "Server Error";
	    	httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	    }
	    
		return new ResponseEntity<> (result, httpStatus);
	}
}
