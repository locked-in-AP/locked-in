<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Update product details in the LockedIN admin dashboard">
    <meta name="keywords" content="product update, admin, locked in">
    <title>Update Product - LockedIN</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admindashboard.css" />
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
        <div class="admin-header">
            <h1 class="section-title">Update Product</h1>
            <div class="right-section">
                <p>Welcome, ${sessionScope.name}</p>
                <a href="${pageContext.request.contextPath}/logout" class="logout-btn">
                    <i class="fas fa-sign-out-alt"></i> Logout
                </a>
                <img src="${pageContext.request.contextPath}/resources/images/system/userpfp.png"
                    alt="Admin Profile" class="admin-pfp" width="40" height="40">
            </div>
        </div>

        <div class="form-container">
            <c:if test="${not empty success}">
                <p class="alert success-msg">${success}</p>
            </c:if>
            <c:if test="${not empty error}">
                <p class="alert error-msg">${error}</p>
            </c:if>
            <form action="${pageContext.request.contextPath}/updateProduct" method="post" enctype="multipart/form-data" class="product-form">
                <div class="form-group full-width">
                    <label for="productId">Product ID</label>
                    <input type="text" id="productId" name="productId" value="${product.productId}" readonly>
                </div>
                
                <div class="form-group">
					<label for="name">Product Name</label>
					<input type="text" id="name" name="name" value="${product.name}" >
				</div>

				<div class="form-group">
					<label for="description">Description</label>
					<textarea id="description" name="description" rows="4">${product.description}</textarea>
				</div>

				<div class="form-group">
					<label for="brand">Brand</label>
					<input type="text" id="brand" name="brand" value="${product.brand}">
				</div>

				<div class="form-group">
					<label for="category">Category</label>
					<select id="category" name="category">
						<option value="equipment" ${product.category == 'equipment' ? 'selected' : ''}>Equipment</option>
                        <option value="supplement" ${product.category == 'supplement' ? 'selected' : ''}>Supplement</option>
                        <option value="merchandise" ${product.category == 'merchandise' ? 'selected' : ''}>Merchandise</option>					
                    </select>
				</div>

				<div class="form-group">
					<label for="tags">Tags (comma separated)</label>
					<input type="text" id="tags" name="tags" value="${product.tags}">
				</div>

				<div class="form-group">
					<label for="price">Price ($)</label>
					<input type="number" id="price" name="price" step="0.01" min="0" value="${product.price}">
				</div>

				<div class="form-group">
					<label for="stockQuantity">Stock Quantity</label>
					<input type="number" id="stockQuantity" name="stockQuantity" min="0" value="${product.stockQuantity}">
				</div>

				<div class="form-group">
					<label for="weight">Weight (kg)</label>
					<input type="number" id="weight" name="weight" step="0.01" min="0" value="${product.weight}">
				</div>

				<div class="form-group">
                    <label for="image">Product Image URL</label>
                    <input type="text" id="image" name="image" value="${product.image}" placeholder="Enter image URL">
                </div>
                
                <div class="form-group">
                    <label for="imageFile">Upload New Image</label>
                    <input type="file" id="imageFile" name="imageFile" accept="image/*">
                    <p class="field-hint">You can upload a new image or use the URL above</p>
                </div>

				<div class="form-group">
					<label for="dimensions">Dimensions (e.g., 10x10x10)</label>
					<input type="text" id="dimensions" name="dimensions" value="${product.dimensions}">
				</div>

                <div class="form-actions">
                    <button type="submit" class="submit-btn">Update Product</button>
                    <a href="${pageContext.request.contextPath}/admindashboard" class="cancel-btn">Cancel</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html> 