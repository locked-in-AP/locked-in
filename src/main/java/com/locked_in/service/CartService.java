package com.locked_in.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.locked_in.model.CartModel;
import com.locked_in.model.ProductModel;
import com.locked_in.config.DbConfig;

/**
 * Service class for handling shopping cart operations.
 * 
 * This service class manages all aspects of the shopping cart including:
 * - Cart item management (add, remove, update)
 * - Stock validation
 * - Cart size tracking
 * - Product availability checks
 * 
 * The class maintains database connections and collaborates with
 * ProductService for stock validation and product information.
 */
public class CartService {
    private Connection connection;
    private ProductService productService;

    /**
     * Creates a new CartService instance.
     * 
     * Initializes the database connection and ProductService.
     * Logs any connection errors that occur during initialization.
     */
    public CartService() {
        try {
            this.connection = DbConfig.getDbConnection();
            this.productService = new ProductService();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("Database connection failed in CartService: " + ex.getMessage());
        }
    }

    /**
     * Retrieves all items in a user's cart.
     * 
     * For each cart item, this method:
     * 1. Gets the basic cart item information
     * 2. Retrieves the complete product details
     * 3. Combines them into a CartModel object
     * 
     * @param userId the ID of the user whose cart to retrieve
     * @return List of cart items with complete product details
     * @throws SQLException if there is an error during database operations
     */
    public List<CartModel> getCartItems(int userId) throws SQLException {
        List<CartModel> cartItems = new ArrayList<>();
        String query = "SELECT * FROM user_product WHERE user_id = ? AND is_currently_in_cart = true";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                CartModel cartItem = new CartModel(
                    rs.getInt("user_id"),
                    rs.getInt("product_id"),
                    rs.getInt("quantity"),
                    rs.getBoolean("is_currently_in_cart")
                );
                
                // Get the product details
                ProductModel product = productService.getProductById(rs.getInt("product_id"));
                cartItem.setProduct(product);
                
                cartItems.add(cartItem);
            }
        }
        
        return cartItems;
    }

    /**
     * Adds a product to the user's cart.
     * 
     * This method performs the following steps:
     * 1. Validates product stock availability
     * 2. Checks if the product is already in the cart
     * 3. Updates quantity if product exists, or adds new item
     * 4. Updates the user's cart size
     * 
     * @param userId the ID of the user
     * @param productId the ID of the product to add
     * @param quantity the quantity to add
     * @return true if the product was added successfully
     * @throws SQLException if there is an error during database operations
     */
    public boolean addToCart(int userId, int productId, int quantity) throws SQLException {
        boolean success = false;
        
        // Check if product exists and has enough stock
        if (!productService.hasAvailableStock(productId, quantity)) {
            return false;
        }
        
        String checkQuery = "SELECT quantity, is_currently_in_cart FROM user_product WHERE user_id = ? AND product_id = ?";
        String updateQuery = "UPDATE user_product SET quantity = ?, is_currently_in_cart = TRUE WHERE user_id = ? AND product_id = ?";
        String insertQuery = "INSERT INTO user_product (user_id, product_id, quantity, is_currently_in_cart) VALUES (?, ?, ?, TRUE)";
        String updateCartSizeQuery = "UPDATE users SET cart_size = cart_size + 1 WHERE user_id = ?";
        
        try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
            checkStmt.setInt(1, userId);
            checkStmt.setInt(2, productId);
            
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    // Product exists in user_product table, update quantity and set in cart
                    int currentQuantity = rs.getInt("quantity");
                    int newQuantity = currentQuantity + quantity;
                    
                    if (!productService.hasAvailableStock(productId, newQuantity)) {
                        return false;
                    }
                    
                    try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                        updateStmt.setInt(1, newQuantity);
                        updateStmt.setInt(2, userId);
                        updateStmt.setInt(3, productId);
                        success = updateStmt.executeUpdate() > 0;
                        
                        // Only update cart size if the item wasn't already in cart
                        if (success && !rs.getBoolean("is_currently_in_cart")) {
                            try (PreparedStatement updateCartSizeStmt = connection.prepareStatement(updateCartSizeQuery)) {
                                updateCartSizeStmt.setInt(1, userId);
                                updateCartSizeStmt.executeUpdate();
                            }
                        }
                    }
                } else {
                    // New product, insert into cart
                    try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                        insertStmt.setInt(1, userId);
                        insertStmt.setInt(2, productId);
                        insertStmt.setInt(3, quantity);
                        success = insertStmt.executeUpdate() > 0;
                        
                        if (success) {
                            // Update cart size
                            try (PreparedStatement updateCartSizeStmt = connection.prepareStatement(updateCartSizeQuery)) {
                                updateCartSizeStmt.setInt(1, userId);
                                updateCartSizeStmt.executeUpdate();
                            }
                        }
                    }
                }
            }
        }
        
        return success;
    }

    /**
     * Removes a product from the user's cart.
     * 
     * This method:
     * 1. Deletes the cart item
     * 2. Updates the user's cart size
     * 3. Ensures cart size never goes below zero
     * 
     * @param userId the ID of the user
     * @param productId the ID of the product to remove
     * @throws SQLException if there is an error during database operations
     */
    public void removeFromCart(int userId, int productId) throws SQLException {
        String deleteQuery = "DELETE FROM user_product WHERE user_id = ? AND product_id = ? AND is_currently_in_cart = true";
        String updateCartSizeQuery = "UPDATE users SET cart_size = GREATEST(cart_size - 1, 0) WHERE user_id = ?";
        
        try (PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)) {
            deleteStmt.setInt(1, userId);
            deleteStmt.setInt(2, productId);
            int rowsAffected = deleteStmt.executeUpdate();
            
            // Only update cart size if rows were actually deleted
            if (rowsAffected > 0) {
                try (PreparedStatement updateCartSizeStmt = connection.prepareStatement(updateCartSizeQuery)) {
                    updateCartSizeStmt.setInt(1, userId);
                    updateCartSizeStmt.executeUpdate();
                }
            }
        }
    }

    /**
     * Updates the quantity of a product in the user's cart.
     * 
     * This method:
     * 1. Validates that the new quantity is available in stock
     * 2. Updates the quantity in the cart
     * 
     * @param userId the ID of the user
     * @param productId the ID of the product to update
     * @param quantity the new quantity
     * @return true if the quantity was updated successfully
     * @throws SQLException if there is an error during database operations
     */
    public boolean updateCartItemQuantity(int userId, int productId, int quantity) throws SQLException {
        // Check if product has enough stock
        if (!productService.hasAvailableStock(productId, quantity)) {
            return false;
        }

        String updateQuery = "UPDATE user_product SET quantity = ? WHERE user_id = ? AND product_id = ? AND is_currently_in_cart = true";
        
        try (PreparedStatement stmt = connection.prepareStatement(updateQuery)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, userId);
            stmt.setInt(3, productId);
            stmt.executeUpdate();
            return true;
        }
    }

    /**
     * Gets the current size of a user's cart.
     * 
     * The cart size represents the number of unique products
     * currently in the user's cart.
     * 
     * @param userId the ID of the user
     * @return the number of items in the cart
     * @throws SQLException if there is an error during database operations
     */
    public int getCartSize(int userId) throws SQLException {
        String query = "SELECT cart_size FROM users WHERE user_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("cart_size");
            }
        }
        
        return 0;
    }
} 