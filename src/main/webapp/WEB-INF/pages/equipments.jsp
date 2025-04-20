
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Locked IN - New Arrivals</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/products.css" />
</head>
<body>
	<jsp:include page="header.jsp" />

	<!-- Hero Banner -->
	<div class="hero-banner">
		<h1 class="hero-title">NEW ARRIVALS</h1>
		<p class="hero-subtitle">Brand new drops, brand new reasons to go
			gym. You're welcome.</p>
	</div>

	<!-- Main Content -->
	<div class="container">
		<div class="page-title">
			<h2 class="title-text">NEW ARRIVALS</h2>
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
						<span>GENDER</span>
						<button class="filter-toggle">▼</button>
					</div>
				</div>

				<div class="filter-divider"></div>

				<div class="filter-section">
					<div class="filter-header">
						<span>SIZE</span>
						<button class="filter-toggle">▼</button>
					</div>
				</div>

				<div class="filter-divider"></div>

				<div class="filter-section">
					<div class="filter-header">
						<span>FEATURES</span>
						<button class="filter-toggle">▼</button>
					</div>
				</div>

				<div class="filter-divider"></div>

				<div class="filter-section">
					<div class="filter-header">
						<span>ACTIVITY</span>
						<button class="filter-toggle">▼</button>
					</div>
				</div>
			</aside>

			<!-- Product Grid -->
			<div class="product-grid">
				<!-- Product 1 -->
				<div class="product-card">
					<div class="product-image-container">
						<span class="product-tag">NEW</span>
						<button class="wishlist-button">
							<span class="wishlist-icon"></span>
						</button>
						<img
							src="https://fitnesssuperstore.com/cdn/shop/files/FF-RCHD5-50-2.png?v=1739353976&width=460"
							alt="Legacy Lifting Gloves" class="product-image">
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

				<!-- Product 2 -->
				<div class="product-card">
					<div class="product-image-container">
						<span class="product-tag">NEW</span>
						<button class="wishlist-button">
							<span class="wishlist-icon"></span>
						</button>
						<img
							src="https://www.topfitness.com/cdn/shop/files/inspire-ft2-pro-smith-functional-trainer-771744.jpg?v=1726811262"
							alt="Crew Socks 5pk" class="product-image">
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

				<!-- Product 3 -->
				<div class="product-card">
					<div class="product-image-container">
						<span class="product-tag">NEW</span>
						<button class="wishlist-button">
							<span class="wishlist-icon"></span>
						</button>
						<img
							src="https://www.topfitness.com/cdn/shop/files/centr-x-hyrox-perform-tread-8_f2282a75-1abe-484c-9ef9-60a27ae1555a.jpg?v=1739048346"
							alt="Everyday Gym Bag Small" class="product-image">
					</div>
					<div class="product-info">
						<div class="product-rating">
							<span class="star"></span> <span class="star"></span> <span
								class="star"></span> <span class="star"></span> <span
								class="rating-text">4.7</span>
						</div>
						<h3 class="product-title">Centr x Hyrox Perform Tread
						</h3>
						<p class="product-color">Treadmill</p>
						<p class="product-price">$6,400</p>
					</div>
				</div>

				<!-- Product 4 -->
				<div class="product-card">
					<div class="product-image-container">
						<span class="product-tag">NEW</span>
						<button class="wishlist-button">
							<span class="wishlist-icon"></span>
						</button>
						<img
							src="https://www.topfitness.com/cdn/shop/files/inspire-ft2-functional-trainer-package-606584.jpg?v=1726623070&width=2048"
							alt="Everyday Mini Gym Bag" class="product-image">
					</div>
					<div class="product-info">
						<div class="product-rating">
							<span class="star"></span> <span class="star"></span> <span
								class="star"></span> <span class="star"></span> <span
								class="rating-text">4.3</span>
						</div>
						<h3 class="product-title">Inspire FT2 </h3>
						<p class="product-color">Functional Trainer</p>
						<p class="product-price">$4,929</p>
					</div>
				</div>

				<!-- Product 5 -->
				<div class="product-card">
					<div class="product-image-container">
						<button class="wishlist-button">
							<span class="wishlist-icon"></span>
						</button>
						<img
							src="https://www.topfitness.com/cdn/shop/files/cityrow-max-rower-241383.jpg?v=1726623045"
							alt="Twist Headband" class="product-image">
					</div>
					<div class="product-info">
						<h3 class="product-title">CityRow Max Rower</h3>
						<p class="product-color">Rower</p>
						<p class="product-price">$1,999</p>
					</div>
				</div>

				<!-- Product 6 -->
				<div class="product-card">
					<div class="product-image-container">
						<button class="wishlist-button">
							<span class="wishlist-icon"></span>
						</button>
						<img
							src="https://www.topfitness.com/cdn/shop/files/Tonal_2_Accessories.jpg?v=1736357242"
							alt="1L Water Bottle" class="product-image">
					</div>
					<div class="product-info">
						<div class="product-rating">
							<span class="star"></span> <span class="star"></span> <span
								class="star"></span> <span class="rating-text">3.6</span>
						</div>
						<h3 class="product-title">Pump RTD Pre-workout</h3>
						<p class="product-color">Ready to Drink Pump</p>
						<p class="product-price">$55</p>
					</div>
				</div>

				<!-- Product 7 -->
				<div class="product-card">
					<div class="product-image-container">
						<button class="wishlist-button">
							<span class="wishlist-icon"></span>
						</button>
						<img
							src="https://www.topfitness.com/cdn/shop/files/inspire-cft-commercial-functional-trainer-385640.jpg?v=1726623063"
							alt="Metal Insulated Straw Bottle 1L" class="product-image">
					</div>
					<div class="product-info">
						<h3 class="product-title">Godzilla Pre-Workout</h3>
						<p class="product-color">Cherry Limeade</p>
						<p class="product-price">$40</p>
					</div>
				</div>

				<!-- Product 8 -->
				<div class="product-card">
					<div class="product-image-container">
						<button class="wishlist-button">
							<span class="wishlist-icon"></span>
						</button>
						<img
							src="https://www.topfitness.com/cdn/shop/files/precor-efx-835-elliptical-857236.webp?v=1738807764"
							alt="Straight Headband" class="product-image">
					</div>
					<div class="product-info">
						<h3 class="product-title">Loaded Protein</h3>
						<p class="product-color">Mint Chip Ice Cream / 27 Servings</p>
						<p class="product-price">$44.99</p>
					</div>
				</div>

				<!-- Product 9 -->
				<div class="product-card">
					<div class="product-image-container">
						<button class="wishlist-button">
							<span class="wishlist-icon"></span>
						</button>
						<img
							src="https://www.topfitness.com/cdn/shop/files/trx-sweat-812575.jpg?v=1726623407"
							alt="Metal Insulated Straw Bottle 1L" class="product-image">
					</div>
					<div class="product-info">
						<h3 class="product-title">Creatine Monohydrate</h3>
						<p class="product-color">Unflavored</p>
						<p class="product-price">$40</p>
					</div>
				</div>



			</div>
		</div>

		<!-- Pagination -->
		<div class="pagination">
			<button class="load-more-btn">LOAD MORE</button>
			<a href="#" class="view-all">View all</a>
			<p class="pagination-info">Viewing 1 - 60 of 65 products</p>
		</div>
	</div>


</body>
</html>
