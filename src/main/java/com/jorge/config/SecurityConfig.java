package com.jorge.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

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
@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = { "com.jorge.controller" }) // This scans the com.jorge.controller package for Spring components
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
		.withUser("admin").password("admin_pwd").roles("USER", "ADMIN");
	}
	
	/**
	 *  Override the Spring's default configure() method. Declare the URL of your custom login page, it's not for DEFAULT page
	 *
	 * By default, the Spring's default login page will be used to protect all the pages of the web application.
	 * This is defined in the default configure() method of Spring Security
	 * 
	 * Comment this method to display DEFAULT page
	 * 
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated(); // Requires authentication for any URL (anyRequest().authenticated())
		http.formLogin().loginPage("/login").permitAll(); // Allows user authentication through the custom login page (formLogin().loginPage("/login")) and
														  // allows anyone access to the login page (loginPage("/login").permitAll())
	}
	
	/**
	 * Authenticating users using a database
	 * 
	 */
	// Database connection details
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/test1");
		dataSource.setUsername("user1");
		dataSource.setPassword("user1pass");
		
		return dataSource;
	}
	
	/**
	 * It is a Spring object that provides convenient methods to query a database
	 * using JDBC. It uses the previously defined DataSource bean (above). We will use the JdbcTemplate bean
	 * from our DAO classes
	 * 
	 */
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
	
	/**
	 * Reverting incomplete database modifications using transactions
	 * Some database modifications involve several SQL queries, for example, inserting an object with
	 * attributes spread across several tables. If one of the queries fails, we would want to undo any
	 * previous ones that were successful
	 * 
	 */
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
}
