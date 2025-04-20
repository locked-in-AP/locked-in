<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>About Us</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/aboutus.css">
<link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>
    <jsp:include page="header.jsp" />
    <!-- Hero Card Section -->
    <section class="hero-card">
        <h1>Welcome to Locked In.co your ultimate fitness companion!<br>We as a team of 5 people are passionate <br>about helping you achieve your <br>fitness goals.</h1>
        <div class="divider"></div>
    </section>
    
    <!-- Team Section Header -->
    <div class="section-header">
        <h2>Our Team</h2>
    </div>
    
    <!-- Team Cards Section -->
    <div class="team-cards">
        <!-- Team Member 1 -->
        <div class="team-card">
            <h3>Pragyan Khadka</h3>
            
            <div class="email">Email: your.email@example.com</div>
            <div class="bio">As the Founder and CEO of HEROINES, I am passionate about creating innovative solutions to help women lead healthier, more empowered lives. I have a background in both social work and community organizing, and I'm excited to bring those experiences together with HEROINES.</div>
        </div>
        
        <!-- Team Member 2 -->
        <div class="team-card">
            <h3>Amiks Karki</h3>
            
            <div class="email">Email: member1.email@example.com</div>
            <div class="bio">Our Lead Developer is a skilled software engineer with a passion for creating user-friendly and efficient applications. They are responsible for the technical architecture and development of our digital platforms.</div>
        </div>
        
        <!-- Team Member 3 -->
        <div class="team-card">
            <h3>Prashanna Sthapit</h3>
            
            <div class="email">Email: member2.email@example.com</div>
            <div class="bio">Our talented UI/UX Designer is responsible for creating the beautiful and intuitive interfaces of our digital presence. They are dedicated to making every interaction with HEROINES a pleasure.</div>
        </div>
        
        <!-- Team Member 4 -->
        <div class="team-card">
            <h3>Dikshyant Chapagain</h3>
            
            <div class="email">Email: member3.email@example.com</div>
            <div class="bio">Our Marketing Manager is responsible for spreading the word about HEROINES. They are dedicated to making sure every woman who could benefit from our community knows about our resources.</div>
        </div>
        
        <!-- Team Member 5 -->
        <div class="team-card">
            <h3>Krishna Bhatta</h3>
            
            <div class="email">Email: member4.email@example.com</div>
            <div class="bio">Our Database Administrator is responsible for managing and securing all of our member data. They ensure that our community's information is protected while remaining accessible when needed.</div>
        </div>
    </div>
</body>
</html>