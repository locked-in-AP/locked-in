package com.locked_in.model;

public class CartModel {
    private int userId;
    private int productId;
    private int quantity;
    private boolean isCurrentlyInCart;
    private ProductModel product;

    public CartModel() {
    }

    public CartModel(int userId, int productId, int quantity, boolean isCurrentlyInCart) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.isCurrentlyInCart = isCurrentlyInCart;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isCurrentlyInCart() {
        return isCurrentlyInCart;
    }

    public void setCurrentlyInCart(boolean isCurrentlyInCart) {
        this.isCurrentlyInCart = isCurrentlyInCart;
    }

    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }
} 