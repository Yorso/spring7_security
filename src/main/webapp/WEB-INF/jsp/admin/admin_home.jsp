<!DOCTYPE HTML>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<html>
	<head>
		<meta charset="utf-8">
		<title>Admin Home Page</title>
	</head>

	<body>
	
		<h1>Admin Home Page</h1>
		
		<hr  style="color: #ff4477;" size="1"/>
		
		<sec:authorize access="isAuthenticated()">
			Username: <b><sec:authentication property="principal.username" /></b>
		</sec:authorize>
		
		<hr  style="color: #ff4477;" size="2"/>
		
		<b>Logout by link:</b>
		<a href="../logout">Logout</a>
		
		<br/>
		<br/>
		
		<b>Logout by submit:</b>
		<form action="../logout" method="post">
		  <input type="submit" value="Logout" />
		  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		</form>
		
	</body>
</html>