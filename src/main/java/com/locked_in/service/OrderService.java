package com.locked_in.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.sql.Timestamp;

import com.locked_in.model.CartModel;
import com.locked_in.config.DbConfig;
import com.locked_in.model.OrderModel;
import com.locked_in.model.OrderItemModel;
import com.locked_in.model.ProductModel;

/**
 * Service class for handling order-related operations.
 * 
 * This service class manages all aspects of order processing including:
 * - Order creation and checkout
 * - Order status management
 * - Order retrieval and filtering
 * - Order analytics and reporting
 * 
 * The class maintains database connections and collaborates with
 * CartService for order processing.
 */
public class OrderService {
    private Connection connection;
    private CartService cartService;
    private boolean isConnectionError = false;

    /**
     * Creates a new OrderService instance.
     * 
     * Initializes the database connection and CartService.
     * Sets the connection error flag if the connection fails.
     */
    public OrderService() {
        try {
            this.connection = DbConfig.getDbConnection();
            this.cartService = new CartService();
            System.out.println("Database connection successful in OrderService");
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            isConnectionError = true;
            System.out.println("Database connection failed in OrderService: " + ex.getMessage());
        }
    }

    /**
     * Processes a user's checkout by creating an order from their cart.
     * 
     * This method performs the following operations in a transaction:
     * 1. Retrieves cart items and calculates total price
     * 2. Creates a new order record
     * 3. Moves cart items to order items
     * 4. Clears the user's cart
     * 5. Updates the user's cart size
     * 
     * @param userId the ID of the user checking out
     * @return true if checkout was successful, false otherwise
     * @throws SQLException if there is an error during database operations
     */
    public boolean processCheckout(int userId) throws SQLException {
        // Start transaction
        connection.setAutoCommit(false);
        
        try {
            // Get cart items
            List<CartModel> cartItems = cartService.getCartItems(userId);
            if (cartItems.isEmpty()) {
                return false;
            }

            // Calculate total price
            BigDecimal totalPrice = BigDecimal.ZERO;
            for (CartModel item : cartItems) {
                totalPrice = totalPrice.add(item.getProduct().getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity())));
            }

            // Create order
            String orderQuery = "INSERT INTO orders (order_date, total_price, payment_status) VALUES (NOW(), ?, 'pending')";
            int orderId;
            
            try (PreparedStatement orderStmt = connection.prepareStatement(orderQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
                orderStmt.setBigDecimal(1, totalPrice);
                orderStmt.executeUpdate();
                
                try (ResultSet generatedKeys = orderStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        orderId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Failed to get order ID");
                    }
                }
            }

            // Move cart items to order items
            String orderItemQuery = "INSERT INTO user_product_order (user_id, product_id, order_id, order_quantity) " +
                                  "VALUES (?, ?, ?, ?)";
            
            try (PreparedStatement orderItemStmt = connection.prepareStatement(orderItemQuery)) {
                for (CartModel item : cartItems) {
                    orderItemStmt.setInt(1, userId);
                    orderItemStmt.setInt(2, item.getProduct().getProductId());
                    orderItemStmt.setInt(3, orderId);
                    orderItemStmt.setInt(4, item.getQuantity());
                    orderItemStmt.addBatch();
                }
                orderItemStmt.executeBatch();
            }

            // Delete cart entries after order is placed
            String deleteCartQuery = "DELETE FROM user_product WHERE user_id = ? AND is_currently_in_cart = TRUE";
            try (PreparedStatement deleteCartStmt = connection.prepareStatement(deleteCartQuery)) {
                deleteCartStmt.setInt(1, userId);
                deleteCartStmt.executeUpdate();
            }

            // Update cart size in users table
            String updateCartSizeQuery = "UPDATE users SET cart_size = 0 WHERE user_id = ?";
            try (PreparedStatement updateCartSizeStmt = connection.prepareStatement(updateCartSizeQuery)) {
                updateCartSizeStmt.setInt(1, userId);
                updateCartSizeStmt.executeUpdate();
            }

            // Commit transaction
            connection.commit();
            return true;

        } catch (SQLException e) {
            // Rollback transaction on error
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    /**
     * Retrieves all orders for a specific user.
     * 
     * Orders are returned with complete details including:
     * - Order information (ID, date, total price, status)
     * - Product details for each item
     * - Review information if available
     * 
     * @param userId the ID of the user whose orders to retrieve
     * @return List of orders with complete details
     */
    public List<OrderModel> getUserOrders(int userId) {
        if (isConnectionError) {
            System.err.println("Cannot fetch user orders: Database connection error");
            return new ArrayList<>();
        }

        List<OrderModel> orders = new ArrayList<>();
        String sql = "SELECT o.order_id, o.order_date, o.total_price, o.payment_status, " +
                    "p.product_id, p.name, p.description, p.price, p.image, " +
                    "upo.order_quantity, upo.review, upo.rating, upo.review_date " +
                    "FROM orders o " +
                    "JOIN user_product_order upo ON o.order_id = upo.order_id " +
                    "JOIN product p ON upo.product_id = p.product_id " +
                    "WHERE upo.user_id = ? " +
                    "ORDER BY o.order_date DESC";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            
            var rs = pstmt.executeQuery();
            Map<Integer, OrderModel> orderMap = new HashMap<>();
            
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                OrderModel order = orderMap.get(orderId);
                
                if (order == null) {
                    order = new OrderModel();
                    order.setOrderId(orderId);
                    Timestamp timestamp = rs.getTimestamp("order_date");
                    if (timestamp != null) {
                        order.setOrderDate(timestamp);
                    }
                    order.setTotalPrice(rs.getBigDecimal("total_price"));
                    order.setPaymentStatus(rs.getString("payment_status"));
                    order.setItems(new ArrayList<>());
                    orderMap.put(orderId, order);
                }
                
                ProductModel product = new ProductModel();
                product.setProductId(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setImage(rs.getString("image"));
                
                OrderItemModel item = new OrderItemModel();
                item.setProduct(product);
                item.setQuantity(rs.getInt("order_quantity"));
                item.setReview(rs.getString("review"));
                item.setRating(rs.getObject("rating", Integer.class));
                if (rs.getTimestamp("review_date") != null) {
                    item.setReviewDate(rs.getTimestamp("review_date").toLocalDateTime());
                }
                
                order.getItems().add(item);
            }
            
            orders.addAll(orderMap.values());
            return orders;
        } catch (SQLException e) {
            System.err.println("Error fetching user orders: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch user orders", e);
        }
    }

    /**
     * Updates the payment status of an order.
     * 
     * @param orderId the ID of the order to update
     * @param status the new payment status
     * @return true if the update was successful, false otherwise
     * @throws SQLException if there is an error during database operations
     */
    public boolean updateOrderStatus(int orderId, String status) throws SQLException {
        if (isConnectionError || connection == null) {
            System.out.println("OrderService - Connection Error!");
            return false;
        }

        String query = "UPDATE orders SET payment_status = ? WHERE order_id = ?";
        System.out.println("OrderService - Updating order " + orderId + " to status: " + status);
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setInt(2, orderId);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("OrderService - Update result: " + rowsAffected + " rows affected");
            if (rowsAffected == 0) {
                System.out.println("OrderService - No rows were updated. Order might not exist.");
            }
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("OrderService - SQL Error updating order status: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Retrieves all orders in the system.
     * 
     * Orders are returned with complete details including:
     * - Order information (ID, date, total price, status)
     * - Product details for each item
     * - User information
     * 
     * Orders are sorted by completion status and date.
     * 
     * @return List of all orders with complete details
     * @throws SQLException if there is an error during database operations
     */
    public List<OrderModel> getAllOrders() throws SQLException {
        List<OrderModel> orders = new ArrayList<>();
        String query = "SELECT o.*, upo.product_id, upo.order_quantity, p.*, u.name as user_name " +
                      "FROM orders o " +
                      "JOIN user_product_order upo ON o.order_id = upo.order_id " +
                      "JOIN product p ON upo.product_id = p.product_id " +
                      "JOIN users u ON upo.user_id = u.user_id " +
                      "ORDER BY (o.payment_status = 'completed') ASC, o.order_date DESC";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            Map<Integer, OrderModel> orderMap = new HashMap<>();
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                OrderModel order = orderMap.get(orderId);
                if (order == null) {
                    order = new OrderModel();
                    order.setOrderId(orderId);
                    order.setOrderDate(rs.getTimestamp("order_date"));
                    order.setTotalPrice(rs.getBigDecimal("total_price"));
                    order.setPaymentStatus(rs.getString("payment_status"));
                    order.setItems(new ArrayList<>());
                    orderMap.put(orderId, order);
                }
                OrderItemModel item = new OrderItemModel();
                item.setProductId(rs.getInt("product_id"));
                item.setQuantity(rs.getInt("order_quantity"));
                ProductModel product = new ProductModel();
                product.setProductId(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setImage(rs.getString("image"));
                item.setProduct(product);
                order.getItems().add(item);
            }
            orders.addAll(orderMap.values());
        }
        return orders;
    }

    /**
     * Retrieves the current payment status of an order.
     * 
     * @param orderId the ID of the order to check
     * @return the current payment status
     * @throws SQLException if there is an error during database operations
     */
    public String getOrderStatus(int orderId) throws SQLException {
        String query = "SELECT payment_status FROM orders WHERE order_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getString("payment_status");
            }
        }
        
        return null;
    }

    /**
     * Checks if an order has been completed.
     * 
     * An order is considered completed when its payment status
     * is set to 'completed'.
     * 
     * @param orderId the ID of the order to check
     * @return true if the order is completed, false otherwise
     */
    public boolean isOrderCompleted(int orderId) {
        if (isConnectionError) {
            System.err.println("Cannot check order status: Database connection error");
            return false;
        }

        String sql = "SELECT payment_status FROM orders WHERE order_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            
            var rs = pstmt.executeQuery();
            if (rs.next()) {
                String status = rs.getString("payment_status");
                return "completed".equalsIgnoreCase(status);
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error checking order status: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves all orders that have not been completed.
     * 
     * Orders are returned with complete details and sorted by date.
     * 
     * @return List of incomplete orders
     * @throws SQLException if there is an error during database operations
     */
    public List<OrderModel> getNotCompletedOrders() throws SQLException {
        List<OrderModel> orders = new ArrayList<>();
        String query = "SELECT o.*, upo.product_id, upo.order_quantity, p.*, u.name as user_name " +
                      "FROM orders o " +
                      "JOIN user_product_order upo ON o.order_id = upo.order_id " +
                      "JOIN product p ON upo.product_id = p.product_id " +
                      "JOIN users u ON upo.user_id = u.user_id " +
                      "WHERE o.payment_status != 'completed' " +
                      "ORDER BY o.order_date DESC";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            Map<Integer, OrderModel> orderMap = new HashMap<>();
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                OrderModel order = orderMap.get(orderId);
                if (order == null) {
                    order = new OrderModel();
                    order.setOrderId(orderId);
                    order.setOrderDate(rs.getTimestamp("order_date"));
                    order.setTotalPrice(rs.getBigDecimal("total_price"));
                    order.setPaymentStatus(rs.getString("payment_status"));
                    order.setItems(new ArrayList<>());
                    orderMap.put(orderId, order);
                }
                OrderItemModel item = new OrderItemModel();
                item.setProductId(rs.getInt("product_id"));
                item.setQuantity(rs.getInt("order_quantity"));
                ProductModel product = new ProductModel();
                product.setProductId(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setImage(rs.getString("image"));
                item.setProduct(product);
                order.getItems().add(item);
            }
            orders.addAll(orderMap.values());
        }
        return orders;
    }

    /**
     * Retrieves all completed orders.
     * 
     * Orders are returned with complete details and sorted by date.
     * 
     * @return List of completed orders
     * @throws SQLException if there is an error during database operations
     */
    public List<OrderModel> getCompletedOrders() throws SQLException {
        List<OrderModel> orders = new ArrayList<>();
        String query = "SELECT o.*, upo.product_id, upo.order_quantity, p.*, u.name as user_name " +
                      "FROM orders o " +
                      "JOIN user_product_order upo ON o.order_id = upo.order_id " +
                      "JOIN product p ON upo.product_id = p.product_id " +
                      "JOIN users u ON upo.user_id = u.user_id " +
                      "WHERE o.payment_status = 'completed' " +
                      "ORDER BY o.order_date DESC";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            Map<Integer, OrderModel> orderMap = new HashMap<>();
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                OrderModel order = orderMap.get(orderId);
                if (order == null) {
                    order = new OrderModel();
                    order.setOrderId(orderId);
                    order.setOrderDate(rs.getTimestamp("order_date"));
                    order.setTotalPrice(rs.getBigDecimal("total_price"));
                    order.setPaymentStatus(rs.getString("payment_status"));
                    order.setItems(new ArrayList<>());
                    orderMap.put(orderId, order);
                }
                OrderItemModel item = new OrderItemModel();
                item.setProductId(rs.getInt("product_id"));
                item.setQuantity(rs.getInt("order_quantity"));
                ProductModel product = new ProductModel();
                product.setProductId(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setImage(rs.getString("image"));
                item.setProduct(product);
                order.getItems().add(item);
            }
            orders.addAll(orderMap.values());
        }
        return orders;
    }

    /**
     * Calculates the total revenue from completed orders in the last 30 days.
     * 
     * @return the total revenue amount
     * @throws SQLException if there is an error during database operations
     */
    public BigDecimal getTotalRevenueLast30Days() throws SQLException {
        String query = "SELECT SUM(total_price) as total_revenue FROM orders " +
                      "WHERE payment_status = 'completed'";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                BigDecimal totalRevenue = rs.getBigDecimal("total_revenue");
                return totalRevenue != null ? totalRevenue : BigDecimal.ZERO;
            }
        }
        return BigDecimal.ZERO;
    }

    /**
     * Calculates the total number of completed orders.
     * 
     * @return the number of orders
     * @throws SQLException if there is an error during database operations
     */
    public int getTotalOrdersLast30Days() throws SQLException {
        String query = "SELECT COUNT(*) as total_orders FROM orders " +
                      "WHERE payment_status = 'completed'";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total_orders");
            }
        }
        return 0;
    }

    /**
     * Counts the number of orders that are pending delivery.
     * 
     * @return the number of pending deliveries
     * @throws SQLException if there is an error during database operations
     */
    public int getPendingDeliveriesCount() throws SQLException {
        String query = "SELECT COUNT(*) as pending_deliveries FROM orders " +
                      "WHERE payment_status != 'completed'";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("pending_deliveries");
            }
        }
        return 0;
    }
} 