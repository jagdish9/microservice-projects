package com.transaction.inventoryservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "t_inventory")
public class Inventory {

    @Id
    private String productId;

    private int stock;
}
