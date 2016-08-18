package com.jorge.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Initialize Spring Security's servlet filter
 * 
 * This class registers, behind the scenes, a servlet filter, which will handle access to any URL of the web application.
 * 
 * The SecurityConfig class will be loaded at startup (because of its declaration in
 * ServletInitializer ). The Spring configuration code that we will write in the following recipes
 * will go in the SecurityConfig class
 *
 */
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
}
