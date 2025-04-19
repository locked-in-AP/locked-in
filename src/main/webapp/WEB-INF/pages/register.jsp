<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Registration</title>
<link rel="stylesheet" type="text/css" href = "${pageContext.request.contextPath}/css/register.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
<div class="registration-container">
  <div class="header">
    <img src="${pageContext.request.contextPath}/resources/images/system/logo.png" alt="">
  <h1>Sign Up</h1>
  <p>Discover your favorites, add must-haves to your wishlist, and track every order with ease.</p>

  </div> 
  <form action="${pageContext.request.contextPath}/registration" method="post">
    <div class="input-box">

      <input type="text" id="firstName" placeholder="Enter your first name" name = "firstName" required>
    </div>

    <div class="input-box">

      <input type="text" id="middleName" placeholder="Enter your middle name" name = "middleName">
    </div>

    <div class="input-box">

      <input type="text" id="lastName" placeholder="Enter your last name"  name = "lastName" required>
    </div>

    <div class="input-box">

      <input type="email" id="email" placeholder="Enter your email" name =  "email" required>
    </div>

    <div class="input-box">

      <input type="tel" id="phone" placeholder="Enter your phone number" name = "phone"required>
    </div>

        <div class="input-box">
        
      <input type="password" placeholder="Password" required>
      
      <i class="fa-solid fa-lock toggle-password"></i>
    </div>

   <div class="input-box">
   
      <input type="password" placeholder="Confirm Password" required>
      
      <i class="fa-solid fa-lock toggle-password"></i>
    </div>

    <button type="submit" class="btn">Sign Up</button>

    <div class="login">
      <p>Already have an account? <a href="#">Login</a></p>
    </div>
  </form>
 </div>
 
  <script>
    document.querySelectorAll('.toggle-password').forEach(icon => {
      icon.addEventListener('click', function() {
        const input = this.previousElementSibling;
        const isPassword = input.type === 'password';
        
        // Toggle input type
        input.type = isPassword ? 'text' : 'password';
        
        // Toggle icon class
        this.classList.toggle('fa-lock-open');
        this.classList.toggle('fa-lock');
      });
    });
  </script>


</body>
</html>