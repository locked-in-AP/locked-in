<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Update Profile - LockedIN</title>
<link rel="stylesheet" type="text/css"
    href="${pageContext.request.contextPath}/css/updateProfile.css" />
<link rel="stylesheet"
    href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
</head>
<body>
    <jsp:include page="header.jsp" />

    <div class="update-profile-container">
        <div class="update-form-header">
            <h1>Update Your Profile</h1>
            <p>Keep your information up to date</p>
        </div>

        <c:if test="${not empty success}">
               <p class="alert success-msg">${success}</p>       
                </c:if>

        <c:if test="${not empty error}">
             <p class="alert error-msg">${success}</p>
        </c:if>

        <form action="${pageContext.request.contextPath}/updateProfile" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="name">Full Name</label>
                <div class="input-box">
                    <i class="fa-solid fa-user input-icon"></i>
                    <input type="text" id="name" name="name" value="${userDetails.name}" required>
                </div>
            </div>

            <div class="form-group">
                <label for="nickname">Nickname</label>
                <div class="input-box">
                    <i class="fa-solid fa-user-tag input-icon"></i>
                    <input type="text" id="nickname" name="nickname" value="${userDetails.nickname}">
                </div>
            </div>

            <div class="form-group">
                <label for="email">Email</label>
                <div class="input-box">
                    <i class="fa-solid fa-envelope input-icon"></i>
                    <input type="email" id="email" name="email" value="${userDetails.email}" readonly>
                    <small>Email cannot be changed</small>
                </div>
            </div>

            <div class="form-group">
                <label for="currentPassword">Current Password (required to save changes)</label>
                <div class="input-box">
                    <i class="fa-solid fa-lock input-icon"></i>
                    <input type="password" id="currentPassword" name="currentPassword" required>
                    <i class="fa-solid fa-eye toggle-password" data-target="currentPassword"></i>
                </div>
            </div>

            <div class="form-group">
                <label for="newPassword">New Password (leave blank to keep current)</label>
                <div class="input-box">
                    <i class="fa-solid fa-key input-icon"></i>
                    <input type="password" id="newPassword" name="newPassword">
                    <i class="fa-solid fa-eye toggle-password" data-target="newPassword"></i>
                </div>
            </div>

            <div class="form-group">
                <label for="confirmPassword">Confirm New Password</label>
                <div class="input-box">
                    <i class="fa-solid fa-key input-icon"></i>
                    <input type="password" id="confirmPassword" name="confirmPassword">
                    <i class="fa-solid fa-eye toggle-password" data-target="confirmPassword"></i>
                </div>
            </div>
            
		            <div class="form-group">
		            
		    <label for="profilePicture">Upload Profile Picture</label>
		    <div class="input-box">
		        <i class="fa-solid fa-image input-icon"></i> <!-- Changed icon to an image icon -->
		        <input type="file" id="profilePicture" name="profilePicture" accept="image/*" >
		    </div>
		</div>
            

            <div class="button-container">
                <button type="submit" class="update-btn">Update Profile</button>
                <a href="${pageContext.request.contextPath}/userprofile" class="cancel-btn">Cancel</a>
            </div>
        </form>
    </div>

    <jsp:include page="footer.jsp" />
    
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // Password toggle functionality
            const toggleButtons = document.querySelectorAll('.toggle-password');
            
            toggleButtons.forEach(button => {
                button.addEventListener('click', function() {
                    const targetId = this.getAttribute('data-target');
                    const passwordInput = document.getElementById(targetId);
                    
                    // Toggle password visibility
                    if (passwordInput.type === 'password') {
                        passwordInput.type = 'text';
                        this.classList.remove('fa-eye');
                        this.classList.add('fa-eye-slash');
                    } else {
                        passwordInput.type = 'password';
                        this.classList.remove('fa-eye-slash');
                        this.classList.add('fa-eye');
                    }
                });
            });
        });
    </script>
</body>
</html> 