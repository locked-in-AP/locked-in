<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/login.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
</head>
<body>
	<div class="login-container">

		<c:if test="${not empty success}">
			<p class="success-msg">${success}</p>
		</c:if>

		<form action="${pageContext.request.contextPath}/login" method="post">
			<div class="header">
				<img
					src="${pageContext.request.contextPath}/resources/images/system/logo.png"
					alt="" />
				<h1>Login</h1>
				<p>Discover your favorites, add must-haves to your wishlist, and
					track every order with ease.</p>
			</div>

			<c:if test="${not empty error}">
				<div class="error-message">${error}</div>
			</c:if>

			<div class="input-box">
				<input type="email" placeholder="Email" name="email" required>
				<i class="fa-solid fa-user"></i>
			</div>

			<div class="input-box">
				<input type="password" placeholder="Password" name="password"
					required> <i class="fa-solid fa-eye toggle-password"></i>
			</div>

			<div class="forgot-box">
				<a href="#">Forgot password?</a>
			</div>

			<button type="submit" class="btn">Login</button>

			<div class="registeration">
				<p>
					Don't have an account? <a
						href="${pageContext.request.contextPath}/register">Register</a>
				</p>
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
        this.classList.toggle('fa-eye');
        this.classList.toggle('fa-eye-slash');
      });
    });
  </script>

</body>
</html>