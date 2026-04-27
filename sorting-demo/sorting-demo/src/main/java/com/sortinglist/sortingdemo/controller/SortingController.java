package com.sortinglist.sortingdemo.controller;

import com.sortinglist.sortingdemo.controller.service.SortingService;
import com.sortinglist.sortingdemo.dto.Input;
import com.sortinglist.sortingdemo.dto.Items;
import com.sortinglist.sortingdemo.dto.Order;
import com.sortinglist.sortingdemo.dto.OrderRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/list")
public class SortingController {

    private final SortingService sortingService;

    public SortingController(SortingService sortingService) {
        this.sortingService = sortingService;
    }

    @PostMapping("/sort")
    public List<Integer> sortList(@RequestBody Input inputList) {
        System.out.println(inputList);
        return inputList.getInputList().stream()
                .sorted().collect(Collectors.toList());
    }

    @PostMapping("/process")
    public OrderRequest processOrderRequest(@RequestBody OrderRequest orderRequest) {
        return sortingService.processOrderRequest(orderRequest);
    }

    @PostMapping("/filter-by-amount")
    public OrderRequest filterByAmount(@RequestBody OrderRequest orderRequest) {
        return sortingService.filterByAmount(orderRequest);
    }

    @PostMapping("/flat-items")
    public List<Items> flatItems(@RequestBody OrderRequest orderRequest) {
        return sortingService.flatItems(orderRequest);
    }

    @PostMapping("/total-amount")
    public double totalAmount(@RequestBody OrderRequest orderRequest) {
        return sortingService.totalAmount(orderRequest);
    }

    @PostMapping("/group-by-status")
    public Map<String, List<Order>> groupByStatus(@RequestBody OrderRequest orderRequest) {
        return sortingService.groupByStatus(orderRequest);
    }
}
