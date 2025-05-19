<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/admindashboard.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
	<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

<meta charset="UTF-8">
<title>Product List - LockedIN</title>
</head>
<body>
	<jsp:include page="header.jsp" />
	<c:if test="${not empty success}">
		<p class="success-msg">${success}</p>
	</c:if>

	<div class="sidebar">
		<ul class="nav">
			<li><a href="${pageContext.request.contextPath}/admindashboard"><span class="icon"><i class="fas fa-home"></i></span> Dashboard</a></li>
			<li><a href="${pageContext.request.contextPath}/users"><span class="icon"><i class="fas fa-user"></i></span>Users</a></li>
			<li><a href="${pageContext.request.contextPath}/admin/orders"><span class="icon"><i class="fas fa-shopping-cart"></i></span> Orders</a></li>
			<li><a href="${pageContext.request.contextPath}/addProduct"><span class="icon"><i class="fas fa-pen"></i></span> Add Product</a></li>
			<li><a href="${pageContext.request.contextPath}/deleteProduct"><span class="icon"><i class="fas fa-trash"></i></span> Delete Product</a></li>
			
		</ul>
	</div>
	<div class="main-content">
		<div class="admin-header">
			<h1 class="section-title">All Products</h1>
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
			<h2>Product List</h2>
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
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
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
							<td>
									<a href="${pageContext.request.contextPath}/updateProduct?id=${product.productId}" class="edit-btn">
										<i class="fas fa-edit"></i> Edit
									</a>
								</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div style="margin-top: 16px; text-align: right;">
				<a href="${pageContext.request.contextPath}/deleteProduct" class="btn btn-primary">Delete Products </a>
				<a href="${pageContext.request.contextPath}/admindashboard" class="btn btn-primary">Back to Dashboard</a>
			</div>
		</div>
	</div>
</body>
</html> 