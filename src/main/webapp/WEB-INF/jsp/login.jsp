<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>

<html>
	<head>
		<meta charset="utf-8">
		<title>Custom page</title>
	</head>

	<body>
		
		<h3>CUSTOM PAGE</h3>
		
		<c:url var="loginUrl" value="login" />
		
		<form method="POST" action="login">
		
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			
			<c:if test="${param.error != null}">
				<p>
					Invalid username and password.
				</p>
			</c:if>
			
			<p>
				<label for="username">Username</label>
				<input type="text" id="username" name="username"/>
			</p>
			
			<p>
				<label for="password">Password</label>
				<input type="password" id="password" name="password"/>
			</p>
			
			<button type="submit">Log in</button>
			<!-- input type="submit" value="Log in"/>  -->
			
		</form>
		
	</body>
</html>