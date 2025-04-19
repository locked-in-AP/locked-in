-- creating USER table
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    nickname VARCHAR(100), -- if not null we use this to refer to the user
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    role ENUM('customer', 'endorser', 'admin') DEFAULT 'customer' NOT NULL,
    gender ENUM('male', 'female', 'prefer_not_to_say') NOT NULL,
    date_of_birth DATE NOT NULL,
    joined_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    cart_size INT DEFAULT 0 NOT NULL
);

-- creating PRODUCT table
CREATE TABLE product (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    description TEXT,
    brand VARCHAR(100), -- e.g., "Nike", "Adidas"
    category ENUM('equipment', 'supplement', 'merchandise') NOT NULL,
    tags TEXT,  -- e.g., "vegan,pre-workout", "t-shirt,men"
    price DECIMAL(10,2) NOT NULL CHECK (price >= 0),
    stock_quantity INT DEFAULT 0 NOT NULL,
    weight DECIMAL(6,2) NOT NULL CHECK (weight >= 0), -- in kg or lbs
    image TEXT, -- URL or image path
    dimensions VARCHAR(50) NOT NULL CHECK (dimensions >= 0), -- e.g., "10x5x2 cm"
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL
);
