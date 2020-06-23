package core.employee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

public class EmployeeJSONWebTokensAuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {
	private final static String TOKEN_HEADER = "Authorization";
	
	@Autowired
	private EmployeeJSONWebTokensService jwtService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private EmployeeRoleService employeeRoleService;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException 
	{
	    HttpServletRequest httpRequest = (HttpServletRequest) request;
	    
	    String authToken = httpRequest.getHeader(TOKEN_HEADER);
	    if (jwtService.validateTokenLogin(authToken)) 
	    {
		      String username = jwtService.getUsernameFromToken(authToken);
		      Employee employee = employeeService.getEmployeeByUsername(username);
		      if (employee != null) 
		      {
		    	  
		    	  List<GrantedAuthority> authorities = new ArrayList<>();
		    	  if (checkAccess(employee, httpRequest.getMethod(), httpRequest.getRequestURL().toString()))
		    	  {
		    		  authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		    	  }
			      boolean enabled = true;
			      boolean accountNonExpired = true;
			      boolean credentialsNonExpired = true;
			      boolean accountNonLocked = true;
			      UserDetails userDetail = new User(username, employee.getPassword(), enabled, accountNonExpired,
			            credentialsNonExpired, accountNonLocked, authorities);
			        
			      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail,
			           null, userDetail.getAuthorities());
			      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
			      SecurityContextHolder.getContext().setAuthentication(authentication);
		      }
	    }
	    
	    chain.doFilter(request, response);
	 }
	
	private Boolean checkAccess(Employee employee, String httpMethod, String url)
	{
		if (employee == null) { return false; }
	    else
	    {
	    	List<EmployeeRole> roles = employeeRoleService.getEmployeeRole(employee.getEmployeeID());
	    	if (roles.isEmpty()) { return false; }
	    	else
	    	{
	    		String[] s = url.split("/", 4);
	    		String api = "/" + s[3];
	    		for (EmployeeRole role : roles)
	    		{
	    			if (role.getHttpMethod().equals(httpMethod) && role.getUrlAPI().equals(api))
	    			{
	    				return true;
	    			}
	    		}
	    	}
	    }
		return false;
	}
}
