<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>All Users - LockedIN</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admindashboard.css" />
    <link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="sidebar">
		<ul class="nav">
			<li><a href="${pageContext.request.contextPath}/admindashboard"><span class="icon"><i class="fas fa-home"></i></span> Dashboard</a></li>
			<li><a href="${pageContext.request.contextPath}/users"><span class="icon"><i class="fas fa-user"></i></span>Users</a></li>
			<li><a href="${pageContext.request.contextPath}/admin/orders"><span class="icon"><i class="fas fa-shopping-cart"></i></span> Orders</a></li>
			<li><a href="${pageContext.request.contextPath}/addProduct"><span class="icon"><i class="fas fa-pen"></i></span> Add Product</a></li>
			<li><a href="${pageContext.request.contextPath}/updateProduct"><span class="icon"><i class="fas fa-pen"></i></span> Edit Product</a></li>
			<li><a href="${pageContext.request.contextPath}/deleteProduct"><span class="icon"><i class="fas fa-trash"></i></span> Delete Product</a></li>
			
		</ul>
	</div>
    
    
    <div class="main-content">
    <div class="admin-header">
			<h1 class="section-title">Registered Users</h1>
			<div class="right-section">
				<p>Welcome, Admin</p>
				<a href="${pageContext.request.contextPath}/logout" class="logout-btn">
					<i class="fas fa-sign-out-alt"></i> Logout
				</a>
				<img src="${pageContext.request.contextPath}/resources/images/system/userpfp.png"
					alt="Admin Profile" class="admin-pfp">
			</div>
		</div>
       
        <div class="table-container">
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Nickname</th>
                        <th>Email</th>
                        <th>Role</th>
                        <th>Date of Birth</th>
                        <th>Date Joined</th>
                        <th>Cart Size</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${users}">
                        <tr>
                            <td>${user.userId}</td>
                            <td>${user.name}</td>
                            <td>${user.nickname}</td>
                            <td>${user.email}</td>
                            <td>${user.role}</td>
                            <td>${user.dateOfBirth}</td>
                            <td>${user.joinedAt}</td>
                            <td>${user.cartSize}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <a href="${pageContext.request.contextPath}/deleteUser" class="btn btn-primary">Delete User by ID</a>
        <a href="${pageContext.request.contextPath}/admindashboard" class="btn btn-primary">Back to Dashboard</a>
    </div>
</body>
</html> 