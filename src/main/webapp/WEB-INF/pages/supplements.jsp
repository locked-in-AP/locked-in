<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Locked IN - Supplements</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/products.css" />
</head>
<body>
	<jsp:include page="header.jsp" />

	<!-- Hero Banner -->
	<div class="hero-banner">
		<h1 class="hero-title">SUPPLEMENTS</h1>
		<p class="hero-subtitle">Premium supplements for serious athletes</p>
	</div>

	<!-- Main Content -->
	<div class="container">
		<!-- Add spinner container -->
		<div class="spinner-container">
			<div class="spinner"></div>
		</div>

		<div class="page-title">
			<h2 class="title-text">SUPPLEMENTS</h2>
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
								value="price-low-high" ${currentSort == 'price-low-high' ? 'checked' : ''}> 
							<label for="price-low-high">Price: Low to High</label>
						</div>
						<div class="filter-option">
							<input type="radio" id="price-high-low" name="sort"
								value="price-high-low" ${currentSort == 'price-high-low' ? 'checked' : ''}> 
							<label for="price-high-low">Price: High to Low</label>
						</div>
						<div class="filter-option">
							<input type="radio" id="relevancy" name="sort" value="relevancy"
								${currentSort == 'relevancy' ? 'checked' : ''}> 
							<label for="relevancy">Relevancy</label>
						</div>
						<div class="filter-option">
							<input type="radio" id="newest" name="sort" value="newest"
								${currentSort == 'newest' ? 'checked' : ''}> 
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
									<c:forEach begin="1" end="5" var="i">
										<c:choose>
											<c:when test="${product.averageRating != null && i <= product.averageRating}">
												<span class="star filled">★</span>
											</c:when>
											<c:otherwise>
												<span class="star">☆</span>
											</c:otherwise>
										</c:choose>
									</c:forEach>
									<span class="rating-text">
										<c:choose>
											<c:when test="${product.averageRating != null}">
												${String.format("%.1f", product.averageRating)}
											</c:when>
											<c:otherwise>
												No ratings
											</c:otherwise>
										</c:choose>
									</span>
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
	
	<script>
		// Add event listeners to all sort radio buttons
		document.querySelectorAll('input[name="sort"]').forEach(radio => {
			radio.addEventListener('change', function() {
				// Get the current URL and create a URLSearchParams object
				const url = new URL(window.location.href);
				const params = new URLSearchParams(url.search);
				
				// Update the sort parameter
				params.set('sort', this.value);
				
				// Update the URL search parameters
				url.search = params.toString();
				
				// Update the URL with the new sort parameter without reloading
				window.history.pushState({}, '', url.toString());
				
				// Show loading state
				const productGrid = document.querySelector('.product-grid');
				const spinnerContainer = document.querySelector('.spinner-container');
				productGrid.classList.add('loading');
				spinnerContainer.style.display = 'block';
				
				// Fetch the sorted products
				fetch(url.toString())
					.then(response => response.text())
					.then(html => {
						// Create a temporary div to parse the HTML
						const parser = new DOMParser();
						const doc = parser.parseFromString(html, 'text/html');
						
						// Get the new product grid content
						const newProductGrid = doc.querySelector('.product-grid');
						
						// Update the product count
						const newProductCount = doc.querySelector('.product-count');
						document.querySelector('.product-count').textContent = newProductCount.textContent;
						
						// Update the product grid with new content
						productGrid.innerHTML = newProductGrid.innerHTML;
						
						// Remove loading state
						productGrid.classList.remove('loading');
						spinnerContainer.style.display = 'none';
					})
					.catch(error => {
						console.error('Error:', error);
						productGrid.classList.remove('loading');
						spinnerContainer.style.display = 'none';
					});
			});
		});
	</script>
</body>
</html>