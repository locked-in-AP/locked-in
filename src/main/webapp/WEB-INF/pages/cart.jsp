<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Shopping Cart - Locked In</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/cart.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
<style>
    body {
        min-height: 100vh;
        display: flex;
        flex-direction: column;
    }

    .cart-wrapper {
        max-width: 1200px;
        margin: 0 auto;
        padding: 20px;
        flex: 1;
    }

    .page-title {
        font-size: 2rem;
        margin-bottom: 2rem;
        color: #1a1f2c;
    }

    .cart-grid {
        display: grid;
        grid-template-columns: 1fr 350px;
        gap: 2rem;
    }

    .cart-items {
        display: flex;
        flex-direction: column;
        gap: 1rem;
    }

    .cart-item {
        background: white;
        border-radius: 8px;
        padding: 1.5rem;
        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    }

    .item-details {
        display: flex;
        gap: 1.5rem;
        margin-bottom: 1rem;
    }

    .product-image-container {
        width: 120px;
        height: 120px;
    }

    .product-image {
        width: 100%;
        height: 100%;
        object-fit: cover;
        border-radius: 4px;
    }

    .product-info {
        flex: 1;
    }

    .product-header {
        display: flex;
        justify-content: space-between;
        align-items: flex-start;
        margin-bottom: 0.5rem;
    }

    .product-header h2 {
        margin: 0;
        font-size: 1.2rem;
        color: #1a1f2c;
    }

    .item-price {
        font-weight: bold;
        color: #1a1f2c;
    }

    .variant {
        margin: 0.25rem 0;
        color: #666;
        font-size: 0.9rem;
    }

    .item-controls {
        display: flex;
        justify-content: space-between;
        align-items: center;
        border-top: 1px solid #eee;
        padding-top: 1rem;
    }

    .quantity-selector {
        display: flex;
        align-items: center;
        gap: 0.5rem;
    }

    .qty-btn {
        background: #f8f9fa;
        border: 1px solid #dee2e6;
        border-radius: 4px;
        padding: 0.5rem 1rem;
        cursor: pointer;
        font-size: 1rem;
        transition: background-color 0.3s;
    }

    .qty-btn:hover:not(:disabled) {
        background: #e9ecef;
    }

    .qty-btn:disabled {
        background: #e9ecef;
        cursor: not-allowed;
    }

    .qty-input {
        width: 40px;
        text-align: center;
        font-weight: bold;
    }

    .remove-btn {
        background: none;
        border: none;
        color: #dc3545;
        cursor: pointer;
        display: flex;
        align-items: center;
        gap: 0.5rem;
        font-size: 0.9rem;
        transition: color 0.3s;
    }

    .remove-btn:hover {
        color: #c82333;
    }

    .order-summary {
        background: white;
        border-radius: 8px;
        padding: 1.5rem;
        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        height: fit-content;
        position: sticky;
        top: 20px;
    }

    .summary-title {
        font-size: 1.2rem;
        margin-bottom: 1.5rem;
        color: #1a1f2c;
    }

    .summary-details {
        display: flex;
        flex-direction: column;
        gap: 1rem;
        margin-bottom: 1.5rem;
    }

    .summary-row, .total-row {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .total-row {
        border-top: 1px solid #eee;
        padding-top: 1rem;
        font-weight: bold;
        font-size: 1.1rem;
    }

    .checkout-btn {
        width: 100%;
        background: #1a1f2c;
        color: white;
        border: none;
        border-radius: 4px;
        padding: 1rem;
        font-size: 1rem;
        cursor: pointer;
        transition: background-color 0.3s;
    }

    .checkout-btn:hover {
        background: #2a2f3c;
    }

    #notification {
        position: fixed;
        top: 20px;
        right: 20px;
        z-index: 1000;
        display: none;
        background-color: white;
        border-radius: 8px;
        box-shadow: 0 4px 12px rgba(0,0,0,0.15);
        min-width: 300px;
        max-width: 400px;
        animation: slideIn 0.3s ease-out;
    }

    @keyframes slideIn {
        from {
            transform: translateX(100%);
            opacity: 0;
        }
        to {
            transform: translateX(0);
            opacity: 1;
        }
    }

    .notification-content {
        display: flex;
        align-items: center;
        padding: 1rem;
        gap: 1rem;
    }

    .notification-body {
        flex: 1;
        font-size: 0.95rem;
        line-height: 1.4;
    }

    .notification-close {
        background: none;
        border: none;
        font-size: 1.5rem;
        cursor: pointer;
        padding: 0;
        line-height: 1;
        color: #666;
        transition: color 0.3s;
    }

    .notification-close:hover {
        color: #333;
    }

    .notification-success {
        border-left: 4px solid #28a745;
    }

    .notification-error {
        border-left: 4px solid #dc3545;
    }

    .info-message {
        background-color: #d1ecf1;
        color: #0c5460;
        padding: 1rem;
        border-radius: 8px;
        margin-bottom: 1rem;
        display: flex;
        align-items: center;
        gap: 1rem;
    }

    .info-message a {
        color: #0c5460;
        text-decoration: underline;
    }

    @media (max-width: 768px) {
        .cart-grid {
            grid-template-columns: 1fr;
        }

        .order-summary {
            position: static;
        }

        .item-details {
            flex-direction: column;
        }

        .product-image-container {
            width: 100%;
            height: 200px;
        }
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
	
	<div class="cart-wrapper">
		<h2 class="page-title">Shopping Cart</h2>
		
		<c:choose>
			<c:when test="${empty cartItems}">
				<div class="info-message">
					<i class="fas fa-info-circle"></i>
					<div>
						Your cart is empty. <a href="${pageContext.request.contextPath}/products">Continue shopping</a>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="cart-grid">
					<div class="cart-items">
						<c:forEach items="${cartItems}" var="item">
							<div class="cart-item product-box" data-product-id="${item.productId}">
								<div class="item-details">
									<div class="product-image-container">
										<img src="${item.product.image}" alt="${item.product.name}" class="product-image">
									</div>
									<div class="product-info">
										<div class="product-header">
											<h2>${item.product.name}</h2>
											<div class="item-price">
												<fmt:formatNumber value="${item.product.price}" type="currency" currencySymbol="$"/>
											</div>
										</div>
										<p class="variant">${item.product.brand}</p>
										<p class="variant">Stock: ${item.product.stockQuantity}</p>
									</div>
								</div>
								<div class="item-controls">
									<div class="quantity-selector">
										<form action="${pageContext.request.contextPath}/cart" method="post" style="display:inline;">
											<input type="hidden" name="action" value="update">
											<input type="hidden" name="productId" value="${item.productId}">
											<button type="submit" name="quantity" value="${item.quantity - 1}" class="qty-btn" ${item.quantity <= 1 ? 'disabled' : ''}>−</button>
										</form>
										<span class="qty-input">${item.quantity}</span>
										<form action="${pageContext.request.contextPath}/cart" method="post" style="display:inline;">
											<input type="hidden" name="action" value="update">
											<input type="hidden" name="productId" value="${item.productId}">
											<button type="submit" name="quantity" value="${item.quantity + 1}" class="qty-btn">+</button>
										</form>
									</div>
									<div class="item-actions">
										<form action="${pageContext.request.contextPath}/cart" method="post" style="display:inline;">
											<input type="hidden" name="action" value="remove">
											<input type="hidden" name="productId" value="${item.productId}">
											<button type="submit" class="remove-btn">
												<i class="fas fa-trash"></i> Remove
											</button>
										</form>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
					
					<div class="order-summary summary-box">
						<h3 class="summary-title">Order Summary</h3>
						<div class="summary-details">
							<div class="summary-row">
								<span>Subtotal</span>
								<span><fmt:formatNumber value="${total}" type="currency" currencySymbol="$"/></span>
							</div>
							<div class="summary-row">
								<span>Shipping</span>
								<span>Free</span>
							</div>
							<div class="summary-row">
								<span>Tax</span>
								<span><fmt:formatNumber value="${total * 0.1}" type="currency" currencySymbol="$"/></span>
							</div>
						</div>
						<div class="total-row">
							<span>Total</span>
							<span><fmt:formatNumber value="${total * 1.1}" type="currency" currencySymbol="$"/></span>
						</div>
						<form action="${pageContext.request.contextPath}/checkout" method="post" onsubmit="return handleCheckout(event)">
							<button type="submit" class="checkout-btn">Proceed to Checkout</button>
						</form>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
	</div>

	<script>
		// Show the message if it exists in the request
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

		function handleCheckout(event) {
			event.preventDefault();
			const form = event.target;
			
			// Show success message
			showMessage("Order placed successfully!", false);
			
			// Submit the form after a short delay
			setTimeout(() => {
				form.submit();
			}, 1000);
			
			return false;
		}
	</script>

	<jsp:include page="footer.jsp" />
</body>
</html>