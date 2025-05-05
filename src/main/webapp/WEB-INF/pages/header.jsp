<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Crimson+Text:ital,wght@0,400;0,600;0,700;1,400&display=swap"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/header.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">

<!-- Top Bar -->
<div class="top-bar">
    <c:choose>
        <c:when test="${sessionScope.email != null}">
            <c:choose>
                <c:when test="${cookie.role.value eq 'admin'}">
                    <span class="top-bar-item greeting">Hi, Admin</span>
                </c:when>
                <c:otherwise>
                    <span class="top-bar-item greeting">Hi, ${sessionScope.name != null ? sessionScope.name : 'User'}</span>
                </c:otherwise>
            </c:choose>
            <a href="${pageContext.request.contextPath}/logout" class="top-bar-item">Logout</a>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/login" class="top-bar-item">Log In</a>
        </c:otherwise>
    </c:choose>
    <a href="${pageContext.request.contextPath}/aboutus" class="top-bar-item">About Us</a>
    <a href="${pageContext.request.contextPath}/contactus" class="top-bar-item">Contact Us</a>
</div>

<!-- Header -->
<header class="header">
	<a href="${pageContext.request.contextPath}/home" class="logo">LockedIN</a>
	<nav class="nav">
		<a href="${pageContext.request.contextPath}/supplements"
			class="nav-item">SUPPLEMENTS</a> <a
			href="${pageContext.request.contextPath}/equipments" class="nav-item">EQUIPMENTS</a>
		<a href="${pageContext.request.contextPath}/merchandise"
			class="nav-item">MERCHANDISE</a>
	</nav>
	<div class="right-menu">
		<div class="search-box">
			<span class="icon search-icon"></span> <input type="text"
				class="search-input" placeholder="What are you looking for to...">
		</div>
		<a href="${pageContext.request.contextPath}/wishlist"><span class="icon heart-icon"></span></a>
		<a href="${pageContext.request.contextPath}/userprofile"><span
			class="icon user-icon"></span></a>
		<a href="${pageContext.request.contextPath}/cart" class="cart-link">
			<span class="icon bag-icon"></span>
			<c:if test="${sessionScope.email != null}">
				<span class="cart-count">${sessionScope.cartSize}</span>
			</c:if>
		</a>
	</div>
</header>

<style>
.cart-link {
    position: relative;
    text-decoration: none;
}

.cart-count {
    position: absolute;
    top: -8px;
    right: -8px;
    background-color: #ff4444;
    color: white;
    border-radius: 50%;
    padding: 2px 6px;
    font-size: 12px;
    min-width: 16px;
    text-align: center;
}
</style>

<script>
 //// Basic JavaScript for interactive elements
document.addEventListener('DOMContentLoaded', function() {
    // Toggle filter sections
    const filterToggles = document.querySelectorAll('.filter-toggle');
    filterToggles.forEach(toggle => {
        toggle.addEventListener('click', function() {
            const filterOptions = this.parentElement.nextElementSibling;
            if (filterOptions) {
                const isVisible = filterOptions.style.display !== 'none';
                filterOptions.style.display = isVisible ? 'none' : 'flex';
                this.innerHTML = isVisible ? '▼' : '▲';
            }
        });
    });
        
    // Wishlist button functionality
    const wishlistButtons = document.querySelectorAll('.wishlist-button');
    wishlistButtons.forEach(button => {
        button.addEventListener('click', function() {
            // Toggle filled heart icon
            const icon = this.querySelector('.wishlist-icon');
            const isFilled = icon.classList.contains('filled');
            
            if (isFilled) {
                icon.style.backgroundImage = `url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none' stroke='%231A1F2C' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3E%3Cpath d='M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z'%3E%3C/path%3E%3C/svg%3E")`;
                icon.classList.remove('filled');
            } else {
                icon.style.backgroundImage = `url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='%231A1F2C' stroke='%231A1F2C' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3E%3Cpath d='M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z'%3E%3C/path%3E%3C/svg%3E")`;
                icon.classList.add('filled');
            }
        });
    });
        
    // Load more button functionality
    const loadMoreBtn = document.querySelector('.load-more-btn');
    if (loadMoreBtn) {
        loadMoreBtn.addEventListener('click', function() {
            alert('Load more functionality would be implemented here');
        });
    }

 // Cart functionality
    const cartIcon = document.getElementById('cartIcon');
    const cartPanel = document.getElementById('cartPanel');
    const cartOverlay = document.getElementById('cartOverlay');
    const closeCart = document.getElementById('closeCart');
    
    if (cartIcon && cartPanel && cartOverlay && closeCart) {
        cartIcon.addEventListener('click', function() {
            cartPanel.classList.add('active');
            cartOverlay.classList.add('active');
            document.body.style.overflow = 'hidden'; // Prevent scrolling when cart is open
        });
        
        closeCart.addEventListener('click', function() {
            cartPanel.classList.remove('active');
            cartOverlay.classList.remove('active');
            document.body.style.overflow = ''; // Restore scrolling
        });
        
        cartOverlay.addEventListener('click', function() {
            cartPanel.classList.remove('active');
            cartOverlay.classList.remove('active');
            document.body.style.overflow = ''; // Restore scrolling
        });
    }

    // Initialize cart count from session storage
    const cartCount = document.querySelector('.cart-count');
    if (cartCount) {
        const storedCartSize = sessionStorage.getItem('cartSize');
        if (storedCartSize) {
            cartCount.textContent = storedCartSize;
        }
    }
});
    </script>