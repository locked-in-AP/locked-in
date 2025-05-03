<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Locked IN - ${product.name}</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/item.css">
</head>
<body>
	<jsp:include page="header.jsp" />
	<div class="product-container">
		<div class="left-column">
			<div class="image-gallery">
				<div class="gallery-grid">
					<div class="gallery-item">
						<img src="${product.image}" class="gallery-image" alt="${product.name}">
					</div>
					<div class="gallery-item">
						<img src="${product.image}" class="gallery-image" alt="${product.name}">
					</div>
					<div class="gallery-item main-image">
						<img src="${product.image}" class="gallery-image" alt="${product.name}">
					</div>
					<div class="gallery-item">
						<img src="${product.image}" class="gallery-image" alt="${product.name}">
					</div>
					<div class="gallery-item">
						<img src="${product.image}" class="gallery-image" alt="${product.name}">
					</div>
				</div>
			</div>
		</div>

		<div class="product-details">
			<h1 class="product-title">${product.name}</h1>
			<div class="product-price">$${product.price}</div>

			<div class="quantity-selector">
				<p>Quantity</p>
				<div class="quantity-controls">
					<button class="quantity-btn">-</button>
					<input type="number" class="quantity-input" value="1" min="1" max="${product.stockQuantity}" id="quantity" readonly>
					<button class="quantity-btn">+</button>
				</div>
			</div>

			<button class="add-to-cart">ADD TO CART</button>

			<div class="product-description">
				<p>${product.description}</p>
				<ul style="margin-top: 15px; list-style: inside;">
					<li>Brand: ${product.brand}</li>
					<li>Category: ${product.category}</li>
					<li>Weight: ${product.weight}</li>
					<li>Dimensions: ${product.dimensions}</li>
					<li>Stock Available: ${product.stockQuantity} units</li>
				</ul>
			</div>
		</div>

		<div class="reviews-section">
			<h2>Customer Reviews</h2>

			<div class="review-form">
				<h3>Write a Review</h3>
				<div class="rating-stars" id="ratingStars">
					<span class="star" data-rating="1">★</span>
					<span class="star" data-rating="2">★</span>
					<span class="star" data-rating="3">★</span>
					<span class="star" data-rating="4">★</span>
					<span class="star" data-rating="5">★</span>
				</div>
				<textarea class="review-textarea" placeholder="Write your review..." id="reviewText"></textarea>
				<button class="submit-review">Submit Review</button>
			</div>

			<div class="review-list" id="reviewList">
				<div class="review-card">
					<div class="review-header">
						<div class="review-rating">
							<span style="color: #ffd700">★★★★★</span>
						</div>
						<span class="review-author">John D.</span>
						<span class="review-date">· August 15, 2023</span>
					</div>
					<p>Best product I've ever owned! Perfect quality and great value.</p>
				</div>
				<div class="review-card">
					<div class="review-header">
						<div class="review-rating">
							<span style="color: #ffd700">★★★★☆</span>
						</div>
						<span class="review-author">Jane S.</span>
						<span class="review-date">· September 01, 2023</span>
					</div>
					<p>Very satisfied with the purchase. Would recommend to others.</p>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="footer.jsp" />
</body>
</html>