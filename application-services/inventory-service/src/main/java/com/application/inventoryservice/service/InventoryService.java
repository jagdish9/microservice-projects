package com.application.inventoryservice.service;

import com.application.inventoryservice.entity.Inventory;
import com.application.inventoryservice.repository.InventoryRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Inventory createInventory(Inventory item) {
        item.setCreatedAt(LocalDateTime.now());
        item.setUpdatedAt(LocalDateTime.now());
        return inventoryRepository.save(item);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Inventory getById(Long id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
    }

    //Get all with pagination
    public Page<Inventory> getAll(int page, int size) {
        return inventoryRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
    }

    //Get all with pagination V2
    public Page<Inventory> getAllV2(int page, int size) {
        return inventoryRepository.findAll(PageRequest.of(page, size,
                Sort.by("quantity").descending()));
    }

    public Inventory updateOrCreate(Long id, Inventory item) {
        return inventoryRepository.findById(id)
                .map(existing -> {
                    existing.setName(item.getName());
                    existing.setQuantity(item.getQuantity());
                    existing.setPrice(item.getPrice());
                    existing.setCategory(item.getCategory());
                    existing.setUpdatedAt(LocalDateTime.now());
                    return inventoryRepository.save(existing);
                })
                .orElseGet(() -> {
                    item.setId(id);
                    return createInventory(item);
                });
    }

    //Patch (Partial update)
    public Inventory patch(Long id, Map<String, Object> updates) {
        Inventory item = getById(id);

        updates.forEach((key, value) -> {
            switch (key) {
                case "name" -> item.setName((String) value);
                case "quantity" -> item.setQuantity((Integer) value);
                case "price" -> item.setPrice((double) value);
                case "category" -> item.setCategory((String) value);
            }
        });

        item.setUpdatedAt(LocalDateTime.now());
        return inventoryRepository.save(item);
    }

    public Inventory delete(Long id) {
        Inventory item = getById(id);
        inventoryRepository.delete(item);
        return item;
    }

    public void deleteAll() {
        inventoryRepository.deleteAll();
    }
}
