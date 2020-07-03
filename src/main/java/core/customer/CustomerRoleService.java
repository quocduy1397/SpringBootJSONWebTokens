package core.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class CustomerRoleService {
	
	private static List<CustomerRole> allCustomerRole = new ArrayList<>();
	
	static
	{
		allCustomerRole.add(new CustomerRole("CS01", ""));
		allCustomerRole.add(new CustomerRole("CS01", ""));
		allCustomerRole.add(new CustomerRole("CS02", "ViewAllCustomer"));
	}
	
	public List<CustomerRole> getCustomerRole(String customerID)
	{
		return allCustomerRole.stream()
				.filter(customer -> customer.getCustomerID().equals(customerID)).collect(Collectors.toList());
	}
}
