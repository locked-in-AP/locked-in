<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>About Us</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/aboutus.css">
<link
	href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600;700&display=swap"
	rel="stylesheet">
</head>
<body>
	<!-- Header Section -->
	<jsp:include page="header.jsp" />
	<!-- Hero Card Section -->
	<section class="hero-card">
		<h1>
			Welcome to LockedIn.co your ultimate fitness companion!<br>We as
			a team of 5 people are passionate <br>about helping you achieve
			your <br>fitness goals.
		</h1>
		<div class="divider"></div>
	</section>

	<!-- Team Section Header -->
	<div class="section-header">
		<h2>Our Team</h2>
	</div>

	<div class="team-cards">
		<!-- Team Member 1 -->
		<div class="team-card">
			<h3>Pragyan Khadka</h3>
			<div class="email">Email: pragyan86.@gmail.com</div>
			<div class="bio">As the Founder and CEO of LockedIn.co, I am
				passionate about empowering fitness lifestyles across Nepal. When
				I'm not building the brand, you’ll find me in the gym—fitness keeps
				me driven and focused. I believe that discipline in training
				translates into discipline in business. My goal is to make
				high-quality gym gear accessible to every fitness enthusiast in the
				country.</div>
		</div>

		<!-- Team Member 2 -->
		<div class="team-card">
			<h3>Amiks Karki</h3>
			<div class="email">Email: amiks22.@gmail.com</div>
			<div class="bio">As our Lead Developer, I’m focused on creating
				seamless digital experiences for LockedIn.co. I’m also a gym
				enthusiast who believes that strength in tech and strength in the
				gym go hand in hand. Whether it's coding a new feature or pushing
				through a tough set, I bring the same energy and commitment to both
				worlds. Innovation and endurance are what drive me.</div>
		</div>

		<!-- Team Member 3 -->
		<div class="team-card">
			<h3>Dikshyant Chapagain</h3>
			<div class="email">Email: dikshyant2004.@gmail.com</div>
			<div class="bio">As the UI/UX Designer at LockedIn.co, I design
				smooth and inspiring interfaces that reflect the strength and
				clarity of our brand. Outside of design, I'm all about gains at the
				gym—where form and function matter just as much. I’m passionate
				about creating user journeys that are as satisfying as a great
				workout. Precision and aesthetics guide everything I do.</div>
		</div>

		<!-- Team Member 4 -->
		<div class="team-card">
			<h3>Krishna Bhatta</h3>
			<div class="email">Email: kkrishnab.@gmail.com</div>
			<div class="bio">As Marketing Manager at LockedIn.co, I spread
				the word about top-tier gym gear across Nepal. I live the lifestyle
				too—committed to lifting both brands and barbells. My campaigns are
				driven by real passion and firsthand fitness experience. From
				influencer partnerships to grassroots promotions, I make sure our
				message hits like a good workout: strong and unforgettable.</div>
		</div>

		<!-- Team Member 5 -->
		<div class="team-card">
			<h3>Prashanna Sthapit</h3>
			<div class="email">Email: prashanna.@gmail.com</div>
			<div class="bio">I manage and secure all customer data as the
				Database Administrator at LockedIn.co. Beyond tech, I’m passionate
				about fitness and never miss a workout—it’s all about balance. I
				approach data with the same consistency and care I bring to my
				training routine. Clean code, clean reps—that’s how I roll. Reliable
				systems and strong habits build strong foundations.</div>
		</div>
	</div>


	<!-- Footer Section -->
	<jsp:include page="footer.jsp" />
</body>
</html>