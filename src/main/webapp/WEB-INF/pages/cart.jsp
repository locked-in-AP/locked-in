<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Shopping Cart - Locked In</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
<style>
	.cart-item {
		border-bottom: 1px solid #dee2e6;
		padding: 20px 0;
	}
	.cart-item:last-child {
		border-bottom: none;
	}
	.product-image {
		max-width: 100px;
		height: auto;
	}
	.quantity-control {
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
	.cart-summary {
		background-color: #f8f9fa;
		padding: 20px;
		border-radius: 5px;
	}
	#messageToast {
		position: fixed;
		top: 20px;
		right: 20px;
		z-index: 1000;
		display: none;
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
	
	<div class="container my-5">
		<h2 class="mb-4">Shopping Cart</h2>
		
		<c:choose>
			<c:when test="${empty cartItems}">
				<div class="alert alert-info">
					Your cart is empty. <a href="${pageContext.request.contextPath}/products">Continue shopping</a>
				</div>
			</c:when>
			<c:otherwise>
				<div class="row">
					<div class="col-md-8">
						<c:forEach items="${cartItems}" var="item">
							<div class="cart-item" data-product-id="${item.productId}">
								<div class="row align-items-center">
									<div class="col-md-2">
										<img src="${item.product.image}" alt="${item.product.name}" class="product-image">
									</div>
									<div class="col-md-4">
										<h5>${item.product.name}</h5>
										<p class="text-muted">${item.product.brand}</p>
										<p class="text-muted">Stock: ${item.product.stockQuantity}</p>
									</div>
									<div class="col-md-2">
										<fmt:formatNumber value="${item.product.price}" type="currency" currencySymbol="$"/>
									</div>
									<div class="col-md-2">
										<div class="quantity-control">
											<form action="${pageContext.request.contextPath}/cart" method="post" style="display:inline;">
												<input type="hidden" name="action" value="update">
												<input type="hidden" name="productId" value="${item.productId}">
												<button type="submit" name="quantity" value="${item.quantity - 1}" class="quantity-btn" ${item.quantity <= 1 ? 'disabled' : ''}>-</button>
											</form>
											<span>${item.quantity}</span>
											<form action="${pageContext.request.contextPath}/cart" method="post" style="display:inline;">
												<input type="hidden" name="action" value="update">
												<input type="hidden" name="productId" value="${item.productId}">
												<button type="submit" name="quantity" value="${item.quantity + 1}" class="quantity-btn">+</button>
											</form>
										</div>
									</div>
									<div class="col-md-2">
										<form action="${pageContext.request.contextPath}/cart" method="post" style="display:inline;">
											<input type="hidden" name="action" value="remove">
											<input type="hidden" name="productId" value="${item.productId}">
											<button type="submit" class="btn btn-danger btn-sm">
												<i class="fas fa-trash"></i>
											</button>
										</form>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
					
					<div class="col-md-4">
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
				</div>
			</c:otherwise>
		</c:choose>
	</div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	<script>
		// Show toast message if there's a message in the URL
		const urlParams = new URLSearchParams(window.location.search);
		const message = urlParams.get('message');
		if (message) {
			showMessage(message, false);
		}

		function showMessage(message, isError) {
			const toast = document.getElementById('messageToast');
			const toastBody = toast.querySelector('.toast-body');
			toastBody.textContent = message;
			toast.classList.add(isError ? 'bg-danger' : 'bg-success');
			toast.classList.add('text-white');
			const bsToast = new bootstrap.Toast(toast);
			bsToast.show();
		}
	</script>

	<jsp:include page="footer.jsp" />
</body>
</html>