<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Chat History</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .user-list {
            list-style-type: none;
            padding: 0;
        }
        .user-item {
            margin: 10px 0;
        }
        .no-chats {
            color: red;
        }
    </style>
</head>
<body>
    <jsp:include page="nav.jsp" />

	<h2>Your Chat History</h2>

    <c:choose>
        <c:when test="${empty chatUsers}">
            <p class="no-chats">You have no chat history.</p>
        </c:when>
        <c:otherwise>
            <ul class="user-list">
                <c:forEach var="user" items="${chatUsers}">
                    <li class="user-item">
                        <img src="${user.getImg_url()} " alt="Profile Picture" width="100">
                        <a href="Chat?receiverId=${user.getUser_id()}">${user.getName()}</a>
                    </li>
                </c:forEach>
            </ul>
        </c:otherwise>
    </c:choose>
</body>
</html>
