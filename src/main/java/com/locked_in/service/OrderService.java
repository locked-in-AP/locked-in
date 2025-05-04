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

import com.locked_in.model.CartModel;
import com.locked_in.config.DbConfig;
import com.locked_in.model.OrderModel;
import com.locked_in.model.OrderItemModel;
import com.locked_in.model.ProductModel;

public class OrderService {
    private Connection connection;
    private CartService cartService;

    public OrderService() {
        try {
            this.connection = DbConfig.getDbConnection();
            this.cartService = new CartService();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("Database connection failed in OrderService: " + ex.getMessage());
        }
    }

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

    public List<OrderModel> getUserOrders(int userId) throws SQLException {
        List<OrderModel> orders = new ArrayList<>();
        String query = "SELECT o.*, upo.product_id, upo.order_quantity, p.* " +
                      "FROM orders o " +
                      "JOIN user_product_order upo ON o.order_id = upo.order_id " +
                      "JOIN product p ON upo.product_id = p.product_id " +
                      "WHERE upo.user_id = ? " +
                      "ORDER BY o.order_date DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
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
                product.setPrice(rs.getBigDecimal("price"));
                product.setImage(rs.getString("image"));
                
                item.setProduct(product);
                order.getItems().add(item);
            }
            
            orders.addAll(orderMap.values());
        }
        
        return orders;
    }

    public boolean updateOrderStatus(int orderId, String status) throws SQLException {
        String query = "UPDATE orders SET payment_status = ? WHERE order_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setInt(2, orderId);
            return stmt.executeUpdate() > 0;
        }
    }
} 