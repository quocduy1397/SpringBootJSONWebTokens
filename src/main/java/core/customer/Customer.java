package core.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
	private String customerID;
	private String username;
	private String password;
	private String passwordEncoded;
}
