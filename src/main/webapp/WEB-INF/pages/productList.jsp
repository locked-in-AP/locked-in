<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Manage products in the LockedIN admin dashboard">
<meta name="keywords" content="product management, admin, locked in">
<title>Product List - LockedIN</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/admindashboard.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
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
		<c:if test="${not empty success}">
			<p class="success-msg">${success}</p>
		</c:if>
		
		<div class="admin-header">
			<h1 class="section-title">All Products</h1>
			<div class="right-section">
				<p>Welcome, ${sessionScope.name}</p>
				<a href="${pageContext.request.contextPath}/logout" class="logout-btn">
					<i class="fas fa-sign-out-alt"></i> Logout
				</a>
				<img src="${pageContext.request.contextPath}/resources/images/system/userpfp.png"
					alt="Admin Profile" class="admin-pfp" width="40" height="40">
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
										<img src="${product.image}" alt="${product.name}" width="30" height="30" class="product-thumbnail">
									</c:when>
									<c:otherwise>
										<img src="${pageContext.request.contextPath}/${product.image}" alt="${product.name}" width="30" height="30" class="product-thumbnail">
									</c:otherwise>
								</c:choose>
							</td>
							<td>${product.name}</td>
							<td>${product.brand}</td>
							<td>${product.category}</td>
							<td>$${product.price}</td>
							<td>${product.stockQuantity}</td>
							<td class="action-buttons">
									<a href="${pageContext.request.contextPath}/updateProduct?id=${product.productId}" class="edit-btn">
										<i class="fas fa-edit"></i> Edit
									</a>
									
									<form action="${pageContext.request.contextPath}/deleteProduct" method="post" class="inline-form" onsubmit="return confirm('Are you sure you want to delete this product?');">
										<input type="hidden" name="productId" value="${product.productId}">
										<button type="submit" class="edit-btn remove-btn">
											<i class="fas fa-trash"></i> Remove
										</button>
									</form>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="action-bar">
				<a href="${pageContext.request.contextPath}/deleteProduct" class="btn btn-primary">Delete Products </a>
				<a href="${pageContext.request.contextPath}/admindashboard" class="btn btn-primary">Back to Dashboard</a>
			</div>
		</div>
	</div>
</body>
</html> 