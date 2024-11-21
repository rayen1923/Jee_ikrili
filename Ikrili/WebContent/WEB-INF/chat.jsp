<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Chat Messages</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .chat-container {
            width: 60%;
            margin: auto;
            border: 1px solid #ccc;
            padding: 10px;
            border-radius: 5px;
            background-color: #f9f9f9;
        }
        .message-box {
            border: 1px solid #ddd;
            padding: 5px;
            margin-bottom: 10px;
            border-radius: 5px;
        }
        .message-box.sent {
            background-color: #e0ffe0;
            text-align: right;
        }
        .message-box.received {
            background-color: #ffe0e0;
        }
        .input-container {
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <jsp:include page="nav.jsp" />

    <div class="chat-container">
        <h2>Chat with <c:out value="${receiverName}" /></h2>
        
        <div id="chat-messages">
            <c:forEach var="message" items="${messages}">
                <div class="message-box ${message.senderId == sessionScope.userId ? 'sent' : 'received'}">
                    <c:out value="${message.text}" />
                    <small style="display: block; font-size: 0.8em; color: #666;">
                        Sent at: <c:out value="${message.timestamp}" />
                    </small>
                </div>
            </c:forEach>
        </div>
        
        <div class="input-container">
            <form action="Chat" method="POST">
                <input type="hidden" name="receiverId" value="${receiverId}" />
                <textarea name="messageText" rows="3" style="width: 100%;" required></textarea><br/>
                <button type="submit">Send</button>
            </form>
        </div>
    </div>
</body>
</html>
