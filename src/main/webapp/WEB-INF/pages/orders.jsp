<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Your Orders</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/orders.css" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
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
        <h2 class="mb-4">Your Orders</h2>
        
        <c:choose>
            <c:when test="${empty orders}">
                <div class="alert alert-info">
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
                                                    <div class="d-flex align-items-center">
                                                        <img src="${item.product.image}" alt="${item.product.name}" class="product-image me-3">
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