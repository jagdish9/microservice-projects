package com.transaction.commonevents;

public class OrderEvent {
    private String orderId;
    private String status;
    private double amount;
    private String itemName;
    private int quantity;

    public OrderEvent() {
    }

    public OrderEvent(String orderId, String status, double amount, String itemName, int quantity) {
        this.orderId = orderId;
        this.status = status;
        this.amount = amount;
        this.itemName = itemName;
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
