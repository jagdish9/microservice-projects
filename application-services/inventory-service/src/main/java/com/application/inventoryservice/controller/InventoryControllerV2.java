package com.application.inventoryservice.controller;

import com.application.inventoryservice.entity.Inventory;
import com.application.inventoryservice.service.InventoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v2/inventory")
@Tag(name = "Inventory API", description = "Operations related to inventory")
public class InventoryControllerV2 {
    private final InventoryService inventoryService;

    public InventoryControllerV2(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<Inventory> result = inventoryService.getAllV2(page, size);

        return ResponseEntity.ok(Map.of(
           "data", result.getContent(),
           "currentPage", result.getNumber(),
           "totalItems", result.getTotalElements(),
           "totalPages", result.getTotalPages()
        ));
    }
}
