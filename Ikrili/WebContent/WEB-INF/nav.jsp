<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
	<style>
        nav ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
            background-color: #333;
            overflow: hidden;
        }
        nav li {
            float: left;
        }
        nav li a {
            display: block;
            color: white;
            text-align: center;
            padding: 14px 16px;
            text-decoration: none;
        }
        nav li a:hover {
            background-color: #111;
        }
        .logout {
            float: right;
        }
    </style>
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    	<a class="navbar-brand" href="#">IKRILI</a>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
        	<ul class="navbar-nav mr-auto">
        		<li class="nav-item">
                	<a class="nav-link" href="CrudHouses">CRUD Houses</a>
                </li>
                <li class="nav-item">
                	<a class="nav-link" href="FindHouse">Find House</a>
              	</li>
                <li class="nav-item">
                	<a class="nav-link" href="Messages">Messages</a>
               	</li>
               	<li class="nav-item">
                	<a class="nav-link" href="Notification">Notification</a>
               	</li>
               	<li class="nav-item">
                	<a class="nav-link" href="UpdateProfile">Profil</a>
               	</li>
          	</ul>
			<a class="nav-link logout" href="Logout">Logout</a>
   		</div>
    </nav>
</body>
</html>