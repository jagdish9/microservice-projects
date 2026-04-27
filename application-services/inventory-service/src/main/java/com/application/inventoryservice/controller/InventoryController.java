package com.application.inventoryservice.controller;

import com.application.inventoryservice.entity.Inventory;
import com.application.inventoryservice.service.InventoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/inventory")
@Tag(name = "Inventory API", description = "Operations related to Inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/create-inventory")
    public ResponseEntity<?> createInventory(@RequestBody Inventory item) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryService.createInventory(item));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryService.getById(id));
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<Inventory> result = inventoryService.getAll(page, size);

        return ResponseEntity.ok(Map.of(
                "data", result.getContent(),
                "currentPage", result.getNumber(),
                "totalItems", result.getTotalElements(),
                "totalPages", result.getTotalPages()
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateInventory(@PathVariable Long id, @RequestBody Inventory item) {
        return ResponseEntity.ok(inventoryService.updateOrCreate(id, item));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> path(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates
    ) {
        return ResponseEntity.ok(inventoryService.patch(id, updates));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryService.delete(id));
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<?> deleteAll() {
        inventoryService.deleteAll();
        return ResponseEntity.ok("All items deleted");
    }
}
