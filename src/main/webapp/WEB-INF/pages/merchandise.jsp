<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Locked IN - Merchandise</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/products.css" />
</head>
<body>
	<jsp:include page="header.jsp" />

	<!-- Hero Banner -->
	<div class="hero-banner">
		<h1 class="hero-title">MERCHANDISE</h1>
		<p class="hero-subtitle">Premium merchandise for serious athletes</p>
	</div>

	<!-- Main Content -->
	<div class="container">
		<div class="page-title">
			<h2 class="title-text">MERCHANDISE</h2>
			<span class="product-count">${products.size()} Products</span>
		</div>

		<div class="content-wrapper">
			<!-- Filters -->
			<aside class="filters">
				<div class="filter-section">
					<button class="filter-button">FILTER & SORT</button>
					<a href="#" class="clear-all">Clear All</a>
				</div>

				<div class="filter-divider"></div>

				<div class="filter-section">
					<div class="filter-header">
						<span>SORT BY</span>
						<button class="filter-toggle">▼</button>
					</div>
					<div class="filter-options">
						<div class="filter-option">
							<input type="radio" id="price-low-high" name="sort"
								value="price-low-high"> <label for="price-low-high">Price:
								Low to High</label>
						</div>
						<div class="filter-option">
							<input type="radio" id="price-high-low" name="sort"
								value="price-high-low"> <label for="price-high-low">Price:
								High to Low</label>
						</div>
						<div class="filter-option">
							<input type="radio" id="relevancy" name="sort" value="relevancy"
								checked> <label for="relevancy">Relevancy</label>
						</div>
						<div class="filter-option">
							<input type="radio" id="newest" name="sort" value="newest">
							<label for="newest">Newest</label>
						</div>
					</div>
				</div>

				<div class="filter-divider"></div>

				<div class="filter-section">
					<div class="filter-header">
						<span>BRAND</span>
						<button class="filter-toggle">▼</button>
					</div>
				</div>

				<div class="filter-divider"></div>

				<div class="filter-section">
					<div class="filter-header">
						<span>TYPE</span>
						<button class="filter-toggle">▼</button>
					</div>
				</div>

				<div class="filter-divider"></div>

				<div class="filter-section">
					<div class="filter-header">
						<span>PRICE RANGE</span>
						<button class="filter-toggle">▼</button>
					</div>
				</div>
			</aside>

			<!-- Product Grid -->
			<div class="product-grid">
				<c:forEach items="${products}" var="product">
					<a href="${pageContext.request.contextPath}/item?id=${product.productId}"
						class="product-link">
						<div class="product-card">
							<div class="product-image-container">
								<span class="product-tag">NEW</span>
								<button class="wishlist-button">
									<span class="wishlist-icon"></span>
								</button>
								<img src="${product.image}" alt="${product.name}"
									class="product-image">
							</div>
							<div class="product-info">
								<div class="product-rating">
									<span class="star"></span>
									<span class="star"></span>
									<span class="star"></span>
									<span class="star"></span>
									<span class="rating-text">4.3</span>
								</div>
								<h3 class="product-title">${product.name}</h3>
								<p class="product-color">${product.brand}</p>
								<p class="product-price">$${product.price}</p>
							</div>
						</div>
					</a>
				</c:forEach>
			</div>
		</div>

		<!-- Pagination -->
		<div class="pagination">
			<button class="load-more-btn">LOAD MORE</button>
			<a href="#" class="view-all">View all</a>
			<p class="pagination-info">Viewing 1 - ${products.size()} of ${products.size()} products</p>
		</div>
	</div>

	<jsp:include page="footer.jsp" />
</body>
</html>