<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Crimson+Text:ital,wght@0,400;0,600;0,700;1,400&display=swap" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    
<!-- Top Bar -->
<div class="top-bar">
    <a href="${pageContext.request.contextPath}/dashboard" class="top-bar-item">Hi, User</a>
    <a href="${pageContext.request.contextPath}/aboutus" class="top-bar-item">About Us</a>
    <a href="${pageContext.request.contextPath}/contactus" class="top-bar-item">Contact Us</a>
</div>

<!-- Header -->
<header class="header">
    <a href="${pageContext.request.contextPath}/home" class="logo">LockedIN</a>
    <nav class="nav">
        <a href="${pageContext.request.contextPath}/supplements" class="nav-item">SUPPLEMENTS</a>
        <a href="${pageContext.request.contextPath}/equipments" class="nav-item">EQUIPMENTS</a>
        <a href="${pageContext.request.contextPath}/merchandises" class="nav-item">MERCHANDISES</a>
    </nav>
    <div class="right-menu">
        <div class="search-box">
            <span class="icon search-icon"></span>
            <input type="text" class="search-input" placeholder="What are you looking for to...">
        </div>
        <span class="icon heart-icon"></span>
        <span class="icon user-icon"></span>
        <span class="icon bag-icon" id="cartIcon"></span>
    </div>
</header>
   <!-- New Cart Overlay and Panel structure -->
<div class="cart-overlay" id="cartOverlay"></div>
<div class="cart-panel" id="cartPanel">
    <div class="cart-header">
        <h2 class="cart-title">YOUR BAG</h2>
        <div class="cart-header-actions">
            <button class="cart-header-button bag-icon-button">
                <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M6 2L3 6v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2V6l-3-4z" fill="white" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    <line x1="3" y1="6" x2="21" y2="6" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    <path d="M16 10a4 4 0 0 1-8 0" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
            </button>
            <button class="close-cart" id="closeCart">
                <span class="close-icon"></span>
            </button>
        </div>
    </div>
    <div class="empty-cart">
        <div class="empty-cart-icon">
            <svg width="50" height="50" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M6 2L3 6v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2V6l-3-4z" stroke="#666" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                <line x1="3" y1="6" x2="21" y2="6" stroke="#666" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                <path d="M16 10a4 4 0 0 1-8 0" stroke="#666" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
        </div>
        <h3 class="empty-cart-text">YOUR BAG IS EMPTY</h3>
        <p class="empty-cart-subtext">There are no products in your bag</p>
        <a href="#" class="cart-action-btn shop-men-btn">SHOP MENS</a>
        <a href="#" class="cart-action-btn shop-women-btn">SHOP WOMENS</a>
    </div>
</div>

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
});
    </script>