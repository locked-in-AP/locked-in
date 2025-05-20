<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Admin - Order Management</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admindashboard.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .admin-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
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

        .order-status.pending {
            background-color: #fff3cd;
            color: #856404;
        }

        .order-status.completed {
            background-color: #d4edda;
            color: #155724;
        }

        .order-status.cancelled {
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
            justify-content: space-between;
            align-items: center;
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

        .status-actions {
            display: flex;
            gap: 1rem;
        }

        .status-btn {
            padding: 0.5rem 1rem;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-weight: 500;
            transition: background-color 0.3s;
        }

        .status-btn.complete {
            background-color: #28a745;
            color: white;
        }

        .status-btn.complete:hover {
            background-color: #218838;
        }

        .status-btn.cancel {
            background-color: #dc3545;
            color: white;
        }

        .status-btn.cancel:hover {
            background-color: #c82333;
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

        .orders-list-section h2 {
            margin-top: 2rem;
        }
    </style>
</head>
<body>
    <jsp:include page="../header.jsp" />
    
    <!-- Notification for messages -->
    <c:if test="${not empty message}">
        <div class="notification ${messageType}">
            <div class="notification-content">
                <div class="notification-body">${message}</div>
                <button type="button" class="notification-close" onclick="this.parentElement.parentElement.style.display='none'">×</button>
            </div>
        </div>
    </c:if>

    <div class="sidebar">
        <ul class="nav">
            <li><a href="${pageContext.request.contextPath}/admindashboard"><span class="icon"><i class="fas fa-home"></i></span> Dashboard</a></li>
            <li><a href="${pageContext.request.contextPath}/users"><span class="icon"><i class="fas fa-user"></i></span>Users</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/orders"><span class="icon"><i class="fas fa-shopping-cart"></i></span> Orders</a></li>
            <li><a href="${pageContext.request.contextPath}/productList"><span class="icon"><i class="fas fa-box"></i></span> View Product</a></li>
            <li><a href="${pageContext.request.contextPath}/addProduct"><span class="icon"><i class="fas fa-pen"></i></span> Add Product</a></li>
            <li><a href="${pageContext.request.contextPath}/deleteProduct"><span class="icon"><i class="fas fa-trash"></i></span> Delete Product</a></li>
        </ul>
    </div>
    
    <div class="main-content">
        <div class="admin-header">
            <h1 class="section-title">Order Management</h1>
            <div class="right-section">
                <p>Welcome, Admin</p>
                <a href="${pageContext.request.contextPath}/logout" class="logout-btn">
                    <i class="fas fa-sign-out-alt"></i> Logout
                </a>
                <img src="${pageContext.request.contextPath}/resources/images/system/userpfp.png"
                    alt="Admin Profile" class="admin-pfp">
            </div>
        </div>
        
        <div class="orders-filter-bar" style="margin-bottom: 2rem; display: flex; align-items: center; gap: 1rem;margin-top: 2rem">
            <label for="orderFilter" style="font-weight: 600;">Show:</label>
            <select id="orderFilter" style="padding: 0.5rem 1rem; border-radius: 4px; border: 1px solid #ccc;">
                <option value="all">All Orders</option>
                <option value="not-completed">Not Completed Orders</option>
                <option value="completed">Completed Orders</option>
            </select>
        </div>

        <div class="orders-list-section" id="notCompletedSection">
            <h2>Not Completed Orders</h2>
            <c:choose>
                <c:when test="${empty notCompletedOrders}">
                    <div class="info-message">
                        <i class="fas fa-info-circle"></i>
                        <div>No not completed orders found.</div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="orders-list">
                        <c:forEach items="${notCompletedOrders}" var="order">
                            <div class="order-card" id="order-${order.orderId}">
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
                                    <div class="status-actions">
                                        <c:if test="${order.paymentStatus == 'pending'}">
                                            <form action="${pageContext.request.contextPath}/updateOrderStatus" method="post" style="display: inline;">
                                                <input type="hidden" name="orderId" value="${order.orderId}">
                                                <input type="hidden" name="status" value="completed">
                                                <button type="submit" class="status-btn complete">Mark as Completed</button>
                                            </form>
                                            <form action="${pageContext.request.contextPath}/updateOrderStatus" method="post" style="display: inline;">
                                                <input type="hidden" name="orderId" value="${order.orderId}">
                                                <input type="hidden" name="status" value="cancelled">
                                                <button type="submit" class="status-btn cancel">Cancel Order</button>
                                            </form>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="orders-list-section" id="completedSection" style="margin-top: 3rem;">
            <h2>Completed Orders</h2>
            <c:choose>
                <c:when test="${empty completedOrders}">
                    <div class="info-message">
                        <i class="fas fa-info-circle"></i>
                        <div>No completed orders found.</div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="orders-list">
                        <c:forEach items="${completedOrders}" var="order">
                            <div class="order-card" id="order-${order.orderId}">
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
                                    <div class="status-actions">
                                        <c:if test="${order.paymentStatus == 'pending'}">
                                            <form action="${pageContext.request.contextPath}/updateOrderStatus" method="post" style="display: inline;">
                                                <input type="hidden" name="orderId" value="${order.orderId}">
                                                <input type="hidden" name="status" value="completed">
                                                <button type="submit" class="status-btn complete">Mark as Completed</button>
                                            </form>
                                            <form action="${pageContext.request.contextPath}/updateOrderStatus" method="post" style="display: inline;">
                                                <input type="hidden" name="orderId" value="${order.orderId}">
                                                <input type="hidden" name="status" value="cancelled">
                                                <button type="submit" class="status-btn cancel">Cancel Order</button>
                                            </form>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <script>
            const filterDropdown = document.getElementById('orderFilter');
            const notCompletedSection = document.getElementById('notCompletedSection');
            const completedSection = document.getElementById('completedSection');

            function updateOrderSections() {
                const value = filterDropdown.value;
                if (value === 'all') {
                    notCompletedSection.style.display = '';
                    completedSection.style.display = '';
                } else if (value === 'not-completed') {
                    notCompletedSection.style.display = '';
                    completedSection.style.display = 'none';
                } else if (value === 'completed') {
                    notCompletedSection.style.display = 'none';
                    completedSection.style.display = '';
                }
            }
            filterDropdown.addEventListener('change', updateOrderSections);
            // Set initial state
            updateOrderSections();
        </script>
    </div>

    <jsp:include page="../footer.jsp" />
</body>
</html> 