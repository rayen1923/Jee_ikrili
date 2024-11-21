<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>House Details</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .house-card {
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 16px;
            margin: 16px auto;
            width: 80%;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        .house-header {
            font-size: 20px;
            font-weight: bold;
            margin-bottom: 8px;
        }
        .house-description {
            margin: 8px 0;
        }
        .house-details {
            margin: 8px 0;
        }
        .image-gallery img {
            max-width: 150px;
            margin-right: 8px;
        }
        .students-list {
            list-style-type: none;
            padding: 0;
        }
        .students-list li {
            margin: 4px 0;
        }
        .back-button {
            margin-top: 16px;
            display: inline-block;
            padding: 8px 16px;
            background-color: #007BFF;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
        .back-button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <jsp:include page="nav.jsp" />
    
    <c:forEach var="house" items="${house}">
        <div class="house-card">
            <div class="house-header">Adress :${house.adress}</div>
            <div class="house-description">
                <strong>Description:</strong> ${house.description}
            </div>
            <div class="house-details">
                <p><strong>Places:</strong> ${house.nb_place_oc} /${house.nb_place}</p>
                <p><strong>Price per Place:</strong> ${house.place_prix}</p>
				<p>
				    <strong>Owner:</strong> 
				    <a href="Chat?receiverId=${house.owner_id}">
				        ${owner}
				    </a>
				</p>
            </div>
            <div class="image-gallery">
                <strong>Images:</strong></br>
                <c:forEach var="image" items="${house.imgs}">
                    <img src="${image}" alt="House Image">
                </c:forEach>
            </div>
            <div>
			    <strong>Students:</strong></br>
			    <c:if test="${empty house.students}">
			        <p>No students have been assigned to this house.</p>
			    </c:if>
			    <c:if test="${not empty house.students}">
			        <ul class="students-list">
			            <c:forEach var="student" items="${house.students}">
			                <li>${student.name} (ID: ${student.user_id})</li>
			            </c:forEach>
			        </ul>
			    </c:if>
			</div>
			 <form action="ResetPlace" method="post" style="display:inline-block;">
                <input type="hidden" name="houseId" value="${house.house_id}">
                <input type="hidden" name="ownerId" value="${house.owner_id}">
                <input type="submit" class="reset-button" value="Reset One Place">
            </form>

            <form action="ResetAllPlaces" method="post" style="display:inline-block;">
                <input type="hidden" name="houseId" value="${house.house_id}">
                <input type="hidden" name="ownerId" value="${house.owner_id}">
                <input type="submit" class="reset-all-button" value="Reset All Places">
            </form>
        </div>
    </c:forEach>

    <a href="FindHouse" class="back-button">Back to House List</a>
</body>
</html>
