package com.jorge.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;

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
	 * Authenticating users using the default login page
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
	
	// With the configure() method overridden, Spring Security will:
	// 		Use JDBC for authentication
	//		Use the provided DataSource bean to connect to the database
	//		Perform these SQL queries to get users and their roles
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.jdbcAuthentication()
		.dataSource(dataSource())
		.usersByUsernameQuery("select username,password,enabled from users where username=?")
		.authoritiesByUsernameQuery("select username,authority from authorities where username=?");
	}
	
	
	/**
	 *  Override the Spring's default configure() method. Declare the URL of your custom login page, it's not for DEFAULT page
	 *
	 * By default, the Spring's default login page will be used to protect all the pages of the web application.
	 * This is defined in the default configure() method of Spring Security
	 * 
	 * Comment this method to display DEFAULT page
	 * 
	 * A good example of this with more options and depending on type of user (admin, simply an user or whatever...) goes to a page or another:
	 * http://www.mkyong.com/spring-security/spring-security-form-login-using-database/
	 * 
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
		    .formLogin()
		        .loginPage("/login") // Custom login page
		        .permitAll() // Login page is visible for evrerybody
		        .defaultSuccessUrl("/home");
		http
			.authorizeRequests()
				.anyRequest()
				.authenticated(); // Pages need authentication
		
	}
	
}
