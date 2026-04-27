package com.sortinglist.sortingdemo.dto;

import java.util.List;

public class OrderRequest {
    private String customerId;
    private List<Order> orders;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
