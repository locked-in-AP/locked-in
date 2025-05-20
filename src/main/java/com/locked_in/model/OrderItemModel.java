package com.locked_in.model;

import java.time.LocalDateTime;

/**
 * OrderItemModel represents an individual item within a customer order.
 * 
 * This model class encapsulates all order item-related data including:
 * - Product information (product ID, product details)
 * - Order details (quantity)
 * - Review information (review text, rating, review date)
 * 
 * The class maintains encapsulation through getters and setters and
 * provides a way to associate products with their reviews.
 */
public class OrderItemModel {
    private int productId;
    private int quantity;
    private ProductModel product;
    private String review;
    private Integer rating;
    private LocalDateTime reviewDate;

    /**
     * Gets the ID of the product in this order item.
     *
     * @return the product ID
     */
    public int getProductId() {
        return productId;
    }

    /**
     * Sets the ID of the product in this order item.
     *
     * @param productId the product ID to set
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     * Gets the quantity of the product ordered.
     *
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the product ordered.
     *
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the complete product details for this order item.
     *
     * @return the product model containing all product information
     */
    public ProductModel getProduct() {
        return product;
    }

    /**
     * Sets the complete product details for this order item.
     *
     * @param product the product model to set
     */
    public void setProduct(ProductModel product) {
        this.product = product;
    }

    /**
     * Gets the review text for this order item.
     *
     * @return the review text, or null if no review exists
     */
    public String getReview() {
        return review;
    }

    /**
     * Sets the review text for this order item.
     *
     * @param review the review text to set
     */
    public void setReview(String review) {
        this.review = review;
    }

    /**
     * Gets the rating given for this order item.
     *
     * @return the rating value, or null if no rating exists
     */
    public Integer getRating() {
        return rating;
    }

    /**
     * Sets the rating for this order item.
     *
     * @param rating the rating value to set
     */
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    /**
     * Gets the date and time when the review was submitted.
     *
     * @return the review date and time, or null if no review exists
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
} 