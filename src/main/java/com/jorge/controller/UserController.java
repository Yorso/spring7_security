package com.jorge.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller // This says this class is a controller
public class UserController {
	
	// Controller method to display the form.
	// It's called for HTTP GET requests
	// Necessary to authenticating users using a CUSTOM login page, not for DEFAULT page
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	// Controller method to process the form when it's submitted.
	@RequestMapping(value="/home", method=RequestMethod.GET)
	public String loginSubmit(HttpServletRequest request) {
		System.out.println(this.getClass().getSimpleName() + "." + new Exception().getStackTrace()[0].getMethodName() + ": User authenticated!!! Redirecting to home from login page");

		return "home";
	}
	
	@RequestMapping(value="/admin/admin_home", method=RequestMethod.POST) // It creates http://localhost:8080/spring7_security/admin/admin_home URL
																	      // If you want http://localhost:8080/spring7_security/admin_home URL you must write '@RequestMapping(value="/admin_home"'. Don't forget change action in home.jsp form
	public String AdminSubmit(HttpServletRequest request) {
		System.out.println(this.getClass().getSimpleName() + "." + new Exception().getStackTrace()[0].getMethodName() + ": User authenticated with ADMIN authority (or role)!!! Redirecting to admin_home from home page");

		return "/admin/admin_home";
	}
	
}
