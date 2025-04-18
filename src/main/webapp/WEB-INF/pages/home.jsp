<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
</head>
<body >
    <jsp:include page="header.jsp" />
        
      <div class="endurance-hero">
            <div class="hero-accent"></div>
            <span class="brand-tag">Locked in</span>
            <h1 class="collection-title">BEST GYM</h1>
            <h2 class="collection-title">COLLECTION</h2>
            <p class="endurance-tag">Precision Performance for Every Locked in Athlete</p>
            <button class="explore-button">Explore Products</button>
     </div>
    
        <div class="main-container">

            <div class="shop-tag">
                <h1> Shop with Categories</h1>
            </div>

            <div class="cards-container">
                <!-- Card 1 -->
                <div class="product-card">
                    <img src="${pageContext.request.contextPath}/resources/images/system/merch.png" alt="Card Background" class="card-bg-image">
                    
                    <div class="card-content">
                      <h2 class="card-title">MERCHANDICE</h2>
                      
                      <button class="shop-button">Shop Now</button>
                    </div>
                  </div>
        
                <!-- Card 2 -->
                <div class="product-card">
                    <img src="${pageContext.request.contextPath}/resources/images/system/equipment.png" alt="Card Background" class="card-bg-image">
                    
                    <div class="card-content">
                      <h2 class="card-title">EQUIPMENTS</h2>
                      <button class="shop-button">Shop Now</button>
                    </div>
                  </div>
        
                <!-- Card 3 -->
                <div class="product-card">
                    <img src="${pageContext.request.contextPath}/resources/images/system/suppliment.png" alt="Card Background" class="card-bg-image">
                    
                    <div class="card-content">
                      <h2 class="card-title">SUPPLIMENTS</h2>
                      <button class="shop-button">Shop Now</button>
                    </div>
                  </div>
            </div>

            <div class="card-article">
                <div class="brand">LOCKED IN</div>
                <h1 class="title">OUR MOTTO</h1>
                
                <p class="tagline">Fueling Every Rep, Equipping Every Goal – Your Strength, Our Mission.</p>
            </div>


            <section class="featured-section">
                <div class="whyus-tag">
                    <h1> Why Choose Us?</h1>
                </div>
                
                <div class="features-grid">
                    <div class="feature">
                        <div class="feature-icon">
                            <img src="${pageContext.request.contextPath}/resources/images/system/delivary.png" alt="Delivary Image">
                        </div>
                        <h4>Timely Delivary</h4>
                        
                    </div>
                    <div class="feature">
                        <div class="feature-icon">
                            <img src="${pageContext.request.contextPath}/resources/images/system/quality.jpg" alt="Quality Image">
                        </div>
                        <h4>Premium Quality</h4>
                        
                    </div>
                    <div class="feature">
                        <div class="feature-icon">
                            <img src="${pageContext.request.contextPath}/resources/images/system/warranty.jpg" alt="Warranty Image">
                        </div>
                        <h4>A Year Warranty in All Equipments</h4>
                        
                    </div>
                </div>
             </section>

    </div>
    
    <jsp:include page="footer.jsp" />
    
    

</body>
</html>