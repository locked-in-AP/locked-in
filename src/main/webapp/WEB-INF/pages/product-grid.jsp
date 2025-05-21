<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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