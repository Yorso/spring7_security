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
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	// Controller method to process the form when it's submitted.
	// It's called for HTTP POST requests (because of method=RequestMethod.POST )
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String loginSubmit(HttpServletRequest request) {
		System.out.println("WWWWWWWWW: " + new Exception().getStackTrace()[0].getMethodName()); // Name of current method to console
		String firstName = request.getParameter("firstName");

		//return "redirect:/home"; // Doesn't work
		return "login"; // Go to home.jsp
	}
	
}
