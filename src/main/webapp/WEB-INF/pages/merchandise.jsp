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
				<a href="${pageContext.request.contextPath}/item"
					class="product-link">
					<div class="product-card">
						<div class="product-image-container">
							<span class="product-tag">NEW</span>
							<button class="wishlist-button">
								<span class="wishlist-icon"></span>
							</button>
							<img
								src="https://getrawnutrition.com/cdn/shop/files/Bum_Itholate_Toathted_Graham_Cracker_25_serv_-_front_312e015a-6614-4544-a1c7-d55c0ff2639e.png?v=1742227696&width=1512"
								alt="CBUM Itholate Protein" class="product-image">
						</div>
						<div class="product-info">
							<div class="product-rating">
								<span class="star"></span> <span class="star"></span> <span
									class="star"></span> <span class="star"></span> <span
									class="rating-text">4.3</span>
							</div>
							<h3 class="product-title">CBUM Itholate Protein</h3>
							<p class="product-color">CBUM Signature Series</p>
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
								src="https://getrawnutrition.com/cdn/shop/files/5_Pounds_Vanilla_Oatmeal_Front_1.jpg?v=1743604298&width=900"
								alt="5lb CBUM Itholate Protein" class="product-image">
						</div>
						<div class="product-info">
							<div class="product-rating">
								<span class="star"></span> <span class="star"></span> <span
									class="star"></span> <span class="star"></span> <span
									class="rating-text">4.4</span>
							</div>
							<h3 class="product-title">5lb CBUM Itholate Protein</h3>
							<p class="product-color">Whey Isolate Protein</p>
							<p class="product-price">$99.99</p>
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
								src="https://getrawnutrition.com/cdn/shop/files/BUM-Itholate-Chris_s_Secret_Whey_Blueberry_muffin.png?v=1731361695&width=1512"
								alt="Christopher's Secret Whey" class="product-image">
						</div>
						<div class="product-info">
							<div class="product-rating">
								<span class="star"></span> <span class="star"></span> <span
									class="star"></span> <span class="star"></span> <span
									class="rating-text">4.7</span>
							</div>
							<h3 class="product-title">Christopher's Secret Whey Itholate
								Protein</h3>
							<p class="product-color">Whey Isolate Protein</p>
							<p class="product-price">$54.99</p>
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
								src="https://getrawnutrition.com/cdn/shop/files/CREATINE_30_c34004ef-8a55-4e38-a0c1-679c685eda87.png?v=1741890235&width=1512"
								alt="Creatine Monohydrate" class="product-image">
						</div>
						<div class="product-info">
							<div class="product-rating">
								<span class="star"></span> <span class="star"></span> <span
									class="star"></span> <span class="star"></span> <span
									class="rating-text">4.3</span>
							</div>
							<h3 class="product-title">CREATINE MONOHYDRATE</h3>
							<p class="product-color">Increase Muscular Strength</p>
							<p class="product-price">$34</p>
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
								src="https://getrawnutrition.com/cdn/shop/files/Pump_Juicy_Pumps_Front.png?v=1731356698&width=900"
								alt="Christopher's Juicy Pumps" class="product-image">
						</div>
						<div class="product-info">
							<h3 class="product-title">Christopher's Juicy Pumps</h3>
							<p class="product-color">Improve Blood Flow</p>
							<p class="product-price">$18</p>
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
								src="https://getrawnutrition.com/cdn/shop/files/Pump_Combo_Fruit_Punch_bf0fa9e5-80d9-43a7-b30d-c5d03ead395e.png?v=1738856223&width=900"
								alt="Pump RTD Pre-workout" class="product-image">
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
								src="https://rysesupps.com/cdn/shop/files/cl_1_bccd21b5-39e2-46a7-a4b7-b384af8f0b0c_800x.webp?v=1737568725"
								alt="Godzilla Pre-Workout" class="product-image">
						</div>
						<div class="product-info">
							<h3 class="product-title">Godzilla Pre-Workout</h3>
							<p class="product-color">Cherry Limeade</p>
							<p class="product-price">$40</p>
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
								src="https://rysesupps.com/cdn/shop/files/2lb_MCI_1_800x.png?v=1742230389"
								alt="Loaded Protein" class="product-image">
						</div>
						<div class="product-info">
							<h3 class="product-title">Loaded Protein</h3>
							<p class="product-color">Mint Chip Ice Cream / 27 Servings</p>
							<p class="product-price">$44.99</p>
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
								src="https://rysesupps.com/cdn/shop/files/btl_creatine_1_800x.webp?v=1714074353"
								alt="Creatine Monohydrate" class="product-image">
						</div>
						<div class="product-info">
							<h3 class="product-title">Creatine Monohydrate</h3>
							<p class="product-color">Unflavored</p>
							<p class="product-price">$40</p>
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