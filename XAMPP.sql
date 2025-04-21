-- ===============================================================
-- TABLE: USERS
-- Stores user information.
-- ===============================================================
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    nickname VARCHAR(100),
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('customer', 'endorser', 'admin') DEFAULT 'customer' NOT NULL,
    date_of_birth DATE NOT NULL,
    joined_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    cart_size INT DEFAULT 0 NOT NULL
);

-- ===============================================================
-- TABLE: PRODUCT
-- Stores product information.
-- ===============================================================
CREATE TABLE product (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    description TEXT,
    brand VARCHAR(100),
    category ENUM('equipment', 'supplement', 'merchandise') NOT NULL,
    tags TEXT,
    price DECIMAL(10,2) NOT NULL CHECK (price >= 0),
    stock_quantity INT DEFAULT 0 NOT NULL,
    weight DECIMAL(6,2) NOT NULL CHECK (weight >= 0),
    image TEXT,
    dimensions VARCHAR(50) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- ===============================================================
-- TABLE: USER_PRODUCT
-- Tracks products associated with users (e.g., cart or wishlist).
-- ===============================================================
CREATE TABLE user_product (
    user_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    is_currently_in_cart BOOLEAN DEFAULT TRUE,
    PRIMARY KEY (user_id, product_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);

-- ===============================================================
-- TABLE: ORDERS
-- Stores individual orders.
-- ===============================================================
CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    order_date DATETIME NOT NULL,
    total_price DECIMAL(12, 2) NOT NULL,
    payment_date DATETIME,
    payment_method VARCHAR(50),
    payment_amount DECIMAL(12, 2),
    payment_status VARCHAR(50) NOT NULL
);

-- ===============================================================
-- TABLE: USER_PRODUCT_ORDER
-- Tracks which products from which users are part of which orders.
-- Also includes review information.
-- ===============================================================
CREATE TABLE user_product_order (
    user_id INT NOT NULL,
    product_id INT NOT NULL,
    order_id INT NOT NULL,
    order_quantity INT NOT NULL,
    review_date DATETIME,
    review TEXT,
    rating INT CHECK (rating >= 1 AND rating <= 5),
    PRIMARY KEY (user_id, product_id, order_id),
    FOREIGN KEY (user_id, product_id) REFERENCES user_product(user_id, product_id),
    FOREIGN KEY (order_id) REFERENCES orders(order_id)
);
