<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/contactus.css">
<title>Contact Us</title>
</head>
<body>
    <jsp:include page="header.jsp" />
    <div class="container">
        <h1>Contact Us - Locked In</h1>
        <p>We'd love to hear from you! Please feel free to reach out with any questions, feedback, or suggestions. Use the form below or contact us directly using the information provided.</p>

        <form action="${pageContext.request.contextPath}/contact-us" method="post">
            <div class="form-group">
                <label for="name">Your Name</label>
                <input type="text" id="name" name="name" placeholder="Enter your name" required>
            </div>
            <div class="form-group">
                <label for="email">Your Email</label>
                <input type="email" id="email" name="email" placeholder="Enter your email" required>
            </div>
            <div class="form-group">
                <label for="message">Your Message</label>
                <textarea id="message" name="message" placeholder="Enter your message" required></textarea>
            </div>
            <button type="submit">Send Message</button>
        </form>

        <div class="contact-info">
            <h2>Our Contact Information</h2>
            <p><strong>Email:</strong> support@lockedinfitness.com</p>
            <p><strong>Phone:</strong> +1 (555) 123-4567</p>
            <p><strong>Address:</strong> 123 Fitness Plaza, Anytown, USA</p>
            <p>Thank you for choosing <span class="highlight">Locked In</span>!</p>
        </div>
    </div>
</body>
</html>