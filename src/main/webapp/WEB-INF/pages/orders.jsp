<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Your Orders</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/orders.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        body {
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }

        .container {
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

        .orders-list {
            display: flex;
            flex-direction: column;
            gap: 2rem;
        }

        .order-card {
            background: white;
            border-radius: 8px;
            padding: 1.5rem;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }

        .order-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 1.5rem;
            padding-bottom: 1rem;
            border-bottom: 1px solid #eee;
        }

        .order-info {
            display: flex;
            flex-direction: column;
            gap: 0.5rem;
        }

        .order-id {
            font-weight: bold;
            color: #1a1f2c;
        }

        .order-date {
            color: #666;
            font-size: 0.9rem;
        }

        .order-status {
            padding: 0.5rem 1rem;
            border-radius: 4px;
            font-weight: 500;
            text-transform: uppercase;
            font-size: 0.8rem;
        }

        .order-status.paid {
            background-color: #d4edda;
            color: #155724;
        }

        .order-status.pending {
            background-color: #fff3cd;
            color: #856404;
        }

        .order-status.failed {
            background-color: #f8d7da;
            color: #721c24;
        }

        .order-items {
            margin-bottom: 1.5rem;
        }

        .order-items table {
            width: 100%;
            border-collapse: collapse;
        }

        .order-items th {
            text-align: left;
            padding: 0.75rem;
            background-color: #f8f9fa;
            color: #1a1f2c;
            font-weight: 500;
        }

        .order-items td {
            padding: 0.75rem;
            border-bottom: 1px solid #eee;
        }

        .product-row {
            display: flex;
            align-items: center;
            gap: 1rem;
        }

        .product-image {
            width: 60px;
            height: 60px;
            object-fit: cover;
            border-radius: 4px;
        }

        .item-details h5 {
            margin: 0;
            color: #1a1f2c;
        }

        .order-footer {
            display: flex;
            justify-content: flex-end;
            padding-top: 1rem;
            border-top: 1px solid #eee;
        }

        .total {
            display: flex;
            align-items: center;
            gap: 1rem;
            font-weight: bold;
            color: #1a1f2c;
        }

        .amount {
            font-size: 1.1rem;
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
            .order-header {
                flex-direction: column;
                gap: 1rem;
                align-items: flex-start;
            }

            .order-items {
                overflow-x: auto;
            }

            .order-items table {
                min-width: 600px;
            }
        }

        .review-section {
            margin-top: 1rem;
            width: 100%;
        }

        .review-form {
            background: #f8f9fa;
            padding: 1rem;
            border-radius: 8px;
            margin-bottom: 1rem;
        }

        .review-product {
            display: flex;
            align-items: center;
            gap: 1rem;
            margin-bottom: 1rem;
        }

        .review-product img {
            width: 50px;
            height: 50px;
            object-fit: cover;
            border-radius: 4px;
        }

        .review-product h5 {
            margin: 0;
            color: #1a1f2c;
        }

        .rating-stars {
            display: flex;
            gap: 0.5rem;
            margin-bottom: 1rem;
        }

        .rating-stars .star {
            cursor: pointer;
            color: #ddd;
            font-size: 1.5rem;
            transition: color 0.2s;
        }

        .rating-stars .star:hover,
        .rating-stars .star.active {
            color: #ffd700;
        }

        .review-form textarea {
            width: 100%;
            height: 100px;
            padding: 0.75rem;
            border: 1px solid #ddd;
            border-radius: 4px;
            margin-bottom: 1rem;
            resize: vertical;
        }

        .submit-review {
            background: #28a745;
            color: white;
            padding: 0.5rem 1rem;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-weight: 500;
            transition: background-color 0.3s;
        }

        .submit-review:hover {
            background: #218838;
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
    
    <div class="container">
        <h2 class="page-title">Your Orders</h2>
        
        <c:choose>
            <c:when test="${empty orders}">
                <div class="info-message">
                    <i class="fas fa-info-circle"></i>
                    <div>
                        You haven't placed any orders yet. <a href="${pageContext.request.contextPath}/products">Start shopping</a>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="orders-list">
                    <c:forEach items="${orders}" var="order">
                        <div class="order-card">
                            <div class="order-header">
                                <div class="order-info">
                                    <span class="order-id">Order #${order.orderId}</span>
                                    <span class="order-date">
                                        <fmt:formatDate value="${order.orderDate}" pattern="MMM dd, yyyy" />
                                    </span>
                                </div>
                                <div class="order-status ${order.paymentStatus.toLowerCase()}">
                                    ${order.paymentStatus}
                                </div>
                            </div>
                            
                            <div class="order-items">
                                <table>
                                    <thead>
                                        <tr>
                                            <th>Product</th>
                                            <th>Quantity</th>
                                            <th>Price</th>
                                            <th>Total</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${order.items}" var="item">
                                            <tr>
                                                <td>
                                                    <div class="product-row">
                                                        <img src="${item.product.image}" alt="${item.product.name}" class="product-image">
                                                        <div class="item-details">
                                                            <h5>${item.product.name}</h5>
                                                        </div>
                                                    </div>
                                                </td>
                                                <td>${item.quantity}</td>
                                                <td>
                                                    <fmt:formatNumber value="${item.product.price}" type="currency" currencySymbol="$"/>
                                                </td>
                                                <td>
                                                    <fmt:formatNumber value="${item.product.price * item.quantity}" type="currency" currencySymbol="$"/>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            
                            <div class="order-footer">
                                <div class="total">
                                    <span>Total:</span>
                                    <span class="amount">
                                        <fmt:formatNumber value="${order.totalPrice}" type="currency" currencySymbol="$"/>
                                    </span>
                                </div>
                                <c:if test="${order.paymentStatus == 'completed'}">
                                    <div class="review-section">
                                        <c:forEach items="${order.items}" var="item">
                                            <c:if test="${empty item.review}">
                                                <form action="${pageContext.request.contextPath}/addReview" method="post" class="review-form">
                                                    <input type="hidden" name="orderId" value="${order.orderId}">
                                                    <input type="hidden" name="productId" value="${item.product.productId}">
                                                    <div class="review-product">
                                                        <img src="${item.product.image}" alt="${item.product.name}" class="product-image">
                                                        <h5>${item.product.name}</h5>
                                                    </div>
                                                    <div class="rating-stars">
                                                        <span class="star" data-rating="1">★</span>
                                                        <span class="star" data-rating="2">★</span>
                                                        <span class="star" data-rating="3">★</span>
                                                        <span class="star" data-rating="4">★</span>
                                                        <span class="star" data-rating="5">★</span>
                                                        <input type="hidden" name="rating" value="0" id="rating-input" required>
                                                    </div>
                                                    <div class="validation-message" id="rating-error" style="color: #dc3545; font-size: 0.9rem; margin-bottom: 1rem; display: none;">Please select a rating</div>
                                                    <textarea name="review" placeholder="Write your review..."></textarea>
                                                    <div class="validation-message" id="review-error" style="color: #dc3545; font-size: 0.9rem; margin-bottom: 1rem; display: none;">Please write your review</div>
                                                    <button type="submit" class="submit-review" id="submit-review-btn" disabled>Submit Review</button>
                                                </form>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <script>
        // Show message if it exists in the request
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

        // Function to check for order status updates
        function checkOrderStatusUpdates() {
            const orderCards = document.querySelectorAll('.order-card');
            orderCards.forEach(card => {
                const orderId = card.id.split('-')[1];
                const currentStatus = card.querySelector('.order-status').textContent.trim();
                
                fetch(`${pageContext.request.contextPath}/getOrderStatus?orderId=${orderId}`)
                    .then(response => response.text())
                    .then(status => {
                        if (status && status !== currentStatus) {
                            const statusDiv = card.querySelector('.order-status');
                            statusDiv.classList.remove('pending', 'completed', 'cancelled');
                            statusDiv.classList.add(status.toLowerCase());
                            statusDiv.textContent = status;
                            
                            showMessage(`Order #${orderId} status updated to ${status}`, false);
                        }
                    })
                    .catch(error => console.error('Error checking order status:', error));
            });
        }

        // Check for updates every 30 seconds
        setInterval(checkOrderStatusUpdates, 30000);

        document.addEventListener('DOMContentLoaded', function() {
            const ratingStars = document.querySelectorAll('.rating-stars');
            
            ratingStars.forEach(stars => {
                const starsList = stars.querySelectorAll('.star');
                const ratingInput = stars.querySelector('input[name="rating"]');
                const submitBtn = stars.closest('form').querySelector('.submit-review');
                const ratingError = stars.closest('form').querySelector('#rating-error');
                const reviewError = stars.closest('form').querySelector('#review-error');
                const reviewTextarea = stars.closest('form').querySelector('textarea[name="review"]');
                
                // Add input event listener for review textarea
                reviewTextarea.addEventListener('input', function() {
                    if (this.value.trim() !== '') {
                        reviewError.style.display = 'none';
                    }
                });
                
                starsList.forEach(star => {
                    star.addEventListener('click', () => {
                        const rating = star.dataset.rating;
                        ratingInput.value = rating;
                        ratingError.style.display = 'none';
                        
                        starsList.forEach(s => {
                            if (s.dataset.rating <= rating) {
                                s.classList.add('active');
                            } else {
                                s.classList.remove('active');
                            }
                        });
                        
                        // Enable submit button when rating is selected
                        submitBtn.disabled = false;
                    });
                });

                // Add form submit validation
                const form = stars.closest('form');
                form.addEventListener('submit', function(e) {
                    let isValid = true;
                    
                    // Check rating
                    if (ratingInput.value === '0') {
                        ratingError.style.display = 'block';
                        isValid = false;
                    }
                    
                    // Check review text
                    if (reviewTextarea.value.trim() === '') {
                        reviewError.style.display = 'block';
                        isValid = false;
                    }
                    
                    if (!isValid) {
                        e.preventDefault();
                    }
                });
            });
        });
    </script>

    <jsp:include page="footer.jsp" />
</body>
</html> 