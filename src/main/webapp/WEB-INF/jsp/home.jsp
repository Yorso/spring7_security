<!DOCTYPE HTML>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<html>
	<head>
		<meta charset="utf-8">
		<title>Home Page</title>
	</head>

	<body>
	
		<h1>Home page</h1>
		
		<hr  style="color: #ff4477;" size="1"/>
		
		<!-- Displaying page elements only to authenticated users in views -->
		<!-- A full list of all the Spring expressions is available at http://docs.spring.io/spring-security/site/docs/3.0.x/reference/el-access.html. -->
		<sec:authorize access="isAuthenticated()">
			Username: <b><sec:authentication property="principal.username" /></b>
		</sec:authorize>
		
		<hr  style="color: #ff4477;" size="2"/>
		
		<!-- This button only can be watched by ADMIN users -->
		<!-- A full list of all the Spring expressions is available at http://docs.spring.io/spring-security/site/docs/3.0.x/reference/el-access.html. -->
		<sec:authorize access="hasAuthority('ADMIN')">
			<b>Admin Page (only if you are Admin):</b>
			<form action="admin/admin_home" method="post">
			  <input type="submit" value="Admin Home" />
			  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			</form>
		
			<hr  style="color: #ff4477;" size="2"/>
		</sec:authorize>
		
		<b>Logout by link:</b>
		<a href="logout">Logout</a>
		
		<br/>
		<br/>
		
		<b>Logout by submit:</b>
		<form action="logout" method="post">
		  <input type="submit" value="Logout" />
		  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		</form>
		
	</body>
</html>