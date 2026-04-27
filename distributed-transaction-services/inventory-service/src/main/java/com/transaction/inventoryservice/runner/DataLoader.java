package com.transaction.inventoryservice.runner;

import com.transaction.inventoryservice.entity.Inventory;
import com.transaction.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final InventoryRepository inventoryRepository;

    public DataLoader(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        for(int i = 1; i <= 10; i++) {
            Inventory inventory = new Inventory();
            inventory.setItemName("Samsung phone " + i);
            inventory.setQuantity(10 * i);
            inventoryRepository.save(inventory);
        }
    }
}
