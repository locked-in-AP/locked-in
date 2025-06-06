package com.locked_in.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.locked_in.config.DbConfig;
import com.locked_in.model.ReviewModel;

/**
 * Service class for handling product review operations.
 * 
 * This service class manages all aspects of product reviews including:
 * - Review creation and retrieval
 * - Rating calculations
 * - Review validation
 * - User review history
 * 
 * The class maintains database connections and ensures data integrity
 * for all review-related operations.
 */
public class ReviewService {
    private Connection connection;
    private boolean isConnectionError;

    /**
     * Creates a new ReviewService instance.
     * 
     * Initializes the database connection and sets the connection error flag
     * if the connection fails.
     */
    public ReviewService() {
        try {
            connection = DbConfig.getDbConnection();
            isConnectionError = false;
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
            isConnectionError = true;
        }
    }

    /**
     * Retrieves all reviews for a specific product.
     * 
     * Reviews are returned with complete details including:
     * - User information (ID, name)
     * - Review content (text, rating)
     * - Temporal information (review date)
     * - Order reference
     * 
     * Reviews are sorted by date in descending order.
     * 
     * @param productId the ID of the product whose reviews to retrieve
     * @return List of reviews with complete details
     */
    public List<ReviewModel> getProductReviews(int productId) {
        List<ReviewModel> reviews = new ArrayList<>();
        if (isConnectionError) {
            System.err.println("Cannot fetch reviews: Database connection error");
            return reviews;
        }

        String sql = "SELECT upo.*, u.name as user_name " +
                    "FROM user_product_order upo " +
                    "JOIN users u ON upo.user_id = u.user_id " +
                    "WHERE upo.product_id = ? AND upo.review IS NOT NULL " +
                    "ORDER BY upo.review_date DESC";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            
            var rs = pstmt.executeQuery();
            while (rs.next()) {
                ReviewModel review = new ReviewModel();
                review.setUserId(rs.getInt("user_id"));
                review.setUserName(rs.getString("user_name"));
                review.setReview(rs.getString("review"));
                review.setRating(rs.getInt("rating"));
                review.setOrderId(rs.getInt("order_id"));
                if (rs.getTimestamp("review_date") != null) {
                    review.setReviewDate(rs.getTimestamp("review_date").toLocalDateTime());
                }
                reviews.add(review);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching product reviews: " + e.getMessage());
            e.printStackTrace();
        }
        return reviews;
    }

    /**
     * Checks if a user has ordered and completed payment for a product.
     * 
     * This method is used to validate if a user is eligible to review
     * a product based on their purchase history.
     * 
     * @param userId the ID of the user to check
     * @param productId the ID of the product to check
     * @return true if the user has completed an order for the product
     */
    public boolean hasUserOrderedProduct(int userId, int productId) {
        if (isConnectionError) {
            System.err.println("Cannot check order status: Database connection error");
            return false;
        }

        String sql = "SELECT o.payment_status " +
                    "FROM user_product_order upo " +
                    "JOIN orders o ON upo.order_id = o.order_id " +
                    "WHERE upo.user_id = ? AND upo.product_id = ? " +
                    "AND o.payment_status = 'completed'";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, productId);
            
            var rs = pstmt.executeQuery();
            return rs.next(); // Returns true if user has ordered the product
        } catch (SQLException e) {
            System.err.println("Error checking if user ordered product: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Calculates the average rating for a product.
     * 
     * Only considers reviews that have a rating value.
     * Returns null if there are no ratings or if there's a database error.
     * 
     * @param productId the ID of the product to calculate rating for
     * @return the average rating, or null if no ratings exist
     */
    public Double getAverageRating(int productId) {
        if (isConnectionError) {
            System.err.println("Cannot calculate average rating: Database connection error");
            return null;
        }

        String sql = "SELECT AVG(rating) as avg_rating " +
                    "FROM user_product_order " +
                    "WHERE product_id = ? AND rating IS NOT NULL";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            
            var rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("avg_rating");
            }
        } catch (SQLException e) {
            System.err.println("Error calculating average rating: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Adds a new review for a product.
     * 
     * The review is associated with a specific order and includes:
     * - Review text
     * - Numerical rating
     * - Timestamp
     * 
     * @param userId the ID of the user submitting the review
     * @param productId the ID of the product being reviewed
     * @param orderId the ID of the order associated with the review
     * @param review the review text
     * @param rating the numerical rating
     * @return true if the review was added successfully
     */
    public boolean addReview(int userId, int productId, int orderId, String review, int rating) {
        if (isConnectionError) {
            System.err.println("Cannot add review: Database connection error");
            return false;
        }

        String sql = "UPDATE user_product_order SET review = ?, rating = ?, review_date = ? " +
                    "WHERE user_id = ? AND product_id = ? AND order_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, review);
            pstmt.setInt(2, rating);
            pstmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setInt(4, userId);
            pstmt.setInt(5, productId);
            pstmt.setInt(6, orderId);

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("ReviewService - Added review for order " + orderId + 
                             ", product " + productId + " by user " + userId + 
                             ". Rows affected: " + rowsAffected);
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error adding review: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks if a user has already reviewed a product for a specific order.
     * 
     * This method is used to prevent duplicate reviews for the same
     * product and order combination.
     * 
     * @param userId the ID of the user to check
     * @param productId the ID of the product to check
     * @param orderId the ID of the order to check
     * @return true if the user has already submitted a review
     */
    public boolean hasReviewed(int userId, int productId, int orderId) {
        if (isConnectionError) {
            System.err.println("Cannot check review status: Database connection error");
            return false;
        }

        String sql = "SELECT review FROM user_product_order " +
                    "WHERE user_id = ? AND product_id = ? AND order_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, productId);
            pstmt.setInt(3, orderId);

            var rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("review") != null;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error checking review status: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
} 