<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Product - LockedIN</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/admindashboard.css" />
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
			<li><a href="${pageContext.request.contextPath}/deleteProduct"><span class="icon"><i class="fas fa-trash"></i></span> Delete Product</a></li>
		</ul>
	</div>

	<div class="main-content">
		<div class="admin-header">
			<h1 class="section-title">Add New Product</h1>
			<div class="right-section">
				<p>Welcome, Admin</p>
				<a href="${pageContext.request.contextPath}/logout" class="logout-btn">
					<i class="fas fa-sign-out-alt"></i> Logout
				</a>
				<img src="${pageContext.request.contextPath}/resources/images/system/userpfp.png"
					alt="Admin Profile" class="admin-pfp">
			</div>
		</div>

		<div class="form-container">
		 <c:if test="${not empty success}">
            <p class="alert success-msg">${success}</p>
        </c:if>
        
        <c:if test="${not empty error}">
            <p class="alert error-msg">${error}</p>
        </c:if>
			<form action="${pageContext.request.contextPath}/addProduct" method="post" enctype="multipart/form-data" class="product-form">
				<div class="form-group">
					<label for="name">Product Name</label>
					<input type="text" id="name" name="name" required>
				</div>

				<div class="form-group">
					<label for="description">Description</label>
					<textarea id="description" name="description" rows="4" required></textarea>
				</div>

				<div class="form-group">
					<label for="brand">Brand</label>
					<input type="text" id="brand" name="brand" required>
				</div>

				<div class="form-group">
					<label for="category">Category</label>
					<select id="category" name="category" required>
						<option value="equipment">Equipment</option>
						<option value="supplement">Supplement</option>
						<option value="merchandise">Merchandise</option>
					</select>
				</div>

				<div class="form-group">
					<label for="tags">Tags (comma separated)</label>
					<input type="text" id="tags" name="tags" required>
				</div>

				<div class="form-group">
					<label for="price">Price ($)</label>
					<input type="number" id="price" name="price" step="0.01" min="0" required>
				</div>

				<div class="form-group">
					<label for="stockQuantity">Stock Quantity</label>
					<input type="number" id="stockQuantity" name="stockQuantity" min="0" required>
				</div>

				<div class="form-group">
					<label for="weight">Weight (kg)</label>
					<input type="number" id="weight" name="weight" step="0.01" min="0" required>
				</div>

				 <div class="form-group">
                <label for="image">Product Image</label>
                <div class="file-input-container">
                    <input type="file" id="image" name="image" accept="image/*" required>
                    
                </div>
            </div>

				<div class="form-group">
					<label for="dimensions">Dimensions (e.g., 10x10x10)</label>
					<input type="text" id="dimensions" name="dimensions" required>
				</div>

				<div class="form-actions">
					<button type="submit" class="submit-btn">Add Product</button>
					<a href="${pageContext.request.contextPath}/admindashboard" class="cancel-btn">Cancel</a>
				</div>
			</form>
		</div>
	</div>
</body>
</html> 