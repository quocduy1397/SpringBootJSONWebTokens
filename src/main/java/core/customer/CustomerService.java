package core.customer;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import core.custom.CryptWithMD5;

@Service
public class CustomerService {
	
	private static List<Customer> allCustomer = new ArrayList<>();
	
	static
	{
		allCustomer.add(new Customer("CS01", "cs1", "12345", CryptWithMD5.cryptWithMD5("12345")));
		allCustomer.add(new Customer("CS02", "cs2", "12345", CryptWithMD5.cryptWithMD5("12345")));
	}
	
	public List<Customer> getAll()
	{
		return allCustomer;
	}
	
	public Customer findByCustomerID(String customerID)
	{
		for (Customer customer : allCustomer)
		{
			if (customer.getCustomerID().equals(customerID))
			{
				return customer;
			}
		}
		
		return null;
	}
	
	public Boolean addCustomer(Customer newCustomer) 
	{
		for (Customer customer : allCustomer)
		{
			if (customer.getCustomerID().equals(newCustomer.getCustomerID()) 
					|| StringUtils.equals(customer.getUsername(), newCustomer.getUsername()))
			{
				return false;
			}
		}
		
		allCustomer.add(newCustomer);
		return true;
	}
	
	public void deleteCustomer(String customerID) 
	{
		allCustomer.removeIf(customer -> customer.getCustomerID().equals(customerID));
	}
	
	public Customer getCustomerByUsername(String username) 
	{
	    for (Customer customer : allCustomer) 
	    {
	    	if (customer.getUsername().equals(username)) 
	    	{
	    		return customer;
	    	}
	    }
	    return null;
	}
	
	public Boolean checkLogin(Customer inputCustomer) 
	{
	    for (Customer customer : allCustomer) 
	    {
	    	if (StringUtils.equals(inputCustomer.getUsername(), customer.getUsername())
	    			&& StringUtils.equals(inputCustomer.getPassword(), customer.getPassword())) 
	    	{
	    		return true;
	    	}
	    }
	    return false;
	}
}
