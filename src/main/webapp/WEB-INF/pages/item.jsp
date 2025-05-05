<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Locked IN - ${product.name}</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/item.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
<style>
	#messageToast {
		position: fixed;
		top: 20px;
		right: 20px;
		z-index: 1000;
		display: none;
        background-color: white;
        border-radius: 4px;
        box-shadow: 0 4px 12px rgba(0,0,0,0.15);
        min-width: 250px;
        max-width: 350px;
	}
    .toast-content {
        display: flex;
        align-items: center;
        padding: 10px 15px;
    }
    .toast-body {
        flex-grow: 1;
        padding: 5px 0;
    }
    .toast-close {
        background: none;
        border: none;
        font-size: 1.5rem;
        cursor: pointer;
        padding: 0 5px;
        line-height: 1;
    }
    .toast-success {
        background-color: #28a745;
        color: white;
    }
    .toast-error {
        background-color: #dc3545;
        color: white;
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
	<div id="messageToast" class="toast">
		<div class="toast-content">
			<div class="toast-body"></div>
			<button type="button" class="toast-close" onclick="closeToast()">×</button>
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

			<form action="${pageContext.request.contextPath}/item" method="post">
				<input type="hidden" name="action" value="addToCart">
				<input type="hidden" name="productId" value="${product.productId}">
				
				<div class="quantity-selector">
					<p>Quantity</p>
					<div class="quantity-controls">
						<button type="button" class="quantity-btn" onclick="updateQuantity(-1)">-</button>
						<input type="number" class="quantity-input" name="quantity" value="1" min="1" max="${product.stockQuantity}" id="quantity" readonly>
						<button type="button" class="quantity-btn" onclick="updateQuantity(1)">+</button>
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
	
				<button type="submit" class="add-to-cart" ${product.stockQuantity == 0 ? 'disabled' : ''}>
					${product.stockQuantity == 0 ? 'OUT OF STOCK' : 'ADD TO CART'}
				</button>
			</form>

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
	
	<script>
		const quantityInput = document.getElementById('quantity');
		const maxStock = parseInt('${product.stockQuantity}');
		
		// Check if there's a message parameter
		const message = "${message}";
		if (message && message.trim() !== "") {
		    showMessage(message, false);
		}
		
		function showMessage(message, isError = false) {
			const toastElement = document.getElementById('messageToast');
			const toastBody = toastElement.querySelector('.toast-body');
			toastBody.textContent = message;
			toastElement.className = 'toast';
			toastElement.classList.add(isError ? 'toast-error' : 'toast-success');
			toastElement.style.display = 'block';
			
			// Auto-hide after 5 seconds
			setTimeout(() => {
				toastElement.style.display = 'none';
			}, 5000);
		}
		
		function closeToast() {
			const toastElement = document.getElementById('messageToast');
			toastElement.style.display = 'none';
		}
		
		function updateQuantity(change) {
			const currentValue = parseInt(quantityInput.value);
			const newValue = currentValue + change;
			
			if (newValue >= 1 && newValue <= maxStock) {
				quantityInput.value = newValue;
			}
		}
	</script>
</body>
</html>