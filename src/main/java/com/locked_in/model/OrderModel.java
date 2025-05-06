package com.locked_in.model;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

public class OrderModel {
    private int orderId;
    private Timestamp orderDate;
    private BigDecimal totalPrice;
    private String paymentStatus;
    private List<OrderItemModel> items;

    public OrderModel() {
        this.items = new ArrayList<>();
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public List<OrderItemModel> getItems() {
        return items;
    }

    public void setItems(List<OrderItemModel> items) {
        this.items = items;
    }

    public void addItem(OrderItemModel item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(item);
    }
} 