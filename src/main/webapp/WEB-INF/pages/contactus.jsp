<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Contact Locked In fitness for customer support, questions, feedback or business inquiries">
<meta name="keywords" content="fitness, contact, support, locked in, customer service">
<title>Contact Us - Locked In</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/header.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/contactus.css">
</head>
<body>
	<jsp:include page="header.jsp" />

	<div class="container">
		<h1>Contact Us - Locked In</h1>
		<p>We'd love to hear from you! Please feel free to reach out with
			any questions, feedback, or suggestions. Use the form below or
			contact us directly using the information provided.</p>

		<form class="contact-form" action="${pageContext.request.contextPath}/contact-us"
			method="post">
			<div class="form-group">
				<label for="name">Your Name</label> <input type="text" id="name"
					name="name" placeholder="Enter your name" required>
			</div>

			<div class="form-group">
				<label for="email">Your Email</label> <input type="email" id="email"
					name="email" placeholder="Enter your email" required>
			</div>

			<div class="form-group">
				<label for="message">Your Message</label>
				<textarea id="message" name="message"
					placeholder="Enter your message" required></textarea>
			</div>

			<button type="submit">Send Message</button>
		</form>

		<div class="contact-info">
			<h2>Our Contact Information</h2>
			<p>
				<strong>Email:</strong> support@lockedinfitness.com
			</p>
			<p>
				<strong>Phone:</strong> +1 (555) 123-4567
			</p>
			<p>
				<strong>Address:</strong> LockedIn.co, Kathmandu City, 44600
			</p>
			<p>
				Thank you for choosing <b>Locked In</b>!
			</p>
		</div>
	</div>

	<jsp:include page="footer.jsp" />
</body>
</html>