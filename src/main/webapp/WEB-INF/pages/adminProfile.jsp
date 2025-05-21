<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Profile - LockedIN</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admindashboard.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <style>
        .profile-container {
            max-width: 1200px;
            margin: 2rem auto;
            padding: 2rem;
            background: white;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }

        .profile-header {
            display: flex;
            align-items: center;
            gap: 2rem;
            margin-bottom: 2rem;
            padding-bottom: 2rem;
            border-bottom: 1px solid #eee;
        }

        .profile-picture {
            width: 150px;
            height: 150px;
            border-radius: 50%;
            object-fit: cover;
            border: 4px solid #4CAF50;
        }

        .profile-info {
            flex: 1;
        }

        .profile-name {
            font-size: 2rem;
            color: #333;
            margin-bottom: 0.5rem;
        }

        .profile-role {
            font-size: 1.2rem;
            color: #666;
            margin-bottom: 1rem;
        }

        .profile-details {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 2rem;
            margin-top: 2rem;
        }

        .detail-card {
            background: #f8f9fa;
            padding: 1.5rem;
            border-radius: 8px;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
        }

        .detail-title {
            font-size: 1.1rem;
            color: #666;
            margin-bottom: 0.5rem;
        }

        .detail-value {
            font-size: 1.2rem;
            color: #333;
            font-weight: 500;
        }

        .profile-actions {
            margin-top: 2rem;
            display: flex;
            gap: 1rem;
        }

        .action-btn {
            padding: 0.8rem 1.5rem;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 1rem;
            transition: all 0.3s ease;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
            gap: 0.5rem;
        }

        .edit-btn {
            background: #4CAF50;
            color: white;
        }

        .edit-btn:hover {
            background: #45a049;
        }

        .back-btn {
            background: #f8f9fa;
            color: #333;
            border: 1px solid #ddd;
        }

        .back-btn:hover {
            background: #e9ecef;
        }

        .error-message {
            background: #ffebee;
            color: #c62828;
            padding: 1rem;
            border-radius: 5px;
            margin-bottom: 1rem;
        }

        .success-message {
            background: #e8f5e9;
            color: #388e3c;
            padding: 1rem;
            border-radius: 5px;
            margin-bottom: 1rem;
        }
    </style>
</head>
<body>

  
	<div class="sidebar">

		<div class="sidebar-title">
			<span class="main-title">LockedIN</span>
			<span class="sub-title">Admin Dashboard</span>
		</div>
		<ul class="nav">
			<li><a href="${pageContext.request.contextPath}/admindashboard"><span class="icon"><i class="fas fa-home"></i></span> Dashboard</a></li>
			<li><a href="${pageContext.request.contextPath}/users"><span class="icon"><i class="fas fa-user"></i></span>Users</a></li>
			<li><a href="${pageContext.request.contextPath}/admin/orders"><span class="icon"><i class="fas fa-shopping-cart"></i></span> Orders</a></li>
			<li><a href="${pageContext.request.contextPath}/productList"><span class="icon"><i class="fas fa-box"></i></span> View Product</a></li>
			<li><a href="${pageContext.request.contextPath}/addProduct"><span class="icon"><i class="fas fa-pen"></i></span> Add Product</a></li>
		</ul>
	</div>

    <div class="main-content">

     	<div class="admin-header">
			<h1 class="section-title">Admin Profile</h1>
			<div class="right-section">
				<p>Welcome, ${sessionScope.name}</p>
				<a href="${pageContext.request.contextPath}/logout"
					class="logout-btn"> <i class="fas fa-sign-out-alt"></i> Logout
				</a> 

				
				

					<a href="${pageContext.request.contextPath}/adminProfile"
					class="logout-btn"> <i class="fas fa-user"></i> Profile
				</a>
				

				
				<img
					src="${pageContext.request.contextPath}/resources/images/system/userpfp.png"

				
					alt="Admin Profile" class="admin-pfp">
			</div>
		</div>

        <div class="profile-container">
            <c:if test="${not empty sessionScope.error}">
                <div class="error-message">
                    <i class="fas fa-exclamation-circle"></i> ${sessionScope.error}
                </div>
                <c:remove var="error" scope="session"/>
            </c:if>

            <c:if test="${not empty sessionScope.success}">
                <div class="success-message">
                    <i class="fas fa-check-circle"></i> ${sessionScope.success}
                </div>
                <c:remove var="success" scope="session"/>
            </c:if>

            <div class="profile-header">
                <img src="${pageContext.request.contextPath}/${userDetails.profilePicture != null ? userDetails.profilePicture : 'resources/images/system/userpfp.png'}"
                    alt="Admin Profile Picture" class="profile-picture">
                <div class="profile-info">
                    <h1 class="profile-name">${userDetails.name}</h1>
                    <p class="profile-role">${userDetails.role}</p>
                </div>
            </div>

            <div class="profile-details">
                <div class="detail-card">
                    <div class="detail-title">Nickname</div>
                    <div class="detail-value">${userDetails.nickname != null ? userDetails.nickname : 'Not set'}</div>
                </div>

                <div class="detail-card">
                    <div class="detail-title">Email</div>
                    <div class="detail-value">${userDetails.email}</div>
                </div>

                <div class="detail-card">
                    <div class="detail-title">Date of Birth</div>
                    <div class="detail-value">${userDetails.dateOfBirth != null ? userDetails.dateOfBirth : 'Not set'}</div>
                </div>
            </div>

            <div class="profile-actions">
               <a href="${pageContext.request.contextPath}/updateAdminProfile" class="btn btn-primary">Edit </a>
                <a href="${pageContext.request.contextPath}/admindashboard" class="btn btn-primary">
                    <i class="fas fa-arrow-left"></i> Back to Dashboard
                </a>
            </div>
        </div>
    </div>


</body>
</html>