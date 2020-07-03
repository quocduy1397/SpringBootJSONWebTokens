package core.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import core.customer.Customer;
import core.customer.CustomerJSONWebTokensService;
import core.customer.CustomerService;

@RestController
@RequestMapping("/login/customer")
public class CustomerLoginController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CustomerJSONWebTokensService jwtService;
	
	@PostMapping(value = "/")
	public ResponseEntity<Object> login(HttpServletRequest request, @RequestBody Customer customer)
	{
		String result = "";
	    HttpStatus httpStatus = null;
	    try 
	    {
	    	if (customerService.checkLogin(customer)) 
	    	{
	    		result = jwtService.generateTokenLogin(customer.getUsername(), customer.getPassword());
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
