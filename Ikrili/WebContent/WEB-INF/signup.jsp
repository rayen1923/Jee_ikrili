<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sign Up</title>
<script>
    function previewImage(event) {
        const imgPreview = document.getElementById("imgPreview");
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function(e) {
                imgPreview.src = e.target.result;
                imgPreview.style.display = "block";
            };
            reader.readAsDataURL(file);
        } else {
            imgPreview.style.display = "none";
        }
    }
</script>
</head>
<body>
    <div class="title">SIGN UP</div>
	<form action="SignUp" method="post" enctype="multipart/form-data">
        <img id="imgPreview" style="display:none; max-width: 150px; max-height: 150px;" alt="Image Preview">
        <input type="file" id="profilePicture" name="profilePicture" accept="image/*" onchange="previewImage(event)" required><br>
        <br>
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" required><br>
        <label for="name">Sexe:</label>
        <select id="sex" name="sex">
			<option value="M">Male</option>
			<option value="F">Female</option>
		</select><br>
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required><br>
        <label for="phone">Phone:</label>
        <input type="tel" id="phone" name="phone" pattern="[0-9]{8}" required><br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br>
        <a href="SignIn">Already have an account?</a>
        <button type="submit">Sign Up</button>
    </form>
</body>
</html>