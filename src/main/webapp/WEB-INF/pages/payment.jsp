<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
    <title>Your Cart</title>
    <%-- Link to the combined CSS file --%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/payment.css" />
    <%-- Removed Font Awesome CSS link --%>
</head>
<body>
   
    <jsp:include page="header.jsp" />
    
    <div class="container">
        <h1 class="page-title">Your Cart</h1>

        <div class="cart-grid">
            <div class="cart-items">
                <div class="cart-item card">
                    <div class="item-details">
                        <img src="${pageContext.request.contextPath}/resources/images/system/person1.png"  class="product-image" alt="Vibes Socks 211">
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
                            <input type="number" class="qty-input" value="1" min="1" onchange="updateTotal()">
                            <button class="qty-btn" onclick="updateQuantity(1, this)">+</button>
                        </div>
                        <div class="item-actions">
                            <button class="remove-btn" onclick="removeItem(this)">Remove</button>
                        </div>
                    </div>
                </div>

                <div class="cart-item card">
                    <div class="item-details">
                        <img src="${pageContext.request.contextPath}/resources/images/system/person2.png" class="product-image" alt="Utitma Atom 520 Pro">
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
                            <input type="number" class="qty-input" value="1" min="1" onchange="updateTotal()">
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
                        <span>Item Total (<span id="totalItems">2</span>)</span>
                        <span id="subtotal">7,100</span>

                    </div>
                     <div class="summary-row">
                        <span>VAT (<span id="totalItems">2</span>)</span>
                        <span id="vat">Calculate VAT</span>


                    </div>

                     <div class="summary-row">
                        <span>Delivery Charge (<span id="totalItems">2</span>)</span>
                        <span id="delivery-charge">Calculate Delivery</span>


                    </div>
                </div>

                <div class="total-row">
                    <span>Total</span>
                    <span id="totalAmount">₹7,100</span>
                </div>

                <%-- Payment Option and Details --%>
                <div class="payment-option-container">
                    <div class="pay-option" id="credit-card-option">
                        Pay via Credit Card / Debit Card
                    </div>

                    <div class="card-details" id="card-details">
                        <div class="form-group">
                            <label for="card-number">Card number</label>
                            <div class="input-with-icon">
                                <input type="text" id="card-number" placeholder="Card number">
                                <%-- Removed icon here --%>
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label for="expiry-date">Expiration date (MM / YY)</label>
                                <input type="text" id="expiry-date" placeholder="MM / YY">
                            </div>
                            <div class="form-group">
                                <label for="security-code">Security code</label>
                                 <div class="input-with-icon">
                                     <input type="text" id="security-code" placeholder="Security code">
                                     <%-- Removed icon here --%>
                                 </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="card-name">Name on card</label>
                            <input type="text" id="card-name" placeholder="Name on card">
                        </div>
                    </div>
                </div>
                <%-- End Payment Option and Details --%>

                <button class="checkout-btn">Make Payment</button>
            </div>
        </div>
    </div>
    <jsp:include page="footer.jsp" />

    <%-- Link to the combined JavaScript file --%>
    <script src="${pageContext.request.contextPath}/js/payment.js"></script>

</body>
</html>