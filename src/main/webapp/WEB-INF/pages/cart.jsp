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
    .alert-info {
        padding: 1rem;
        background-color: #d1ecf1;
        color: #0c5460;
        border-radius: 4px;
        margin-bottom: 1rem;
    }
    .remove-button {
        background-color: #dc3545;
        color: white;
        border: none;
        border-radius: 4px;
        padding: 5px 10px;
        cursor: pointer;
    }
    .remove-button:hover {
        background-color: #c82333;
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
	
	<div class="container">
		<h2 class="page-title">Shopping Cart</h2>
		
		<c:choose>
			<c:when test="${empty cartItems}">
				<div class="alert-info">
					Your cart is empty. <a href="${pageContext.request.contextPath}/products">Continue shopping</a>
				</div>
			</c:when>
			<c:otherwise>
				<div class="cart-grid">
					<div class="cart-items">
						<c:forEach items="${cartItems}" var="item">
							<div class="cart-item card" data-product-id="${item.productId}">
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
					
					<div class="order-summary card">
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
						<form action="${pageContext.request.contextPath}/checkout" method="post">
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
			const toast = document.getElementById('messageToast');
			const toastBody = toast.querySelector('.toast-body');
			toastBody.textContent = message;
			toast.className = 'toast';
			toast.classList.add(isError ? 'toast-error' : 'toast-success');
			toast.style.display = 'block';
			
			// Auto-hide after 5 seconds
			setTimeout(() => {
				toast.style.display = 'none';
			}, 5000);
		}
		
		function closeToast() {
			const toast = document.getElementById('messageToast');
			toast.style.display = 'none';
		}
	</script>

	<jsp:include page="footer.jsp" />
</body>
</html>