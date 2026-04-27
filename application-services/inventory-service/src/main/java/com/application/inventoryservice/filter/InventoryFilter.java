package com.application.inventoryservice.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryFilter {
    private String name;
    private String category;
    private Double minPrice;
    private Double maxPrice;
    private Integer minQuantity;
    private Integer maxQuantity;
}
