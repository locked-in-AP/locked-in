<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>User Dashboard</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/userprofile.css">

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <jsp:include page="header.jsp" />

	<div class="account-container">
		<div class="account-overview-container">
			<div class="account-name">
				<img
					src="${pageContext.request.contextPath}/resources/images/system/userpfp.png"
					alt="Profile">
				<h1>${sessionScope.name != null ? sessionScope.name : 'USERNAME'}</h1>
			</div>
			<div class="account-tier">
				<div class="reward-card">
					<h2 class="card-title">Unlock XP And Rewards</h2>
					<button class="header-btn">VIEW TIERS AND BENEFITS</button>
				</div>
			</div>

			<div class="contact-info">
				<h2>CONTACT INFO</h2>
				<div class="contact-item">
					<i class="fas fa-map-marker-alt"></i> 500 Panda, Brazil
				</div>
				<div class="contact-item">
					<i class="fas fa-phone"></i> 240 Iotos
				</div>
				<div class="contact-item">
					<i class="fas fa-envelope"></i> ${sessionScope.email != null ? sessionScope.email : 'email@example.com'}
				</div>
			</div>
		</div>
	</div>

	<div class="card-container">
		<div class="orders-column">
			<div class="orders-card">
				<h2 class="orders-header">ORDERS</h2>
				<div class="image-container">
					<img
						src="${pageContext.request.contextPath}/resources/images/system/orders.png"
						class="placeholder-image" alt="Orders">
				</div>
				<div class="empty-state">
					You haven't made any orders yet.<br> When you make an order
					it'll show up here.
				</div>
				<div class="button-container">
					<button class="shop-button" onclick="window.location.href='${pageContext.request.contextPath}/products'">VIEW PRODUCTS</button>
				</div>
			</div>

			<div class="orders-card">
				<h2 class="orders-header">RECENT ACTIVITY</h2>
				<div class="image-container">
					<img
						src="${pageContext.request.contextPath}/resources/images/system/orders.png"
						class="placeholder-image" alt="Activity">
				</div>
				<div class="empty-state">
					No recent activity found.<br> Your workout history will appear
					here.
				</div>
				<div class="button-container">
					<button class="shop-button" onclick="window.location.href='${pageContext.request.contextPath}/history'">VIEW HISTORY</button>
				</div>
			</div>
		</div>

		<div class="features-card">
			<div class="section">
				<h2>RETURNS</h2>
				<p>Quick, easy and simple returns with Happy Returns.</p>
				<button class="button" onclick="alert('Returns feature coming soon!')">START RETURN</button>
			</div>

			<div class="section">
				<h2>REFER A FRIEND</h2>
				<p>Introduce your friends and give them £10 off, and to say
					thanks we'll give you £10 off your next order too.</p>
				<button class="button" onclick="alert('Referral system coming soon!')">REFER NOW</button>
			</div>

			<div class="section">
				<h2>ADDRESS</h2>
				<p>Get your orders whenever, wherever you are!</p>
				<button class="button" onclick="window.location.href='${pageContext.request.contextPath}/updateProfile'">CHANGE ADDRESS</button>
			</div>

			<div class="section">
				<h2>WISH LIST</h2>
				<p>Save your favorite items for later</p>
				<button class="button" onclick="window.location.href='${pageContext.request.contextPath}/wishlist'">VIEW WISH LIST</button>
			</div>
		</div>
    </div>

	<div class="account-signout-update">
		<!-- Sign Out Icon -->
		<a href="${pageContext.request.contextPath}/logout" class="account-action">
			<i class="fa-solid fa-right-from-bracket"></i> Sign Out
		</a>

		<!-- Update Profile Icon -->
		<a href="${pageContext.request.contextPath}/updateProfile" class="account-action">
			<i class="fa-solid fa-user-pen"></i> Update Profile
		</a>
	</div>

    <jsp:include page="footer.jsp" />
    
    <script>
    document.addEventListener('DOMContentLoaded', function() {
        // Handle tiers and benefits button
        document.querySelector('.header-btn').addEventListener('click', function() {
            alert('Tiers and benefits feature coming soon!');
        });
        
        // Ensure all buttons have proper hover effects
        const buttons = document.querySelectorAll('.button, .shop-button, .header-btn');
        buttons.forEach(button => {
            button.addEventListener('mouseover', function() {
                this.style.transition = 'all 0.3s ease';
            });
        });
    });
    </script>
</body>
</html>