package com.appservice.cqrsservice.query.handler;

import com.appservice.cqrsservice.query.dto.OrderResponse;
import com.appservice.cqrsservice.query.service.OrderQueryService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetOrderHandler {

    private final OrderQueryService orderQueryService;

    public GetOrderHandler(OrderQueryService orderQueryService) {
        this.orderQueryService = orderQueryService;
    }

    public OrderResponse getOrder(Long id) {
        return orderQueryService.getOrder(id);
    }

    public List<OrderResponse> getAllOrders() {
        return orderQueryService.getAllOrders();
    }
}
