<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>User Registration</title>
	<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/css/register.css">
	<link rel="stylesheet"
		href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
	<div class="registration-container">
		<div class="header">
			<img src="${pageContext.request.contextPath}/resources/images/system/logo.png" alt="">
			<h1>Sign Up</h1>
			<p>Get ready to LOCK IN and become a better YOU</p>
		</div>

		<!-- Error / Success Message -->
		<c:if test="${not empty error}">
			<p class="error-msg">${error}</p>
		</c:if>
		<c:if test="${not empty success}">
			<p class="success-msg">${success}</p>
		</c:if>

		<form action="${pageContext.request.contextPath}/register" method="post">
			<div class="input-box">
				<input type="text" name="name" placeholder="Enter your name" required value="${name != null ? name : ''}">
			</div>

			<div class="input-box">
				<input type="text" name="nickname" placeholder="Enter your preferred nickname" required value="${nickname != null ? nickname : ''}">
			</div>

			<div class="input-box">
				<input type="email" name="email" placeholder="Enter your email" required value="${email != null ? email : ''}">
			</div>

			<div class="input-box">
				<input type="tel" name="phoneNumber" placeholder="Enter your phone number" required value="${phoneNumber != null ? phoneNumber : ''}">
			</div>

			<div class="input-box">
				<input type="date" name="dateOfBirth" required value="${dateOfBirth != null ? dateOfBirth : ''}">
			</div>

			<div class="input-box">
				<select name="gender" required>
					<option value="" disabled ${empty gender ? 'selected' : ''}>Select gender</option>
					<option value="male" ${gender == 'male' ? 'selected' : ''}>Male</option>
					<option value="female" ${gender == 'female' ? 'selected' : ''}>Female</option>
					<option value="prefer_not_to_say" ${gender == 'prefer_not_to_say' ? 'selected' : ''}>Prefer not to say</option>
				</select>

			</div>

			<div class="input-box">
				<input type="password" name="password" placeholder="Enter your password" required>
				<i class="fa-solid fa-lock toggle-password"></i>
			</div>

			<div class="input-box">
				<input type="password" name="repassword" placeholder="Confirm password" required>
				<i class="fa-solid fa-lock toggle-password"></i>
			</div>

			<button type="submit" class="btn">Sign Up</button>

			<div class="login">
				<p>Already have an account? <a href="/login">Login</a></p>
			</div>
		</form>
	</div>

	<script>
		document.querySelectorAll('.toggle-password').forEach(icon => {
			icon.addEventListener('click', function () {
				const input = this.previousElementSibling;
				const isPassword = input.type === 'password';
				input.type = isPassword ? 'text' : 'password';
				this.classList.toggle('fa-lock-open');
				this.classList.toggle('fa-lock');
			});
		});
	</script>
</body>
</html>
