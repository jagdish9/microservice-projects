package com.retrymechanism.orderservice.controller;

import com.retrymechanism.commonevents.dto.OrderDto;
import com.retrymechanism.orderservice.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody OrderDto dto) {
        return ResponseEntity.ok(orderService.createOrder(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get
            (@PathVariable Long id) {
        return ResponseEntity.ok(orderService.get(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody OrderDto dto, @PathVariable Long id) {
        return ResponseEntity.ok(orderService.update(dto, id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patch(@PathVariable Long id, @PathVariable double price) {
        return ResponseEntity.ok(orderService.patch(price, id));
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        return orderService.deleteById(id);
    }

    @GetMapping("/test/{id}")
    public double price(@PathVariable Long id) {
        return orderService.price(id);
    }
}
