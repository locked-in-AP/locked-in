<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Add new products in the LockedIN admin dashboard">
<meta name="keywords" content="add product, admin, locked in">
<title>Add Product - LockedIN</title>
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
		<div class="admin-header">
			<h1 class="section-title">Add New Product</h1>
			<div class="right-section">
				<p>Welcome, ${sessionScope.name}</p>
				<a href="${pageContext.request.contextPath}/logout" class="logout-btn">
					<i class="fas fa-sign-out-alt"></i> Logout
				</a>
				
				
					<a href="${pageContext.request.contextPath}/adminProfile"
					class="logout-btn"> <i class="fas fa-user"></i> Profile
				</a>
				
				
				<img src="${pageContext.request.contextPath}/resources/images/system/userpfp.png"
					alt="Admin Profile" class="admin-pfp" height="40" width="40">
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
					<input type="text" id="name" name="name" 
						class="${not empty nameError ? 'error-border' : ''}"
						value="${requestScope.name != null ? requestScope.name : ''}"
						placeholder="Enter product name">
					<c:if test="${not empty nameError}">
						<p class="field-error">${nameError}</p>
					</c:if>
				</div>

				<div class="form-group">
					<label for="description">Description</label>
					<textarea id="description" name="description" rows="4" 
						class="${not empty descriptionError ? 'error-border' : ''}"
						placeholder="Enter product description">${description != null ? description : ''}</textarea>
					<c:if test="${not empty descriptionError}">
						<p class="field-error">${descriptionError}</p>
					</c:if>
				</div>

				<div class="form-group">
					<label for="brand">Brand</label>
					<input type="text" id="brand" name="brand" 
						class="${not empty brandError ? 'error-border' : ''}"
						value="${brand != null ? brand : ''}"
						placeholder="Enter brand name">
					<c:if test="${not empty brandError}">
						<p class="field-error">${brandError}</p>
					</c:if>
				</div>

				<div class="form-group">
					<label for="category">Category</label>
					<select id="category" name="category" 
						class="${not empty categoryError ? 'error-border' : ''}">
						<option value="" disabled ${category == null ? 'selected' : ''}>Select category</option>
						<option value="equipment" ${category == 'equipment' ? 'selected' : ''}>Equipment</option>
						<option value="supplement" ${category == 'supplement' ? 'selected' : ''}>Supplement</option>
						<option value="merchandise" ${category == 'merchandise' ? 'selected' : ''}>Merchandise</option>
					</select>
					<c:if test="${not empty categoryError}">
						<p class="field-error">${categoryError}</p>
					</c:if>
				</div>

				<div class="form-group">
					<label for="tags">Tags (comma separated)</label>
					<input type="text" id="tags" name="tags" 
						class="${not empty tagsError ? 'error-border' : ''}"
						value="${tags != null ? tags : ''}"
						placeholder="e.g. protein, whey, isolate">
					<c:if test="${not empty tagsError}">
						<p class="field-error">${tagsError}</p>
					</c:if>
				</div>

				<div class="form-group">
					<label for="price">Price ($)</label>
					<input type="number" id="price" name="price" step="0.01" 
						class="${not empty priceError ? 'error-border' : ''}"
						value="${price != null ? price : ''}"
						placeholder="Enter price in USD">
					<c:if test="${not empty priceError}">
						<p class="field-error">${priceError}</p>
					</c:if>
				</div>

				<div class="form-group">
					<label for="stockQuantity">Stock Quantity</label>
					<input type="number" id="stockQuantity" name="stockQuantity" 
						class="${not empty stockQuantityError ? 'error-border' : ''}"
						value="${stockQuantity != null ? stockQuantity : ''}"
						placeholder="Enter stock quantity">
					<c:if test="${not empty stockQuantityError}">
						<p class="field-error">${stockQuantityError}</p>
					</c:if>
				</div>

				<div class="form-group">
					<label for="weight">Weight (kg)</label>
					<input type="number" id="weight" name="weight" step="0.01" 
						class="${not empty weightError ? 'error-border' : ''}"
						value="${weight != null ? weight : ''}"
						placeholder="Enter weight in kg">
					<c:if test="${not empty weightError}">
						<p class="field-error">${weightError}</p>
					</c:if>
				</div>

				<div class="form-group">
					<label for="image">Product Image URL</label>
					<input type="text" id="image" name="image" 
						class="${not empty imageError ? 'error-border' : ''}"
						value="${image != null ? image : ''}"
						placeholder="Enter image URL">
					<c:if test="${not empty imageError}">
						<p class="field-error">${imageError}</p>
					</c:if>
				</div>

				<div class="form-group">
					<label for="dimensions">Dimensions (e.g., 10x10x10)</label>
					<input type="text" id="dimensions" name="dimensions" 
						class="${not empty dimensionsError ? 'error-border' : ''}"
						value="${dimensions != null ? dimensions : ''}"
						placeholder="e.g., 10x10x10">
					<c:if test="${not empty dimensionsError}">
						<p class="field-error">${dimensionsError}</p>
					</c:if>
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