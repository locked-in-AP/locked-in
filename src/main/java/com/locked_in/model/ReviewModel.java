package com.locked_in.model;

import java.time.LocalDateTime;

/**
 * ReviewModel represents a product review in the system.
 * 
 * This model class encapsulates all review-related data including:
 * - User information (user ID, user name)
 * - Review content (review text, rating)
 * - Metadata (review date, associated order ID)
 * 
 * The class provides getters and setters for all fields to maintain
 * encapsulation and data integrity.
 */
public class ReviewModel {
    private int userId;
    private String userName;
    private String review;
    private int rating;
    private LocalDateTime reviewDate;
    private int orderId;

    /**
     * Gets the ID of the user who wrote the review.
     *
     * @return the user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the ID of the user who wrote the review.
     *
     * @param userId the user ID to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the name of the user who wrote the review.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the name of the user who wrote the review.
     *
     * @param userName the user name to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the review text content.
     *
     * @return the review text
     */
    public String getReview() {
        return review;
    }

    /**
     * Sets the review text content.
     *
     * @param review the review text to set
     */
    public void setReview(String review) {
        this.review = review;
    }

    /**
     * Gets the numerical rating given in the review.
     *
     * @return the rating value
     */
    public int getRating() {
        return rating;
    }

    /**
     * Sets the numerical rating for the review.
     *
     * @param rating the rating value to set
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Gets the date and time when the review was submitted.
     *
     * @return the review date and time
     */
    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    /**
     * Sets the date and time when the review was submitted.
     *
     * @param reviewDate the review date and time to set
     */
    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }

    /**
     * Gets the ID of the order associated with this review.
     *
     * @return the order ID
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * Sets the ID of the order associated with this review.
     *
     * @param orderId the order ID to set
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
} 