package com.locked_in.model;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

/**
 * OrderModel represents a customer order in the system.
 * 
 * This model class encapsulates all order-related data including:
 * - Order identification (order ID)
 * - Temporal information (order date)
 * - Financial details (total price, payment status)
 * - Order contents (list of order items)
 * 
 * The class provides methods to manage order items and maintains
 * encapsulation through getters and setters.
 */
public class OrderModel {
    private int orderId;
    private Timestamp orderDate;
    private BigDecimal totalPrice;
    private String paymentStatus;
    private List<OrderItemModel> items;

    /**
     * Creates an empty order with an initialized items list.
     * 
     * Initializes an empty ArrayList for order items to prevent null pointer
     * exceptions when adding items later.
     */
    public OrderModel() {
        this.items = new ArrayList<>();
    }

    /**
     * Gets the unique identifier for this order.
     *
     * @return the order ID
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * Sets the unique identifier for this order.
     *
     * @param orderId the order ID to set
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /**
     * Gets the date and time when the order was placed.
     *
     * @return the order date and time
     */
    public Timestamp getOrderDate() {
        return orderDate;
    }

    /**
     * Sets the date and time when the order was placed.
     *
     * @param orderDate the order date and time to set
     */
    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * Gets the total price of the order.
     *
     * @return the total price
     */
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets the total price of the order.
     *
     * @param totalPrice the total price to set
     */
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Gets the current payment status of the order.
     *
     * @return the payment status
     */
    public String getPaymentStatus() {
        return paymentStatus;
    }

    /**
     * Sets the payment status of the order.
     *
     * @param paymentStatus the payment status to set
     */
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    /**
     * Gets the list of items in this order.
     *
     * @return the list of order items
     */
    public List<OrderItemModel> getItems() {
        return items;
    }

    /**
     * Sets the list of items for this order.
     *
     * @param items the list of order items to set
     */
    public void setItems(List<OrderItemModel> items) {
        this.items = items;
    }

    /**
     * Adds an item to the order.
     * 
     * If the items list is null, initializes it before adding the item.
     * This ensures the items list is always available for adding items.
     *
     * @param item the order item to add
     */
    public void addItem(OrderItemModel item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(item);
    }
} 