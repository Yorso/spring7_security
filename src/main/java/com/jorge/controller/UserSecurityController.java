package com.jorge.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller // This says this class is a controller
public class UserSecurityController {
	
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
		System.out.println(this.getClass().getSimpleName() + "." + new Exception().getStackTrace()[0].getMethodName() + ": User authenticated!! Redirecting to home from login page");

		return "home";
	}
	
}
