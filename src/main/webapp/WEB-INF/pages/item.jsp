<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Gymshark-style Item Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/item.css">
</head>
<body>
	    <jsp:include page="header.jsp" />
    <div class="product-container">
        <div class="left-column">
            <div class="image-gallery">
                <div class="gallery-grid">
                    <div class="gallery-item">
                        <img src="${pageContext.request.contextPath}/resources/images/system/sample.png" class="gallery-image" alt="...">
                    </div>
                    <div class="gallery-item">
                        <img src="${pageContext.request.contextPath}/resources/images/system/sample.png" class="gallery-image" alt="...">
                    </div>
                    <div class="gallery-item main-image">
                        <img src="${pageContext.request.contextPath}/resources/images/system/sample.png" class="gallery-image" alt="...">
                    </div>
                    <div class="gallery-item">
                        <img src="${pageContext.request.contextPath}/resources/images/system/sample.png" class="gallery-image" alt="...">
                    </div>
                    <div class="gallery-item">
                        <img src="${pageContext.request.contextPath}/resources/images/system/sample.png" class="gallery-image" alt="...">
                    </div>

                </div>
            </div>
        </div>

        <div class="product-details">
            <h1 class="product-title">Performance Crew Socks 5-Pack</h1>
            <div class="product-price">$35.00</div>

            <div class="quantity-selector">
                <p>Quantity</p>
                <div class="quantity-controls">
                    <button class="quantity-btn">-</button>
                    <input type="number" class="quantity-input" value="1" min="1" id="quantity" readonly>
                    <button class="quantity-btn">+</button>
                </div>
            </div>

            <div class="size-selector">
                <p>Size</p>
                <select class="size-dropdown">
                    <option value="">Select Size</option>
                    <option value="s">S</option>
                    <option value="m">M</option>
                    <option value="l">L</option>
                </select>
            </div>

            <button class="add-to-cart">ADD TO CART</button>

            <div class="product-description">
                <p>The Performance Crew Socks feature our signature breathable fabric technology with reinforced heel and toe construction for enhanced durability. Moisture-wicking properties keep your feet dry during intense workouts.</p>
                <ul style="margin-top: 15px; list-style: inside;">
                    <li>5-pair pack</li>
                    <li>80% Cotton, 15% Polyester, 5% Elastane</li>
                    <li>Arch support compression band</li>
                    <li>Breathable mesh panels</li>
                </ul>
            </div>
        </div>

        <div class="reviews-section">
            <h2>Customer Reviews</h2>

            <div class="review-form">
                <h3>Write a Review</h3>
                <div class="rating-stars" id="ratingStars">
                    <span class="star" data-rating="1">★</span>
                    <span class="star" data-rating="2">★</span>
                    <span class="star" data-rating="3">★</span>
                    <span class="star" data-rating="4">★</span>
                    <span class="star" data-rating="5">★</span>
                </div>
                <textarea class="review-textarea" placeholder="Write your review..." id="reviewText"></textarea>
                <button class="submit-review">Submit Review</button>
            </div>

            <div class="review-list" id="reviewList">
                <div class="review-card">
                    <div class="review-header">
                        <div class="review-rating">
                            <span style="color: #ffd700">★★★★★</span>
                        </div>
                        <span class="review-author">John D.</span>
                        <span class="review-date">· August 15, 2023</span>
                    </div>
                    <p>Best socks I've ever owned! Perfect fit and great quality.</p>
                </div>
                <div class="review-card">
                    <div class="review-header">
                        <div class="review-rating">
                            <span style="color: #ffd700">★★★★☆</span>
                        </div>
                        <span class="review-author">Jane S.</span>
                        <span class="review-date">· September 01, 2023</span>
                    </div>
                    <p>Very comfortable for workouts. Would buy again.</p>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="footer.jsp" />
</body>
</html>