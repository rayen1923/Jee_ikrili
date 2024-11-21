<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@ page import="java.util.List, Entities.House" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CRUD Houses</title>
<style>
.modal {
    display: none; /* Hidden by default */
    position: fixed;
    z-index: 1;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    overflow: auto;
    background-color: rgba(0, 0, 0, 0.4); /* Black with transparency */
}

.modal-content {
    position: fixed;
    background-color: #fff;
    padding: 20px;
    border-radius: 8px;
    width: 50%; /* Width of the modal */
    max-width: 500px; /* Ensure it doesn't get too large */
    box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.5);
    
    /* Centering the modal */
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
}

.close {
    color: #aaa;
    float: right;
    font-size: 24px;
    font-weight: bold;
    cursor: pointer;
}

.close:hover,
.close:focus {
    color: #000;
    text-decoration: none;
}
</style>

</head>
<body>
    <jsp:include page="nav.jsp" />
    <h1>Your Houses</h1>
    
    <%
        List<Entities.House> houses = (List<Entities.House>) request.getAttribute("houses");
        if (houses == null || houses.isEmpty()) {
    %>
        <p>No houses available.</p>
    <%
        } else {
    %>
        <table>
            <thead>
                <tr>
                    <th>Address</th>
                    <th>Description</th>
                    <th>Price</th>
                    <th>Places</th>
                    <th>Images</th>
                    <th>Students</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (Entities.House house : houses) {
                %>
                <tr>
                    <td><%= house.getAdress() %></td>
                    <td><%= house.getDescription() %></td>
                    <td>$<%= house.getPlace_prix() %></td>
                    <td><%= house.getNb_place_oc() %>/<%= house.getNb_place() %></td>
                    <td>
                        <% for (String img : house.getImgs()) { %>
                            <img src="<%= img %>" alt="House Image" style="width: 50px; height: auto;">
                        <% } %>
                    </td>
                    <td>
                        <% if (house.getStudents() != null && !house.getStudents().isEmpty()) { %>
                            <ul>
                                <% for (Entities.User student : house.getStudents()) { %>
                                    <li>
                                        <%= student.getName() %>
                                        <form action="KickStudent" method="post" style="display:inline;">
                                            <input type="hidden" name="houseId" value="<%= house.getHouse_id() %>">
                                            <input type="hidden" name="studentId" value="<%= student.getUser_id() %>">
                                            <button type="submit" style="background-color: red; color: white;">Kick</button>
                                        </form>
                                    </li>
                                <% } %>
                            </ul>
                        <% } else { %>
                            No students
                        <% } %>
                    </td>
                    <td>
                        <button onclick="openUpdateModal(<%= house.getHouse_id() %>, '<%= house.getAdress() %>', '<%= house.getDescription() %>', <%= house.getNb_place() %>, <%= house.getPlace_prix() %>)">Edit</button>
                        <button onclick="openDeleteModal(<%= house.getHouse_id() %>)" style="background-color: red; color: white;">Delete</button>
                    </td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    <%
        }
    %>
	<div id="addHouseModal" class="modal">
	    <div class="modal-content">
	        <span class="close" onclick="closeModal()">&times;</span>
	        <h2>Add New House</h2>
			<form action="CrudHouses" method="post" enctype="multipart/form-data">
	            <label for="adress">Address:</label>
	            <input type="text" id="adress" name="adress" required><br>
	            
	            <label for="description">Description:</label>
	            <input type="text" id="description" name="description" required><br>
	            
	            <label for="nb_place">Number of Places:</label>
	            <input type="number" id="nb_place" name="nb_place" required><br>
	            
	            <label for="place_prix">Price per Place:</label>
	            <input type="number" step="0.01" id="place_prix" name="place_prix" required><br>
	            
	            <label for="images">Upload Images:</label>
            	<input type="file" id="images" name="images" accept="image/*" multiple><br>
            	
	            <button type="submit">Add House</button>
	        </form>
	    </div>
	</div>
	
	<div id="deleteModal" class="modal">
	    <div class="modal-content">
	        <span class="close" onclick="closeModal('deleteModal')">&times;</span>
	        <h2>Confirm Deletion</h2>
	        <p>Are you sure you want to delete this house?</p>
	        <form id="deleteForm" action="DeleteHouse" method="post">
	            <input type="hidden" id="deleteHouseId" name="houseId">
	            <button type="submit">Delete</button>
	            <button type="button" onclick="closeModal('deleteModal')">Cancel</button>
	        </form>
	    </div>
	</div>
	
	<div id="updateModal" class="modal">
	    <div class="modal-content">
	        <span class="close" onclick="closeModal('updateModal')">&times;</span>
	        <h2>Update House</h2>
	        <form id="updateForm" action="UpdateHouse" method="post">
	            <input type="hidden" id="updateHouseId" name="houseId">
	            <label for="updateAddress">Address:</label>
	            <input type="text" id="updateAddress" name="adress" required><br>
	            <label for="updateDescription">Description:</label>
	            <input type="text" id="updateDescription" name="description" required><br>
	            <label for="updateNbPlace">Number of Places:</label>
	            <input type="number" id="updateNbPlace" name="nb_place" required><br>
	            <label for="updatePrice">Price per Place:</label>
	            <input type="number" step="0.01" id="updatePrice" name="place_prix" required><br>
	            <button type="submit">Update</button>
	        </form>
	    </div>
	</div>
	<script>
		function closeModal(modalId) {
		    const modal = document.getElementById(modalId);
		    if (modal) {
		        modal.style.display = 'none';
		    }
		}

		function openModal() {
		    document.getElementById('addHouseModal').style.display = 'block';
		}
		
		function openDeleteModal(houseId) {
	        document.getElementById('deleteHouseId').value = houseId;
	        document.getElementById('deleteModal').style.display = 'block';
	    }
	
	    function openUpdateModal(houseId, address, description, nbPlace, price) {
	        document.getElementById('updateHouseId').value = houseId;
	        document.getElementById('updateAddress').value = address;
	        document.getElementById('updateDescription').value = description;
	        document.getElementById('updateNbPlace').value = nbPlace;
	        document.getElementById('updatePrice').value = price;
	        document.getElementById('updateModal').style.display = 'block';
	    }
	    const feedbackMessage = document.getElementById('feedbackMessage');
	    const showMessage = (message, success = true) => {
	        feedbackMessage.style.display = 'block';
	        feedbackMessage.textContent = message;
	        feedbackMessage.style.borderColor = success ? 'green' : 'red';
	        feedbackMessage.style.color = success ? 'green' : 'red';
	    };
	    window.onclick = function (event) {
	        const modals = document.querySelectorAll('.modal');
	        modals.forEach(modal => {
	            if (event.target === modal) {
	                modal.style.display = 'none';
	            }
	        });
	    };

	    window.onkeydown = function (event) {
	        if (event.key === 'Escape') {
	            const modals = document.querySelectorAll('.modal');
	            modals.forEach(modal => {
	                modal.style.display = 'none';
	            });
	        }
	    };

	</script>
	<button onclick="openModal()">Add House</button>
</body>
</html>