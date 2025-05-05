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
	#notification {
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
    .notification-content {
        display: flex;
        align-items: center;
        padding: 10px 15px;
    }
    .notification-body {
        flex-grow: 1;
        padding: 5px 0;
    }
    .notification-close {
        background: none;
        border: none;
        font-size: 1.5rem;
        cursor: pointer;
        padding: 0 5px;
        line-height: 1;
    }
    .notification-success {
        background-color: #28a745;
        color: white;
    }
    .notification-error {
        background-color: #dc3545;
        color: white;
    }
	.quantity-controls {
		display: flex;
		align-items: center;
		gap: 10px;
	}
	.quantity-btn {
		background-color: #f8f9fa;
		border: 1px solid #dee2e6;
		border-radius: 4px;
		padding: 5px 10px;
		cursor: pointer;
		font-size: 1rem;
		transition: background-color 0.3s;
	}
	.quantity-btn:hover {
		background-color: #e9ecef;
	}
	.quantity-btn:disabled {
		background-color: #e9ecef;
		cursor: not-allowed;
	}
	.quantity-input {
		width: 60px;
		text-align: center;
		border: 1px solid #dee2e6;
		padding: 5px;
	}
	.add-to-cart-btn {
		background-color: #1a1f2c;
		color: white;
		border: none;
		border-radius: 4px;
		padding: 10px 20px;
		cursor: pointer;
		font-size: 1rem;
		transition: background-color 0.3s;
	}
	.add-to-cart-btn:hover {
		background-color: #2a2f3c;
	}
	.add-to-cart-btn:disabled {
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
	
	<!-- Notification for messages -->
	<div id="notification" class="notification">
		<div class="notification-content">
			<div class="notification-body"></div>
			<button type="button" class="notification-close" onclick="closeNotification()">×</button>
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
	
				<button type="submit" class="add-to-cart-btn" ${product.stockQuantity == 0 ? 'disabled' : ''}>
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
		
		function showMessage(message, isError) {
			const notification = document.getElementById('notification');
			const notificationBody = notification.querySelector('.notification-body');
			notificationBody.textContent = message;
			notification.className = 'notification';
			notification.classList.add(isError ? 'notification-error' : 'notification-success');
			notification.style.display = 'block';
			
			// Auto-hide after 5 seconds
			setTimeout(() => {
				notification.style.display = 'none';
			}, 5000);
		}
		
		function closeNotification() {
			const notification = document.getElementById('notification');
			notification.style.display = 'none';
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