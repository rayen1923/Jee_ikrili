<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sign In</title>
</head>
<body>
	<div class="title">SIGN IN</div>
    <form action="SignIn" method="post">
    	<% if (request.getParameter("error") != null) { %>
			<p style="color:red;">Invalid username or password.</p>
        <% } %>
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required><br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br>
        <a href="SignUp">I don't have an account?</a>
        <button type="submit">Sign In</button>
    </form>
</body>
</html>