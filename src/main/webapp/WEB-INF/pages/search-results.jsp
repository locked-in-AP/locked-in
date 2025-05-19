<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Locked IN - Search Results</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/products.css" />
</head>
<body>
    <jsp:include page="header.jsp" />

    <!-- Hero Banner -->
    <div class="hero-banner">
        <h1 class="hero-title">SEARCH RESULTS</h1>
        <p class="hero-subtitle">Showing results for "${searchQuery}"</p>
    </div>

    <!-- Main Content -->
    <div class="container">
        <!-- Add spinner container -->
        <div class="spinner-container">
            <div class="spinner"></div>
        </div>

        <div class="page-title">
            <h2 class="title-text">SEARCH RESULTS</h2>
            <span class="product-count">${products.size()} Products</span>
        </div>

        <div class="content-wrapper">
            <!-- Filters -->
            <aside class="filters">
                <div class="filter-section">
                    <button class="filter-button">FILTER & SORT</button>
                    <a href="${pageContext.request.contextPath}/search?q=${searchQuery}" class="clear-all">Clear All</a>
                </div>

                <div class="filter-divider"></div>

                <div class="filter-section">
                    <div class="filter-header">
                        <span>SORT BY</span>
                        <button class="filter-toggle">▼</button>
                    </div>
                    <div class="filter-options">
                        <div class="filter-option">
                            <input type="radio" id="price-low-high" name="sort" value="price-low-high" 
                                ${currentSort == 'price-low-high' ? 'checked' : ''}> 
                            <label for="price-low-high">Price: Low to High</label>
                        </div>
                        <div class="filter-option">
                            <input type="radio" id="price-high-low" name="sort" value="price-high-low"
                                ${currentSort == 'price-high-low' ? 'checked' : ''}> 
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
                        <span>CATEGORY</span>
                        <button class="filter-toggle">▼</button>
                    </div>
                    <div class="filter-options">
                        <div class="filter-option">
                            <input type="radio" id="all-categories" name="category" value=""
                                ${currentCategory == null || currentCategory.isEmpty() ? 'checked' : ''}> 
                            <label for="all-categories">All Categories</label>
                        </div>
                        <div class="filter-option">
                            <input type="radio" id="supplements" name="category" value="supplement"
                                ${currentCategory == 'supplement' ? 'checked' : ''}> 
                            <label for="supplements">Supplements</label>
                        </div>
                        <div class="filter-option">
                            <input type="radio" id="equipment" name="category" value="equipment"
                                ${currentCategory == 'equipment' ? 'checked' : ''}> 
                            <label for="equipment">Equipment</label>
                        </div>
                        <div class="filter-option">
                            <input type="radio" id="merchandise" name="category" value="merchandise"
                                ${currentCategory == 'merchandise' ? 'checked' : ''}> 
                            <label for="merchandise">Merchandise</label>
                        </div>
                    </div>
                </div>
            </aside>

            <!-- Product Grid -->
            <div class="product-grid${empty products ? ' empty' : ''}">
                <c:choose>
                    <c:when test="${empty products}">
                        <div class="no-results">
                            <div class="no-results-icon">🔍</div>
                            <h3>No Products Found</h3>
                            <p>We couldn't find any products matching "${searchQuery}"</p>
                            <div class="no-results-suggestions">
                                <p>Suggestions:</p>
                                <ul>
                                    <li>Check your spelling</li>
                                    <li>Try more general keywords</li>
                                    <li>Try different keywords</li>
                                </ul>
                            </div>
                            <a href="${pageContext.request.contextPath}/home" class="back-to-home">Back to Home</a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${products}" var="product">
                            <a href="${pageContext.request.contextPath}/item?id=${product.productId}" class="product-link">
                                <div class="product-card">
                                    <div class="product-image-container">
                                        <span class="product-tag">NEW</span>
                                        <button class="wishlist-button">
                                            <span class="wishlist-icon"></span>
                                        </button>
                                        <img src="${product.image}" alt="${product.name}" class="product-image">
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
                    </c:otherwise>
                </c:choose>
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
        // Add event listeners to all sort and category radio buttons
        document.querySelectorAll('input[name="sort"], input[name="category"]').forEach(radio => {
            radio.addEventListener('change', function() {
                // Get the current URL and create a URLSearchParams object
                const url = new URL(window.location.href);
                const params = new URLSearchParams(url.search);
                
                // Update the sort or category parameter
                params.set(this.name, this.value);
                
                // Update the URL search parameters
                url.search = params.toString();
                
                // Update the URL with the new parameters without reloading
                window.history.pushState({}, '', url.toString());
                
                // Show loading state
                const productGrid = document.querySelector('.product-grid');
                const spinnerContainer = document.querySelector('.spinner-container');
                productGrid.classList.add('loading');
                spinnerContainer.style.display = 'block';
                
                // Fetch the filtered/sorted products
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