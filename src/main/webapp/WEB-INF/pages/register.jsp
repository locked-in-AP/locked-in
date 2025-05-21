<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
			<img
				src="${pageContext.request.contextPath}/resources/images/system/logo.png"
				alt="">
			<h1>Register</h1>
			<p>Get ready to LOCK IN and become a better YOU</p>
		</div>

		<!-- Error / Success Message -->
		<c:if test="${not empty error}">
			<p class="error-msg">${error}</p>
		</c:if>

		<form action="${pageContext.request.contextPath}/register"
			method="post"  enctype="multipart/form-data" novalidate>
			<div class="input-box">
				<input type="text" name="name" placeholder="Enter your Name"
					class="${not empty nameError ? 'error-border' : ''}"
					value="${name != null ? name : ''}">
			</div>
			<c:if test="${not empty nameError}">
				<p class="field-error">${nameError}</p>
			</c:if>

			<div class="input-box">
				<input type="text" name="nickname"
					placeholder="Enter preferred Nickname"
					class="${not empty nicknameError ? 'error-border' : ''}"
					value="${nickname != null ? nickname : ''}">
			</div>
			<c:if test="${not empty nicknameError}">
				<p class="field-error">${nicknameError}</p>
			</c:if>

			<div class="input-box">
				<input type="email" name="email" placeholder="Enter your Email"
					class="${not empty emailError ? 'error-border' : ''}"
					value="${email != null ? email : ''}">
			</div>
			<c:if test="${not empty emailError}">
				<label class="field-error">${emailError}</label>
			</c:if>

			<div class="input-box disappearing-label">
				<input type="date" name="dateOfBirth" id="dateOfBirth"
					class="${not empty dateOfBirthError ? 'error-border' : ''}"
					value="${dateOfBirth != null ? dateOfBirth : ''}"> 
				<label for="dateOfBirth">Date of birth</label>
			</div>
			<c:if test="${not empty dateOfBirthError}">
				<p class="field-error">${dateOfBirthError}</p>
			</c:if>

			<div class="input-box">
				<input type="password" name="password" placeholder="Enter Password"
					value="${password != null ? password : ''}"
					class="${not empty passwordError ? 'error-border' : ''}"> 
				<i class="fa-solid fa-eye toggle-password"></i>
			</div>
			<c:if test="${not empty passwordError}">
				<p class="field-error">${passwordError}</p>
			</c:if>

			<div class="input-box">
				<input type="password" name="repassword"
					value="${repassword != null ? repassword : ''}"
					placeholder="Confirm Password"
					class="${not empty repasswordError ? 'error-border' : ''}">
				<i class="fa-solid fa-eye toggle-password"></i>
			</div>
			<c:if test="${not empty repasswordError}">
				<p class="field-error">${repasswordError}</p>
			</c:if>
			
			<div class="input-box">
				<label for="profilePicture" class="file-upload-label">
					<i class="fas fa-camera"></i> Upload Profile Picture
				</label>
				<input type="file" name="profilePicture" id="profilePicture" accept="image/*" class="file-upload-input">
			</div>
			
			<button type="submit" class="btn">Register</button>

			<div class="login">
				<p>
					Already have an account? <a
						href="${pageContext.request.contextPath}/login">Login</a>
				</p>
			</div>
		</form>
	</div>

	<script>
        document.querySelectorAll('.toggle-password').forEach(icon => {
            icon.addEventListener('click', function () {
                const input = this.parentElement.querySelector('input');
                const isPassword = input.type === 'password';
                input.type = isPassword ? 'text' : 'password';
                this.classList.toggle('fa-eye');
                this.classList.toggle('fa-eye-slash');
            });
        });
        
        const dobInput = document.querySelector('#dateOfBirth');
        const dobLabel = dobInput.nextElementSibling;

        function toggleDobLabel() {
            if (dobInput.value) {
                dobLabel.style.display = 'none';
            } else {
                dobLabel.style.display = 'block';
            }
        }

        // Initial check on page load
        toggleDobLabel();

        // Event listener for changes
        dobInput.addEventListener('input', toggleDobLabel);
    </script>
</body>
</html>