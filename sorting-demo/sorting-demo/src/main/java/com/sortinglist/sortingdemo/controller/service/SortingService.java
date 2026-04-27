package com.sortinglist.sortingdemo.controller.service;

import com.sortinglist.sortingdemo.constants.OrderStatus;
import com.sortinglist.sortingdemo.dto.Items;
import com.sortinglist.sortingdemo.dto.Order;
import com.sortinglist.sortingdemo.dto.OrderRequest;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SortingService {

    public OrderRequest processOrderRequest(OrderRequest orderRequest) {
        /*
        Step 1: Filter only SUCCESS orders
        Step 2: Sort by amount DESC
        Step 3: Sort items inside each order by price ASC
        Step 4: Set processed orders back
         */

        List<Order> orderList = orderRequest.getOrders()
                .stream()
                .filter(order -> order.getStatus().equals(OrderStatus.SUCCESS.name()))
                .sorted(Comparator.comparingDouble(Order::getAmount).reversed())
                .collect(Collectors.toList());

        orderList.forEach(order -> {
            List<Items> sortedItems = order.getItems().stream()
                    .sorted(Comparator.comparingDouble(Items::getPrice))
                    .collect(Collectors.toList());

            order.setItems(sortedItems);
        });

        orderRequest.setOrders(orderList);

        return orderRequest;
    }

    //filter by amount, amount > 3000
    public OrderRequest filterByAmount(OrderRequest orderRequest) {
        List<Order> orderList = orderRequest.getOrders()
                .stream()
                .filter(order -> order.getAmount() > 3000)
                .toList();

        orderRequest.setOrders(orderList);

        return orderRequest;
    }

    //Flatten all items across orders
    public List<Items> flatItems(OrderRequest orderRequest) {
        return orderRequest.getOrders()
                .stream()
                .flatMap(order -> order.getItems().stream())
                .toList();
    }

    //Get total amount
    public double totalAmount(OrderRequest orderRequest) {
        return orderRequest.getOrders()
                .stream()
                .mapToDouble(Order::getAmount)
                .sum();
    }

    //Group by status
    public Map<String, List<Order>> groupByStatus(OrderRequest orderRequest) {
        return orderRequest.getOrders()
                .stream()
                .collect(Collectors.groupingBy(Order::getStatus));
    }

}
