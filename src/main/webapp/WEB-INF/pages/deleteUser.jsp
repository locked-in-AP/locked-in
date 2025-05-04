<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Delete User - LockedIN</title>
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
			<li><a href="${pageContext.request.contextPath}/addProduct"><span class="icon"><i class="fas fa-pen"></i></span> Add Product</a></li>
			<li><a href="${pageContext.request.contextPath}/updateProduct"><span class="icon"><i class="fas fa-pen"></i></span> Edit Product</a></li>
			<li><a href="${pageContext.request.contextPath}/deleteProduct"><span class="icon"><i class="fas fa-trash"></i></span> Delete Product</a></li>
			
		</ul>
	</div>
    <div class="main-content">
        <div class="admin-header">
            <h1 class="section-title">Delete User</h1>
            <div class="right-section">
                <p>Welcome, Admin</p>
                <a href="${pageContext.request.contextPath}/logout" class="logout-btn">
                    <i class="fas fa-sign-out-alt"></i> Logout
                </a>
                <img src="${pageContext.request.contextPath}/resources/images/system/userpfp.png"
                    alt="Admin Profile" class="admin-pfp">
            </div>
        </div>
        <c:if test="${not empty success}">
              <p class="alert success-msg">${success}</p>
        </c:if>
        <c:if test="${not empty error}">
               <p class="alert error-msg">${error}</p>
        </c:if>
        <form action="${pageContext.request.contextPath}/deleteUser" method="post" class="product-form">
            <div class="form-group">
                <label for="userId">User ID to Delete</label>
                <input type="number" id="userId" name="userId" required min="1">
            </div>
            <div class="form-actions">
                <button type="submit" class="submit-btn">Delete User</button>
                <a href="${pageContext.request.contextPath}/users" class="cancel-btn">Cancel</a>
            </div>
        </form>
    </div>
</body>
</html> 