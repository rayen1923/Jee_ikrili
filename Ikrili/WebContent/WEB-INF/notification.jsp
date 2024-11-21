<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Reservation Notifications</title>
</head>
<body>
    <jsp:include page="nav.jsp" />
    <div class="container">
        <%
            ArrayList<Entities.Resarvation> Resarvations = (ArrayList<Entities.Resarvation>) request.getAttribute("reservations");
            if (Resarvations != null && !Resarvations.isEmpty()) {
                for (Entities.Resarvation Resarvation : Resarvations) {
        %>
            <div>
                <%
                if (!Resarvation.getRead()) {
                %>
                    <p>
                        From: <a href="Chat?receiverId=<%= Resarvation.getStudent_id() %>">${sender}</a><br>
                        Date: <%= Resarvation.getResarvation_date() %><br>
                        This house is reserved for: <%= Resarvation.getType() %><br>
                        Status: <%= Resarvation.getStatus() %><br>
                        For house : ${adress}<br>
                    </p>
                    <form action="ResarvationAction" method="post" style="display:inline;">
					    <input type="hidden" name="action" value="reject">
					    <input type="hidden" name="reservationId" value="<%= Resarvation.getResarvation_id() %>">
					    <button type="submit">
					        <img src="https://scontent.ftun14-1.fna.fbcdn.net/v/t1.15752-9/387331026_7117563868306583_8266326362434249713_n.png?_nc_cat=106&ccb=1-7&_nc_sid=9f807c&_nc_ohc=FdrdjFLiZ8EQ7kNvgGY7HH2&_nc_zt=23&_nc_ht=scontent.ftun14-1.fna&oh=03_Q7cD1QFqTEi8fR63QlONI5gatzdLBdAxHg6NZllWWIklbcW7mg&oe=67658450" width="20" height="20">
					    </button>
					</form>
					<form action="ResarvationAction" method="post" style="display:inline;">
					    <input type="hidden" name="action" value="accept">
					    <input type="hidden" name="reservationId" value="<%= Resarvation.getResarvation_id() %>">
					    <button type="submit">
					        <img src="https://scontent.ftun14-1.fna.fbcdn.net/v/t1.15752-9/464894455_1315888996069568_3985875466027666443_n.png?_nc_cat=101&ccb=1-7&_nc_sid=9f807c&_nc_ohc=Hsl9Ct5AddgQ7kNvgHyLvjG&_nc_zt=23&_nc_ht=scontent.ftun14-1.fna&oh=03_Q7cD1QE4jfVIdlLtBnf9Thzdt61G4OJK1sgjTlqkczEenBPGgg&oe=67656544" width="20" height="20">
					    </button>
					</form>
                </div>
                <%
                }
                }
            } else {
        %>
        <p>No houses available at the moment.</p>
        <%
            }
        %>
    </div>
</body>
</html>
