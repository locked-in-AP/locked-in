<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Locked IN - Equipment</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/products.css" />
</head>
<body>
	<jsp:include page="header.jsp" />

	<!-- Hero Banner -->
	<div class="hero-banner">
		<h1 class="hero-title">GYM EQUIPMENT</h1>
		<p class="hero-subtitle">Premium equipment for serious athletes</p>
	</div>

	<!-- Main Content -->
	<div class="container">
		<div class="page-title">
			<h2 class="title-text">GYM EQUIPMENT</h2>
			<span class="product-count">65 Products</span>
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
						<span>CATEGORY</span>
						<button class="filter-toggle">▼</button>
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
						<span>EQUIPMENT TYPE</span>
						<button class="filter-toggle">▼</button>
					</div>
				</div>

				<div class="filter-divider"></div>

				<div class="filter-section">
					<div class="filter-header">
						<span>WEIGHT CAPACITY</span>
						<button class="filter-toggle">▼</button>
					</div>
				</div>
			</aside>

			<!-- Product Grid -->
			<div class="product-grid">
				<!-- Product 1 -->
				<a href="${pageContext.request.contextPath}/item"
					class="product-link">
					<div class="product-card">
						<div class="product-image-container">
							<span class="product-tag">NEW</span>
							<button class="wishlist-button">
								<span class="wishlist-icon"></span>
							</button>
							<img
								src="https://fitnesssuperstore.com/cdn/shop/files/FF-RCHD5-50-2.png?v=1739353976&width=460"
								alt="Hex Dumbbell Set" class="product-image">
						</div>
						<div class="product-info">
							<div class="product-rating">
								<span class="star"></span> <span class="star"></span> <span
									class="star"></span> <span class="star"></span> <span
									class="rating-text">4.3</span>
							</div>
							<h3 class="product-title">French Fitness Rubber Coated Hex
								Dumbbell Set</h3>
							<p class="product-color">5-50 lbs (New)</p>
							<p class="product-price">$55</p>
						</div>
					</div>
				</a>

				<!-- Product 2 -->
				<a href="${pageContext.request.contextPath}/item"
					class="product-link">
					<div class="product-card">
						<div class="product-image-container">
							<span class="product-tag">NEW</span>
							<button class="wishlist-button">
								<span class="wishlist-icon"></span>
							</button>
							<img
								src="https://www.topfitness.com/cdn/shop/files/inspire-ft2-pro-smith-functional-trainer-771744.jpg?v=1726811262"
								alt="Smith Functional Trainer" class="product-image">
						</div>
						<div class="product-info">
							<div class="product-rating">
								<span class="star"></span> <span class="star"></span> <span
									class="star"></span> <span class="star"></span> <span
									class="rating-text">4.4</span>
							</div>
							<h3 class="product-title">Smith Functional Trainer</h3>
							<p class="product-color">Inspire Series FT2 Pro</p>
							<p class="product-price">$6,499</p>
						</div>
					</div>
				</a>

				<!-- Product 3 -->
				<a href="${pageContext.request.contextPath}/item"
					class="product-link">
					<div class="product-card">
						<div class="product-image-container">
							<span class="product-tag">NEW</span>
							<button class="wishlist-button">
								<span class="wishlist-icon"></span>
							</button>
							<img
								src="https://www.topfitness.com/cdn/shop/files/centr-x-hyrox-perform-tread-8_f2282a75-1abe-484c-9ef9-60a27ae1555a.jpg?v=1739048346"
								alt="Centr Treadmill" class="product-image">
						</div>
						<div class="product-info">
							<div class="product-rating">
								<span class="star"></span> <span class="star"></span> <span
									class="star"></span> <span class="star"></span> <span
									class="rating-text">4.7</span>
							</div>
							<h3 class="product-title">Centr x Hyrox Perform Tread</h3>
							<p class="product-color">Treadmill</p>
							<p class="product-price">$6,400</p>
						</div>
					</div>
				</a>

				<!-- Product 4 -->
				<a href="${pageContext.request.contextPath}/item"
					class="product-link">
					<div class="product-card">
						<div class="product-image-container">
							<span class="product-tag">NEW</span>
							<button class="wishlist-button">
								<span class="wishlist-icon"></span>
							</button>
							<img
								src="https://www.topfitness.com/cdn/shop/files/inspire-ft2-functional-trainer-package-606584.jpg?v=1726623070&width=2048"
								alt="Inspire FT2" class="product-image">
						</div>
						<div class="product-info">
							<div class="product-rating">
								<span class="star"></span> <span class="star"></span> <span
									class="star"></span> <span class="star"></span> <span
									class="rating-text">4.3</span>
							</div>
							<h3 class="product-title">Inspire FT2</h3>
							<p class="product-color">Functional Trainer</p>
							<p class="product-price">$4,929</p>
						</div>
					</div>
				</a>

				<!-- Product 5 -->
				<a href="${pageContext.request.contextPath}/item"
					class="product-link">
					<div class="product-card">
						<div class="product-image-container">
							<button class="wishlist-button">
								<span class="wishlist-icon"></span>
							</button>
							<img
								src="https://www.topfitness.com/cdn/shop/files/cityrow-max-rower-241383.jpg?v=1726623045"
								alt="CityRow Max Rower" class="product-image">
						</div>
						<div class="product-info">
							<div class="product-rating">
								<span class="star"></span> <span class="star"></span> <span
									class="star"></span> <span class="star"></span> <span
									class="rating-text">4.3</span>
							</div>
							<h3 class="product-title">CityRow Max Rower</h3>
							<p class="product-color">Rower</p>
							<p class="product-price">$1,999</p>
						</div>
					</div>
				</a>

				<!-- Product 6 -->
				<a href="${pageContext.request.contextPath}/item"
					class="product-link">
					<div class="product-card">
						<div class="product-image-container">
							<button class="wishlist-button">
								<span class="wishlist-icon"></span>
							</button>
							<img
								src="https://www.topfitness.com/cdn/shop/files/Tonal_2_Accessories.jpg?v=1736357242"
								alt="Tonal Accessories" class="product-image">
						</div>
						<div class="product-info">
							<div class="product-rating">
								<span class="star"></span> <span class="star"></span> <span
									class="star"></span> <span class="rating-text">3.6</span>
							</div>
							<h3 class="product-title">Tonal Accessories Bundle</h3>
							<p class="product-color">Complete Set</p>
							<p class="product-price">$299</p>
						</div>
					</div>
				</a>

				<!-- Product 7 -->
				<a href="${pageContext.request.contextPath}/item"
					class="product-link">
					<div class="product-card">
						<div class="product-image-container">
							<button class="wishlist-button">
								<span class="wishlist-icon"></span>
							</button>
							<img
								src="https://www.topfitness.com/cdn/shop/files/inspire-cft-commercial-functional-trainer-385640.jpg?v=1726623063"
								alt="Commercial Functional Trainer" class="product-image">
						</div>
						<div class="product-info">
							<div class="product-rating">
								<span class="star"></span> <span class="star"></span> <span
									class="star"></span> <span class="star"></span> <span
									class="rating-text">4.3</span>
							</div>
							<h3 class="product-title">Inspire CFT Commercial Trainer</h3>
							<p class="product-color">Professional Grade</p>
							<p class="product-price">$7,999</p>
						</div>
					</div>
				</a>

				<!-- Product 8 -->
				<a href="${pageContext.request.contextPath}/item"
					class="product-link">
					<div class="product-card">
						<div class="product-image-container">
							<button class="wishlist-button">
								<span class="wishlist-icon"></span>
							</button>
							<img
								src="https://www.topfitness.com/cdn/shop/files/precor-efx-835-elliptical-857236.webp?v=1738807764"
								alt="Precor Elliptical" class="product-image">
						</div>
						<div class="product-info">
							<div class="product-rating">
								<span class="star"></span> <span class="star"></span> <span
									class="star"></span> <span class="star"></span> <span
									class="rating-text">4.3</span>
							</div>
							<h3 class="product-title">Precor EFX 835 Elliptical</h3>
							<p class="product-color">Commercial Series</p>
							<p class="product-price">$4,299</p>
						</div>
					</div>
				</a>

				<!-- Product 9 -->
				<a href="${pageContext.request.contextPath}/item"
					class="product-link">
					<div class="product-card">
						<div class="product-image-container">
							<button class="wishlist-button">
								<span class="wishlist-icon"></span>
							</button>
							<img
								src="https://www.topfitness.com/cdn/shop/files/trx-sweat-812575.jpg?v=1726623407"
								alt="TRX System" class="product-image">
						</div>
						<div class="product-info">
							<div class="product-rating">
								<span class="star"></span> <span class="star"></span> <span
									class="star"></span> <span class="star"></span> <span
									class="rating-text">4.3</span>
							</div>
							<h3 class="product-title">TRX Suspension Training System</h3>
							<p class="product-color">Complete Home Gym</p>
							<p class="product-price">$199</p>
						</div>
					</div>
				</a>
			</div>
		</div>

		<!-- Pagination -->
		<div class="pagination">
			<button class="load-more-btn">LOAD MORE</button>
			<a href="#" class="view-all">View all</a>
			<p class="pagination-info">Viewing 1 - 60 of 65 products</p>
		</div>
	</div>

	<jsp:include page="footer.jsp" />
</body>
</html>