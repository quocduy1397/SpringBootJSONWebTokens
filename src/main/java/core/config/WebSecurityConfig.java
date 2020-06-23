package core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import core.custom.CustomAccessDeniedHandler;
import core.custom.RestAuthenticationEntryPoint;
import core.employee.EmployeeJSONWebTokensAuthenticationTokenFilter;
import core.jsonwebtokens.JwtAuthenticationTokenFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() throws Exception {
	    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter = new JwtAuthenticationTokenFilter();
	    jwtAuthenticationTokenFilter.setAuthenticationManager(authenticationManager());
	    return jwtAuthenticationTokenFilter;
	}
	
	@Bean
	public RestAuthenticationEntryPoint restServicesEntryPoint() 
	{
	    return new RestAuthenticationEntryPoint();
	}
	  
	@Bean
	public CustomAccessDeniedHandler customAccessDeniedHandler() 
	{
	    return new CustomAccessDeniedHandler();
	}
	
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception 
	{
	    return super.authenticationManager();
	}
	
	@Bean
	public EmployeeJSONWebTokensAuthenticationTokenFilter employeeJWTAuthentication()
			throws Exception
	{
		EmployeeJSONWebTokensAuthenticationTokenFilter jwt = new EmployeeJSONWebTokensAuthenticationTokenFilter();
		jwt.setAuthenticationManager(authenticationManager());
		return jwt;
	}
	
	protected void configure(HttpSecurity http) throws Exception 
	{
//	    http.csrf().ignoringAntMatchers("/security/**");
//	    http.authorizeRequests().antMatchers("/security/login**").permitAll();
//	    http.antMatcher("/security/**").httpBasic().authenticationEntryPoint(restServicesEntryPoint()).and()
//	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
//	        .antMatchers(HttpMethod.GET, "/security/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
//	        .antMatchers(HttpMethod.POST, "/security/**").access("hasRole('ROLE_ADMIN')")
//	        .antMatchers(HttpMethod.DELETE, "/security/**").access("hasRole('ROLE_ADMIN')").and()
//	        .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
//	        .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());
	    
//		http.csrf().ignoringAntMatchers("/employee/**");
		http.csrf().disable();
	    http.authorizeRequests().antMatchers("/login/**").permitAll();
	    
	    String urlAPI = "/employee/**";
	    http.antMatcher(urlAPI).httpBasic().authenticationEntryPoint(restServicesEntryPoint()).and()
	    	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
	    	.antMatchers(HttpMethod.GET, urlAPI).authenticated()
	    	.antMatchers(HttpMethod.POST, urlAPI).authenticated()
	    	.antMatchers(HttpMethod.DELETE, urlAPI).access("hasRole('ROLE_USER')").and()
	    	.addFilterBefore(employeeJWTAuthentication(), UsernamePasswordAuthenticationFilter.class)
	    	.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());
	}
	
}
