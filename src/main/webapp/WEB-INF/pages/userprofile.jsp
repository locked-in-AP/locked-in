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
	
<%
    String profilePicture = (String) session.getAttribute("profilePicture");
    if (profilePicture == null || profilePicture.isEmpty()) {
        // Default profile picture
        profilePicture = "resources/images/system/userpfp.png";
    }
%>	

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <jsp:include page="header.jsp" />

	<div class="profile-wrapper">
		<div class="profile-overview">
			<div class="rewards-box">
				<h2 class="box-title">Unlock XP And Rewards</h2>
				<button class="action-btn">VIEW TIERS AND BENEFITS</button>
			</div>
		</div>

		<div class="profile-content">
			<div class="orders-section">
				<div class="orders-box">
					<h3 class="box-title">Your Orders</h3>
					<div class="image-wrapper">
						<img src="${pageContext.request.contextPath}/resources/images/system/orders.png" alt="Orders">
					</div>
					<div class="button-wrapper">
						<a href="${pageContext.request.contextPath}/orders" class="action-btn">VIEW ORDERS</a>
					</div>
				</div>

				<div class="orders-box">
					<h3 class="box-title">Your Wishlist</h3>
					<div class="image-wrapper">
						<img src="${pageContext.request.contextPath}/resources/images/system/wishlist.png" alt="Wishlist">
					</div>
					<div class="button-wrapper">
						<a href="${pageContext.request.contextPath}/wishlist" class="action-btn">VIEW WISHLIST</a>
					</div>
				</div>

				<div class="features-box">
					<h3 class="box-title">Returns</h3>
					<button class="action-btn" onclick="alert('Returns feature coming soon!')">START RETURN</button>
				</div>

				<div class="features-box">
					<h3 class="box-title">Refer a Friend</h3>
					<button class="action-btn" onclick="alert('Referral system coming soon!')">REFER NOW</button>
				</div>
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
        document.querySelector('.action-btn').addEventListener('click', function() {
            alert('Tiers and benefits feature coming soon!');
        });
        
        // Ensure all buttons have proper hover effects
        const buttons = document.querySelectorAll('.action-btn, .shop-button');
        buttons.forEach(button => {
            button.addEventListener('mouseover', function() {
                this.style.transition = 'all 0.3s ease';
            });
        });

        // Add click event listeners to prevent default action for coming soon features
        buttons.forEach(button => {
            button.addEventListener('click', function(e) {
                if (this.textContent.includes('coming soon')) {
                    e.preventDefault();
                    alert('This feature is coming soon!');
                }
            });
        });
    });
    </script>
</body>
</html>