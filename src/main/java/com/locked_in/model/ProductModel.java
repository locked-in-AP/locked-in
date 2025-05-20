package com.locked_in.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

/**
 * ProductModel represents a product in the e-commerce system.
 * 
 * This model class encapsulates all product-related data including:
 * - Basic information (name, description, brand)
 * - Categorization (category, tags)
 * - Pricing and inventory (price, stock quantity)
 * - Physical attributes (weight, dimensions)
 * - Media (image)
 * - Metadata (product ID, creation date)
 * - User interaction (reviews, ratings, order status)
 * 
 * The class provides constructors for different product creation scenarios
 * and maintains default values for new products.
 */
public class ProductModel {
    private Integer productId;
    private String name;
    private String description;
    private String brand;
    private String category;
    private String tags;
    private BigDecimal price;
    private Integer stockQuantity;
    private BigDecimal weight;
    private String image;
    private String dimensions;
    private LocalDateTime createdAt;
    private List<ReviewModel> reviews;
    private Double averageRating;
    private boolean hasUserReviewed;
    private boolean hasUserOrdered;

    /**
     * Creates an empty product model with default values.
     * 
     * Initializes the creation date to current time and
     * creates an empty list for reviews.
     */
    public ProductModel() {
        this.createdAt = LocalDateTime.now();
        this.reviews = new ArrayList<>();
    }

    /**
     * Creates a complete product model with all fields specified.
     * 
     * Used when retrieving a product from the database or creating a product
     * with all information available. Initializes an empty list for reviews.
     *
     * @param productId     the unique identifier for the product
     * @param name          the product name
     * @param description   the product description
     * @param brand         the product brand
     * @param category      the product category
     * @param tags          the product tags
     * @param price         the product price
     * @param stockQuantity the quantity in stock
     * @param weight        the product weight
     * @param image         the product image path
     * @param dimensions    the product dimensions
     * @param createdAt     the creation date and time
     */
    public ProductModel(Integer productId, String name, String description, String brand, 
                       String category, String tags, BigDecimal price, Integer stockQuantity,
                       BigDecimal weight, String image, String dimensions, LocalDateTime createdAt) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.category = category;
        this.tags = tags;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.weight = weight;
        this.image = image;
        this.dimensions = dimensions;
        this.createdAt = createdAt;
        this.reviews = new ArrayList<>();
    }

    /**
     * Creates a new product model for product creation.
     * 
     * Used when adding a new product to the system. Sets the creation date
     * to current time and initializes an empty list for reviews.
     *
     * @param name          the product name
     * @param description   the product description
     * @param brand         the product brand
     * @param category      the product category
     * @param tags          the product tags
     * @param price         the product price
     * @param stockQuantity the quantity in stock
     * @param weight        the product weight
     * @param image         the product image path
     * @param dimensions    the product dimensions
     */
    public ProductModel(String name, String description, String brand, String category,
                       String tags, BigDecimal price, Integer stockQuantity, BigDecimal weight,
                       String image, String dimensions) {
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.category = category;
        this.tags = tags;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.weight = weight;
        this.image = image;
        this.dimensions = dimensions;
        this.createdAt = LocalDateTime.now();
        this.reviews = new ArrayList<>();
    }

    /**
     * Gets the product's unique identifier.
     *
     * @return the product ID
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * Sets the product's unique identifier.
     *
     * @param productId the product ID to set
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * Gets the product name.
     *
     * @return the product name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the product name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the product description.
     *
     * @return the product description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the product description.
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the product brand.
     *
     * @return the product brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Sets the product brand.
     *
     * @param brand the brand to set
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Gets the product category.
     *
     * @return the product category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the product category.
     *
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets the product tags.
     *
     * @return the product tags
     */
    public String getTags() {
        return tags;
    }

    /**
     * Sets the product tags.
     *
     * @param tags the tags to set
     */
    public void setTags(String tags) {
        this.tags = tags;
    }

    /**
     * Gets the product price.
     *
     * @return the product price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets the product price.
     *
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Gets the product stock quantity.
     *
     * @return the stock quantity
     */
    public Integer getStockQuantity() {
        return stockQuantity;
    }

    /**
     * Sets the product stock quantity.
     *
     * @param stockQuantity the stock quantity to set
     */
    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    /**
     * Gets the product weight.
     *
     * @return the product weight
     */
    public BigDecimal getWeight() {
        return weight;
    }

    /**
     * Sets the product weight.
     *
     * @param weight the weight to set
     */
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    /**
     * Gets the product image path.
     *
     * @return the image path
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the product image path.
     *
     * @param image the image path to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Gets the product dimensions.
     *
     * @return the product dimensions
     */
    public String getDimensions() {
        return dimensions;
    }

    /**
     * Sets the product dimensions.
     *
     * @param dimensions the dimensions to set
     */
    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    /**
     * Gets the product creation date and time.
     *
     * @return the creation date and time
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the product creation date and time.
     *
     * @param createdAt the creation date and time to set
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the list of product reviews.
     *
     * @return the list of reviews
     */
    public List<ReviewModel> getReviews() {
        return reviews;
    }

    /**
     * Sets the list of product reviews.
     *
     * @param reviews the list of reviews to set
     */
    public void setReviews(List<ReviewModel> reviews) {
        this.reviews = reviews;
    }

    /**
     * Gets the product's average rating.
     *
     * @return the average rating
     */
    public Double getAverageRating() {
        return averageRating;
    }

    /**
     * Sets the product's average rating.
     *
     * @param averageRating the average rating to set
     */
    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    /**
     * Checks if the current user has reviewed this product.
     *
     * @return true if the user has reviewed the product, false otherwise
     */
    public boolean isHasUserReviewed() {
        return hasUserReviewed;
    }

    /**
     * Sets whether the current user has reviewed this product.
     *
     * @param hasUserReviewed the review status to set
     */
    public void setHasUserReviewed(boolean hasUserReviewed) {
        this.hasUserReviewed = hasUserReviewed;
    }

    /**
     * Checks if the current user has ordered this product.
     *
     * @return true if the user has ordered the product, false otherwise
     */
    public boolean isHasUserOrdered() {
        return hasUserOrdered;
    }

    /**
     * Sets whether the current user has ordered this product.
     *
     * @param hasUserOrdered the order status to set
     */
    public void setHasUserOrdered(boolean hasUserOrdered) {
        this.hasUserOrdered = hasUserOrdered;
    }
} 