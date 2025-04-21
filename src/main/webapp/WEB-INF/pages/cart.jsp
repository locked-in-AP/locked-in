<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Your Cart</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/cart.css" />
</head>
<body>
	<jsp:include page="header.jsp" />
	<div class="container">
		<h1 class="page-title">Your Cart</h1>

		<div class="cart-grid">
			<div class="cart-items">
				<div class="cart-item card">
					<div class="item-details">
						<img
							src="${pageContext.request.contextPath}/resources/images/system/sample.png"
							class="product-image" alt="Vibes Socks 211">
						<div class="product-info">
							<div class="product-header">
								<h2>Vibes Bass Buds 211</h2>
							</div>
							<p class="variant">White</p>
							<div class="delivery-options">
								<p class="delivery-option">Pickup at Hukut Store</p>
								<p class="delivery-time">Ready by Mon. Apr 14</p>
							</div>
							<div class="product-actions">
								<span class="item-price">₹4,497</span>
							</div>
						</div>
					</div>
					<div class="item-controls">
						<div class="quantity-selector">
							<button class="qty-btn" onclick="updateQuantity(-1, this)">−</button>
							<input type="number" class="qty-input" value="1" min="1"
								onchange="updateTotal()">
							<button class="qty-btn" onclick="updateQuantity(1, this)">+</button>
						</div>
						<div class="item-actions">
							<button class="remove-btn" onclick="removeItem(this)">Remove</button>
						</div>
					</div>
				</div>

				<div class="cart-item card">
					<div class="item-details">
						<img src="https://via.placeholder.com/100" class="product-image"
							alt="Utitma Atom 520 Pro">
						<div class="product-info">
							<div class="product-header">
								<h2>Utitma Atom 520 Pro</h2>
							</div>
							<p class="variant">White</p>
							<div class="delivery-options">
								<p class="delivery-option">Pickup at Hukut Store</p>
								<p class="delivery-time">Ready by Mon. Apr 14</p>
							</div>
							<div class="product-actions">
								<span class="item-price">₹2,603</span>
							</div>
						</div>
					</div>
					<div class="item-controls">
						<div class="quantity-selector">
							<button class="qty-btn" onclick="updateQuantity(-1, this)">−</button>
							<input type="number" class="qty-input" value="1" min="1"
								onchange="updateTotal()">
							<button class="qty-btn" onclick="updateQuantity(1, this)">+</button>
						</div>
						<div class="item-actions">
							<button class="remove-btn" onclick="removeItem(this)">Remove</button>
						</div>
					</div>
				</div>
			</div>

			<div class="order-summary card">
				<h2 class="summary-title">Order Summary</h2>

				<div class="summary-details">
					<div class="summary-row">
						<span>Item Total (<span id="totalItems">2</span>)
						</span> <span id="subtotal">7,100</span>
					</div>
				</div>

				<div class="total-row">
					<span>Total</span> <span id="totalAmount">₹7,100</span>
				</div>

				<button class="checkout-btn">Proceed to Checkout</button>
			</div>
		</div>
	</div>
	<jsp:include page="header.jsp" />
	<script>
        function updateQuantity(change, btn) {
            const input = btn.parentElement.querySelector('.qty-input');
            let newVal = parseInt(input.value) + change;
            if(newVal < 1) newVal = 1;
            input.value = newVal;
            updateTotal();
        }

        
        function updateTotal() {
            const items = document.querySelectorAll('.cart-item');
            let totalItems = 0;
            let subtotal = 0;

            items.forEach(item => {
                const qty = parseInt(item.querySelector('.qty-input').value);
                const price = parseFloat(item.querySelector('.item-price').textContent.replace('₹','').replace(/,/g, ''));
                subtotal += qty * price;
                totalItems += qty;
            });

            // Always show these elements even when empty
            document.getElementById('totalItems').textContent = totalItems;
            document.getElementById('subtotal').textContent = `₹${subtotal.toLocaleString()}`;
            document.getElementById('totalAmount').textContent = `₹${subtotal.toLocaleString()}`;
        }

        function removeItem(btn) {
            const item = btn.closest('.cart-item');
            item.remove();
            updateTotal();
            
            // Add empty state if needed
            const cartItems = document.querySelector('.cart-items');
            if(cartItems.children.length === 0) {
                const emptyCart = document.createElement('div');
                emptyCart.className = 'empty-cart card';
                emptyCart.innerHTML = `
                    <h3>Your cart is empty</h3>
                    <p>Continue shopping to add items to your cart</p>
                `;
                cartItems.prepend(emptyCart);
            }
        }
  
    </script>

</body>
</html>