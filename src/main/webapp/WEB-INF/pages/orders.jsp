<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Your Orders</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/orders.css" />
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
        <h2 class="page-title">Your Orders</h2>
        
        <c:choose>
            <c:when test="${empty orders}">
                <div class="alert-info">
                    You haven't placed any orders yet. <a href="${pageContext.request.contextPath}/products">Start shopping</a>
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