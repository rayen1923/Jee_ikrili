<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="Entities.User" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>User Profile</title>
</head>
<body>
    <jsp:include page="nav.jsp" />

	<h1>Profile Page</h1>
    <%
        User user = (User) request.getAttribute("user");
        if (user == null) {
            out.println("<p>Error: User data not found.</p>");
            return;
        }
    %>
    <form action="UpdateProfile" method="post" enctype="multipart/form-data">
        <label>Name:</label><br>
        <input type="text" name="name" value="<%= user.getName() %>" required><br><br>

        <label>Email:</label><br>
        <input type="email" name="mail" value="<%= user.getMail() %>" required><br><br>

        <label>Phone:</label><br>
        <input type="text" name="phone" value="<%= user.getPhone() %>" required><br><br>

        <label>Password:</label><br>
        <input type="password" name="password" value="<%= user.getPassword() %>" required><br><br>

        <label>Profile Picture:</label><br>
        <img src="<%= user.getImg_url() %>" alt="Profile Picture" width="100"><br>
        <input type="file" name="profileImage"><br><br>

        <button type="submit">Update Profile</button>
    </form>

    <br><br>
    <form action="LeaveHouse" method="post">
        <button type="submit">Leave House</button>
    </form>
</body>
</html>