package com.jorge.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Class for the Spring Security configuration
 * 
 * The SecurityConfig class will be loaded at startup (because of its declaration in
 * ServletInitializer ). The Spring configuration code that we will write in the following recipes
 * will go in the SecurityConfig class
 *
 * Try: http://localhost:8080/spring7_security/login
 * 
 */

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	/**
	 * Authenticating users using inMemoryAuthentication() method
	 * 
	 * Spring makes it easy to quickly add a login page to your web application; just define some user
	 * credentials (usernames and passwords) in the security configuration class. To access any page, the
	 * user will have to go through Spring's default login page first
	 * 
	 * By default, the Spring's default login page will be used to protect all the pages of the web application.
	 * This is defined in the default configure() method of Spring Security
	 * 
	 */
	/*@Autowired
	public void configureUsers(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
		.withUser("user1").password("pwd").roles("USER")
		.and()
		.withUser("a").password("a").roles("ADMIN")
		.and()
		.withUser("admin").password("admin_pwd").roles("USER", "ADMIN");
		
		System.out.println(this.getClass().getSimpleName() + "." + new Exception().getStackTrace()[0].getMethodName() + ": Authenticating users using inMemoryAuthentication() method"); // Name of current method to console
	}*/
	
	
	
	/**
	 * Authenticating users using a database
	 * 
	 */
	// Database connection details
	@Bean
	public DataSource dataSource() {
		SecurityContextHolder.getContext().setAuthentication(null);
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/test1");
		dataSource.setUsername("user1");
		dataSource.setPassword("user1pass");
		
		return dataSource;
	}
	
	@Bean
	public DataSourceTransactionManager transactionManager() {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		
		transactionManager.setDataSource(dataSource());
		
		return transactionManager;
	}
	
	
	/**
	 *  With the configure() method overridden, Spring Security will:
	 *  	Use JDBC for authentication
	 *		Use the provided DataSource bean to connect to the database
	 *		Perform these SQL queries to get users and their roles
	 */
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.jdbcAuthentication()
		.dataSource(dataSource())
		.usersByUsernameQuery("select username, password, enabled from users where username=?")
		.authoritiesByUsernameQuery("select username, authority from authorities where username=?");

		System.out.println(this.getClass().getSimpleName() + "." + new Exception().getStackTrace()[0].getMethodName() + " (AuthenticationManagerBuilder): Authenticating users using a database"); // Name of current method to console
	}
	
	
	/**
	 * Override the Spring's default configure() method. Declare the URL of your custom login page, it's not for DEFAULT page
	 *
	 * By default, the Spring's default login page will be used to protect all the pages of the web application.
	 * This is defined in the default configure() method of Spring Security
	 * 
	 * A good example of this with more options and depending on type of user (admin, simply an user or whatever...), goes to a page or another:
	 * http://www.mkyong.com/spring-security/spring-security-form-login-using-database/
	 * 
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
		.authorizeRequests()
			//.antMatchers("/css/**", "/js/**", "/img/**").permitAll() // Doing same thing in 'public void configure(WebSecurity web)' method.
																	   // This enables public access to these folders, but requires authentication for any other request.
			.antMatchers("/admin/**").hasAuthority("ADMIN") // Authorizing only users with a specific authority to view some pages. I.e: ADMIN authority can have access to admin pages in admin folder
			//hasRole("ADMIN") // Authorizing only users with a specific role to view some pages. I.e: ADMIN role can have access to admin pages in admin folder
			.anyRequest()
			.authenticated(); // Pages require authentication for any request
		
		http
		    .formLogin()
		        .loginPage("/login") // CUSTOM login page. Allows user authentication through the custom login page. Comment this line to get DEFAULT login page.
		        .permitAll() // Allows anyone access to the login page
				.defaultSuccessUrl("/home")
				.failureUrl("/login?error");
		
		// logout link (in home.jsp or writing "logout" in URL)
		AntPathRequestMatcher pathRequestMatcher = new AntPathRequestMatcher("/logout");
		http
			.logout()
				.logoutRequestMatcher(pathRequestMatcher) // While going to the URL /logout , the user will be logged out
				.logoutSuccessUrl("/login");
		
		System.out.println(this.getClass().getSimpleName() + "." + new Exception().getStackTrace()[0].getMethodName() + " (HttpSecurity): Custom page 'login.jsp' redirecting to 'home.jsp' when authenticated, including logout link and button (submit) in 'home.jsp'. Only ADMIN role can have access to admin folder pages."); // Name of current method to console
		
	}
	
	/**
	 * Using public folders
	 *
	 * Some folders need their contents to be accessible without authentication, for example, the folder
	 * containing CSS files, the folder containing JavaScript files, and the folder containing static images.
	 * None of these usually contain confidential information and some of their files may be necessary to
	 * display the login page and the public pages of the website properly.
	 * 
	 * This enables public access to these folders
	 * 
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web
			.ignoring()
				.antMatchers("/css/**")
				.antMatchers("/js/**")
				.antMatchers("/img/**");
		
		System.out.println(this.getClass().getSimpleName() + "." + new Exception().getStackTrace()[0].getMethodName() + " (WebSecurity): Ignoring public folders (i.e.)"); // Name of current method to console
	}
	
}
