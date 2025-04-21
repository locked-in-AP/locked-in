<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/admindashboard.css" />
<meta charset="UTF-8">
<title>Admin Dashboard - LockedIN</title>
</head>
<body>

	<jsp:include page="header.jsp" />
	<c:if test="${not empty success}">
		<p class="success-msg">You are logged in!</p>
	</c:if>

	<div class="admin-header">
		<h1 class="section-title">Admin Dashboard</h1>
		<div class="right-section">
			<p>Welcome, Admin</p>

			<img
				src="${pageContext.request.contextPath}/resources/images/system/Redd.png"
				alt="Admin Profile" class="admin-pfp">
		</div>

			<a href="${pageContext.request.contextPath}/logout" class="logout-btn">
				<i class="fas fa-sign-out-alt"></i> Logout
			</a>
			<img
				src="${pageContext.request.contextPath}/resources/images/system/userpfp.png"
				alt="Admin Profile" class="admin-pfp">
		</div>
	</div>


	<div class="dashboard-buttons">
		<a href="#" class="btn"><i class="fas fa-plus"></i> Add Product</a> <a
			href="#" class="btn"><i class="fas fa-box"></i> View Orders</a>
	</div>

	<div class="dashboard-container">
		<h1 class="section-title">Overview</h1>
		<div class="metrics-grid">
			<div class="metric-card">
				<div class="metric-content">
					<h3 class="metric-title">Total Revenue</h3>
					<p class="metric-subtitle">Last 30 days</p>
				</div>
				<div class="metric-value revenue">$82,650</div>
			</div>

			<div class="metric-card">
				<div class="metric-content">
					<h3 class="metric-title">Total Order</h3>
					<p class="metric-subtitle">Last 30 days</p>
				</div>
				<div class="metric-value order">16.45</div>
			</div>

			<div class="metric-card">
				<div class="metric-content">
					<h3 class="metric-title">Total Customer</h3>
					<p class="metric-subtitle">Last 30 days</p>
				</div>
				<div class="metric-value customer">1,462</div>
			</div>

			<div class="metric-card">
				<div class="metric-content">
					<h3 class="metric-title">Pending Delivery</h3>
					<p class="metric-subtitle">Last 30 days</p>
				</div>
				<div class="metric-value delivery">117</div>
			</div>
		</div>
	</div>

	<div class="table-container">
		<h2>Recent Orders</h2>
		<table>
			<thead>
				<tr>
					<th>#</th>
					<th>Product Name</th>
					<th>Product ID</th>
					<th>Quantity</th>
					<th>Price</th>
					<th>Order Time</th>
					<th>Customer</th>
					<th>Status</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>1</td>
					<td>Adjustable Dumbbell</td>
					<td>gym001</td>
					<td>5</td>
					<td>$150.00</td>
					<td>2025-04-12 09:45</td>
					<td>John Muscle</td>
					<td class="status intransit">In Transit</td>
				</tr>
				<tr>
					<td>2</td>
					<td>Whey Protein 2kg</td>
					<td>gym002</td>
					<td>2</td>
					<td>$90.00</td>
					<td>2025-04-11 14:33</td>
					<td>Sara Fitwell</td>
					<td class="status delivered">Delivered</td>
				</tr>
				<tr>
					<td>3</td>
					<td>Gym Tank Top</td>
					<td>gym003</td>
					<td>10</td>
					<td>$250.00</td>
					<td>2025-04-10 18:12</td>
					<td>Mike Ironman</td>
					<td class="status delivered">Delivered</td>
				</tr>
				<tr>
					<td>4</td>
					<td>Yoga Mat Pro</td>
					<td>gym004</td>
					<td>3</td>
					<td>$60.00</td>
					<td>2025-04-09 10:15</td>
					<td>Amanda Flex</td>
					<td class="status delivered">Delivered</td>
				</tr>
			</tbody>
		</table>
		<div class="view-details">
			<button>View Details</button>
		</div>
	</div>

	<div class="dashboard-container">
		<h1 class="section-title">Analytics Summary</h1>
		<div class="card-container">
			<div class="card">
				<div class="card-content">
					<div class="icon">📦</div>
					<h2>No Orders</h2>
					<p>No sales made in the past 30 days</p>
				</div>
			</div>

			<div class="card">
				<div class="card-content">
					<div class="icon">💳</div>
					<h2>No Revenue</h2>
					<p>Revenue hasn't changed in the last 30 days</p>
				</div>
			</div>





		</div>
	</div>

			<div class="card">
				<div class="card-content">
					<div class="icon">💳</div>
					<h2>No Revenue</h2>
					<p>Revenue hasn't changed in the last 30 days</p>
				</div>
			</div>
		</div>
	</div>
</body>
</html>