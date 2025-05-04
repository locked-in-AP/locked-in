<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Locked IN - ${product.name}</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/item.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
<style>
	#messageToast {
		position: fixed;
		top: 20px;
		right: 20px;
		z-index: 1000;
		display: none;
	}
	.quantity-controls {
		display: flex;
		align-items: center;
		gap: 10px;
	}
	.quantity-btn {
		padding: 5px 10px;
		border: 1px solid #dee2e6;
		background: none;
		cursor: pointer;
	}
	.quantity-input {
		width: 60px;
		text-align: center;
		border: 1px solid #dee2e6;
		padding: 5px;
	}
	.add-to-cart {
		width: 100%;
		padding: 15px;
		background-color: #1a1f2c;
		color: white;
		border: none;
		border-radius: 5px;
		margin: 20px 0;
		cursor: pointer;
		transition: background-color 0.3s;
	}
	.add-to-cart:hover {
		background-color: #2a2f3c;
	}
	.add-to-cart:disabled {
		background-color: #ccc;
		cursor: not-allowed;
	}
	.stock-status {
		margin-top: 10px;
		font-size: 14px;
	}
	.in-stock {
		color: #28a745;
	}
	.low-stock {
		color: #ffc107;
	}
	.out-of-stock {
		color: #dc3545;
	}
</style>
</head>
<body>
	<jsp:include page="header.jsp" />
	
	<!-- Toast for messages -->
	<div id="messageToast" class="toast align-items-center" role="alert" aria-live="assertive" aria-atomic="true">
		<div class="d-flex">
			<div class="toast-body"></div>
			<button type="button" class="btn-close me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
		</div>
	</div>
	
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
					<button class="quantity-btn" onclick="updateQuantity(-1)">-</button>
					<input type="number" class="quantity-input" value="1" min="1" max="${product.stockQuantity}" id="quantity" readonly>
					<button class="quantity-btn" onclick="updateQuantity(1)">+</button>
				</div>
				<div class="stock-status ${product.stockQuantity > 10 ? 'in-stock' : (product.stockQuantity > 0 ? 'low-stock' : 'out-of-stock')}">
					<c:choose>
						<c:when test="${product.stockQuantity > 10}">
							<i class="fas fa-check-circle"></i> In Stock
						</c:when>
						<c:when test="${product.stockQuantity > 0}">
							<i class="fas fa-exclamation-circle"></i> Low Stock - Only ${product.stockQuantity} left
						</c:when>
						<c:otherwise>
							<i class="fas fa-times-circle"></i> Out of Stock
						</c:otherwise>
					</c:choose>
				</div>
			</div>

			<button class="add-to-cart" onclick="addToCart()" ${product.stockQuantity == 0 ? 'disabled' : ''}>
				${product.stockQuantity == 0 ? 'OUT OF STOCK' : 'ADD TO CART'}
			</button>

			<div class="product-description">
				<p>${product.description}</p>
				<ul style="margin-top: 15px; list-style: inside;">
					<li>Brand: ${product.brand}</li>
					<li>Category: ${product.category}</li>
					<li>Weight: ${product.weight}</li>
					<li>Dimensions: ${product.dimensions}</li>
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
	
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	<script>
		const toast = new bootstrap.Toast(document.getElementById('messageToast'));
		const quantityInput = document.getElementById('quantity');
		const maxStock = parseInt('${product.stockQuantity}');
		const productId = parseInt('${product.productId}');
		
		function showMessage(message, isError = false) {
			const toastElement = document.getElementById('messageToast');
			const toastBody = toastElement.querySelector('.toast-body');
			toastBody.textContent = message;
			toastElement.classList.remove('bg-success', 'bg-danger', 'text-white');
			toastElement.classList.add(isError ? 'bg-danger' : 'bg-success', 'text-white');
			toast.show();
		}
		
		function updateQuantity(change) {
			const currentValue = parseInt(quantityInput.value);
			const newValue = currentValue + change;
			
			if (newValue >= 1 && newValue <= maxStock) {
				quantityInput.value = newValue;
			}
		}
		
		function addToCart() {
			const quantity = parseInt(quantityInput.value);
			
			const formData = new URLSearchParams();
			formData.append('action', 'add');
			formData.append('productId', productId);
			formData.append('quantity', quantity);
			
			fetch('${pageContext.request.contextPath}/cart', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/x-www-form-urlencoded'
				},
				body: formData
			})
			.then(async response => {
				const data = await response.json();
				if (!response.ok) {
					if (response.status === 401) {
						window.location.href = '${pageContext.request.contextPath}/login';
						throw new Error('Please log in to continue');
					}
					showMessage(data.error || 'Server error', true);
					throw new Error(data.error || 'Server error');
				}
				return data;
			})
			.then(data => {
				showMessage(data.message, !data.success);
				if (data.success) {
					document.querySelectorAll('.cart-count').forEach(el => {
						el.textContent = data.cartSize;
					});
					sessionStorage.setItem('cartSize', data.cartSize);
				}
			})
			.catch(error => {
				console.error('Error:', error);
				if (error.message !== 'Please log in to continue') {
					showMessage(error.message || 'An error occurred while adding to cart', true);
				}
			});
		}
	</script>
</body>
</html>