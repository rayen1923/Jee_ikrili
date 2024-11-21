<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Find House</title>
    <style>
        .container {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
        }
        .card {
            background-color: white;
            border: 1px solid #ddd;
            border-radius: 8px;
            width: 300px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            overflow: hidden;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
        }
        .card img {
            width: 100%;
            height: 200px;
            object-fit: cover;
        }
        .card-body {
            padding: 15px;
        }
        .card-title {
            font-size: 18px;
            font-weight: bold;
            margin: 0 0 10px;
        }
        .card-text {
            font-size: 14px;
            margin: 0 0 15px;
            color: #555;
        }
        .card-footer {
            padding: 10px;
            text-align: right;
            background-color: #f9f9f9;
        }
        .view-btn {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 10px 15px;
            text-decoration: none;
            border-radius: 5px;
            font-size: 14px;
            cursor: pointer;
        }
        .view-btn:hover {
            background-color: #0056b3;
        }
        .no-results {
            font-size: 18px;
            color: #e74c3c;
            text-align: center;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <jsp:include page="nav.jsp" />

    <h1>Find Houses</h1>

    <form method="get" action="FindHouse">
        <input type="text" name="searchAddress" placeholder="Search by Address" />
        <button type="submit">Search</button>
    </form>

    <div class="container">
        <%
            ArrayList<Entities.House> houses = (ArrayList<Entities.House>) request.getAttribute("houses");
            if (houses != null && !houses.isEmpty()) {
                for (Entities.House house : houses) {
                    if (house.getNb_place_oc() < house.getNb_place()) {
                        String imageUrl = house.getImgs().isEmpty() ? "default-house.jpg" : house.getImgs().get(0);
        %>
        <div class="card">
            <img src="<%= imageUrl %>" alt="House Image">
            <div class="card-body">
                <h2 class="card-title"><%= house.getAdress() %></h2>
                <p class="card-text">
                    Description: <%= house.getDescription() %><br>
                    Price per Place: $<%= house.getPlace_prix() %><br>
                    Available Places: <%= house.getNb_place_oc() %> / <%= house.getNb_place() %>
                </p>
            </div>
            <div class="card-footer">
                <a href="HouseDetails?houseId=<%= house.getHouse_id() %>" class="view-btn">View</a>
            </div>
        </div>
        <%
                }
            }
        } else {
        %>
        <p class="no-results">No houses found for your search.</p>
        <%
            }
        %>
    </div>
</body>
</html>
