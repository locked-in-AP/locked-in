-- Number of orders made by user
SELECT 
    u.user_id,
    u.name,
    COUNT(DISTINCT upo.order_id) AS total_orders
FROM 
    users u
JOIN 
    user_product_order upo ON u.user_id = upo.user_id
GROUP BY 
    u.user_id, u.name;


-- Number of products purchased by user
SELECT 
    u.user_id,
    u.name,
    SUM(upo.order_quantity) AS total_products_purchased
FROM 
    users u
JOIN 
    user_product_order upo ON u.user_id = upo.user_id
GROUP BY 
    u.user_id, u.name;


-- number of disctinct product purchased by user
SELECT 
    u.user_id,
    u.name,
    COUNT(DISTINCT upo.product_id) AS distinct_products_purchased
FROM 
    users u
JOIN 
    user_product_order upo ON u.user_id = upo.user_id
GROUP BY 
    u.user_id, u.name;


-- Add to cart
INSERT INTO user_product (user_id, product_id, quantity, is_currently_in_cart)
VALUES (?, ?, ?, TRUE)
ON DUPLICATE KEY UPDATE 
    quantity = quantity + VALUES(quantity),
    is_currently_in_cart = TRUE;

-- Soft remove from cart (retain history)
UPDATE user_product
SET is_currently_in_cart = FALSE
WHERE user_id = ? AND product_id = ? AND is_currently_in_cart = TRUE;


-- Creating order from cart items
-- Step 1: Create a new order
INSERT INTO orders (order_date, total_price, payment_status)
VALUES (NOW(), ?, 'pending');  -- You should calculate total_price in your application

-- Assume last_insert_id() gives the new order_id
SET @new_order_id = LAST_INSERT_ID();

-- Step 2: Move cart items to user_product_order
INSERT INTO user_product_order (user_id, product_id, order_id, order_quantity)
SELECT 
    up.user_id,
    up.product_id,
    @new_order_id,
    up.quantity
FROM 
    user_product up
WHERE 
    up.user_id = ? AND up.is_currently_in_cart = TRUE;

-- Step 3: Clear cart items for the user
DELETE FROM user_product
WHERE user_id = ? AND is_currently_in_cart = TRUE;


-- Buy now 
-- just create a user-product-order with a corresponding new order
-- Step 1: Create a new order
INSERT INTO orders (order_date, total_price, payment_status)
VALUES (NOW(), ?, 'pending');

-- Step 2: Get the new order ID
SET @order_id = LAST_INSERT_ID();

-- Step 3: Insert into user_product_order
INSERT INTO user_product_order (user_id, product_id, order_id, order_quantity)
VALUES (?, ?, @order_id, ?);

