package com.locked_in.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * ProductModel represents a product in the system with all its details.
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

    /**
     * Full constructor for ProductModel
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
    }

    /**
     * Constructor for creating new products
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
    }

    // Getters and Setters
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
} 