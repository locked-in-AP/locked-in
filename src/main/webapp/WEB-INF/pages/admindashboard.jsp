<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/admindashboard.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

<meta charset="UTF-8">
<title>Admin Dashboard - LockedIN</title>
</head>
<body>

	<jsp:include page="header.jsp" />
	<c:if test="${not empty success}">
		<p class="success-msg">You are logged in!</p>
	</c:if>

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
			<h1 class="section-title">Admin Dashboard</h1>
			<div class="right-section">
				<p>Welcome, Admin</p>

				<a href="${pageContext.request.contextPath}/logout"
					class="logout-btn"> <i class="fas fa-sign-out-alt"></i> Logout
				</a> <img
					src="${pageContext.request.contextPath}/resources/images/system/userpfp.png"
					alt="Admin Profile" class="admin-pfp">
			</div>
		</div>

		<c:if test="${not empty error}">
			<div class="error-message">
				<i class="fas fa-exclamation-circle"></i> ${error}
			</div>
		</c:if>

		<div class="dashboard-container">
			<h1 class="section-title">Overview</h1>
			<div class="metrics-grid">
				<div class="metric-card">
					<div class="metric-content">
						<h3 class="metric-title">Total Revenue</h3>
						<p class="metric-subtitle">Last 30 days</p>
					</div>
					<div class="metric-value revenue">$82,650</div>
				</div>

				<div class="metric-card">
					<div class="metric-content">
						<h3 class="metric-title">Total Orders</h3>
						<p class="metric-subtitle">Last 30 days</p>
					</div>
					<div class="metric-value order">16.45</div>
				</div>

				<div class="metric-card">
					<div class="metric-content">
						<h3 class="metric-title">Total Customers</h3>
						<p class="metric-subtitle">Last 30 days</p>
					</div>
					<div class="metric-value customer">1,462</div>
				</div>

				<div class="metric-card">
					<div class="metric-content">
						<h3 class="metric-title">Pending Deliveries</h3>
						<p class="metric-subtitle">Last 30 days</p>
					</div>
					<div class="metric-value delivery">117</div>
				</div>
			</div>
		</div>

		<div class="table-container">
			<h2>Product List</h2>
			<div style="margin-bottom: 10px; text-align: right;">
				<a href="${pageContext.request.contextPath}/productList" class="btn btn-primary">View All Products</a>
				
			</div>
			<table>
				<thead>
					<tr>
						<th>ID</th>
						<th>Image</th>
						<th>Name</th>
						<th>Brand</th>
						<th>Category</th>
						<th>Price</th>
						<th>Stock</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty products}">
						<c:forEach var="product" items="${products}">
							<tr>
								<td>${product.productId}</td>
								<td>
									<c:choose>
										<c:when test="${product.image.startsWith('http')}">
											<img src="${product.image}" alt="${product.name}" style="width: 30px; height: 30px; object-fit: cover;">
										</c:when>
										<c:otherwise>
											<img src="${pageContext.request.contextPath}/${product.image}" alt="${product.name}" style="width: 30px; height: 30px; object-fit: cover;">
										</c:otherwise>
									</c:choose>
								</td>
								<td>${product.name}</td>
								<td>${product.brand}</td>
								<td>${product.category}</td>
								<td>$${product.price}</td>
								<td>${product.stockQuantity}</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty products}">
						<tr>
							<td colspan="7" style="text-align: center;">No products found</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
		
			<div class="table-container">
			<h2>Users List</h2>
			<div style="margin-bottom: 10px; text-align: right;">
				<a href="${pageContext.request.contextPath}/users" class="btn btn-primary" style="margin-left: 8px;">View All Users</a>
			</div>
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
					<c:if test="${not empty users}">
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
					</c:if>
					<c:if test="${empty users}">
						<tr>
							<td colspan="8" style="text-align: center;">No users found</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
		
	</div>
	
	
	<jsp:include page="footer.jsp" />
	
</body>
</html>