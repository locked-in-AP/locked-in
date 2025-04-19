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
    			<input type="text" name="name" placeholder="Enter your Name" required
        			class="${not empty nameError ? 'error-border' : ''}"
       				value="${name != null ? name : ''}">
    					<c:if test="${not empty nameError}">
        					<p class="field-error">${nameError}</p>
    					</c:if>
			</div>

			<div class="input-box">
				    <input type="text" name="nickname" placeholder="Enter preffered Nickname" required
        			class="${not empty nicknameError ? 'error-border' : ''}"
       				value="${nickname != null ? nickname : ''}">
    					<c:if test="${not empty nicknameError}">
        					<p class="field-error">${nicknameError}</p>
    					</c:if>
			</div>

			<div class="input-box">
				    			<input type="email" name="email" placeholder="Enter your Email" required
        			class="${not empty emailError ? 'error-border' : ''}"
       				value="${email != null ? email : ''}">
    					<c:if test="${not empty emailError}">
        					<p class="field-error">${emailError}</p>
    					</c:if>
			</div>

			<div class="input-box">
				    <input type="tel" name="phoneNumber" placeholder="Enter your Phone number" required
        			class="${not empty phoneNumberError ? 'error-border' : ''}"
       				value="${phoneNumber != null ? phoneNumber : ''}">
    					<c:if test="${not empty phoneNumberError}">
        					<p class="field-error">${phoneNumberError}</p>
    					</c:if>
			</div>

			<div class="input-box">
					<input type="date" name="dateOfBirth" placeholder="Enter your Date of Birth" required
        			class="${not empty DateError ? 'error-border' : ''}"
       				value="${date != null ? date : ''}">
    					<c:if test="${not empty DateError}">
        					<p class="field-error">${DateError}</p>
    					</c:if>
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
					<input type="password" name="password" placeholder="Enter Passowrd" required
        			class="${not empty PasswordError ? 'error-border' : ''}"
       				value="${password != null ? Password : ''}">
    					<c:if test="${not empty PasswordError}">
        					<p class="field-error">${PasswordError}</p>
    					</c:if>
				<i class="fa-solid fa-lock toggle-password"></i>
			</div>

			<div class="input-box">
					<input type="password" name="rePassword" placeholder="Conform Password" required
        			class="${not empty RePasswordError ? 'error-border' : ''}"
       				value="${RePassword != null ? rePassword : ''}">
    					<c:if test="${not empty rePasswordError}">
        					<p class="field-error">${rePasswordError}</p>
    					</c:if>
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
