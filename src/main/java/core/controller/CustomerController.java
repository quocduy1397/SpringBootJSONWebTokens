package core.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import core.customer.Customer;
import core.customer.CustomerJSONWebTokensService;
import core.customer.CustomerRole;
import core.customer.CustomerRoleService;
import core.customer.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	private final static String TOKEN_HEADER = "Authorization";
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CustomerRoleService customerRoleService;
	
	@Autowired
	private CustomerJSONWebTokensService jwtService;
	
	@GetMapping(value = "/get-all/")
	public ResponseEntity<Object> getAllCustomer(HttpServletRequest request)
	{
		String apiNameKey = "ViewAllCustomer";
		HashMap<String, Object> checkAccessResult = checkCustomerAccess(request, apiNameKey);
		if ((Boolean) checkAccessResult.get("access"))
		{
			return new ResponseEntity<> (customerService.getAll(), HttpStatus.OK);
		}
		
		return new ResponseEntity<> (checkAccessResult.get("message"), HttpStatus.BAD_REQUEST);
	}
	
	private HashMap<String, Object> checkCustomerAccess(HttpServletRequest request, String apiNameKey)
	{
		HashMap<String, Object> result = new HashMap<>();
		String authToken = request.getHeader(TOKEN_HEADER);
		if (authToken == null)
		{
			result.put("message", "Unauthorized");
			result.put("access", false);
			return result;
		}
		
		if (jwtService.validateTokenLogin(authToken))
		{
			String username = jwtService.getUsernameFromToken(authToken);
		    Customer customer = customerService.getCustomerByUsername(username);
		    if (customer != null)
		    {
		    	if (checkCustomerRole(customer, apiNameKey))
		    	{
		    		result.put("message", "OK, Access Allowed!");
					result.put("access", true);
					return result;
		    	}
		    	
		    	result.put("message", "Access Denied!");
				result.put("access", false);
				return result;
		    }
		    
		    result.put("message", "Not Exist Customer!");
			result.put("access", false);
			return result;
		}
		else
		{
			result.put("message", "Invalid Token");
			result.put("access", false);
			return result;
		}
		
	}
	
	private Boolean checkCustomerRole(Customer customer, String apiNameKey)
	{
		List<CustomerRole> roles = customerRoleService.getCustomerRole(customer.getCustomerID());
		if (roles.isEmpty()) return false;
		else
		{
			for (CustomerRole role : roles)
			{
				if (role.getRole().equals(apiNameKey)) return true;
			}
		}
		return false;
	}
}
