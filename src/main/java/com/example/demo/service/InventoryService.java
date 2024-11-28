package com.example.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.InventoryOverview;
import com.example.demo.repository.InventoryRepository;
import com.example.demo.model.Inventory;

import java.util.List;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    public InventoryOverview getInventoryOverview() {
        List<Inventory> inventories = inventoryRepository.findAll();
        
        int totalStock = inventories.stream()
            .mapToInt(Inventory::getStock)
            .sum();
            
        int lowStock = (int) inventories.stream()
            .filter(i -> i.getStock() < i.getMinStock())
            .count();
            
        int outOfStock = (int) inventories.stream()
            .filter(i -> i.getStock() == 0)
            .count();
            
        return new InventoryOverview(totalStock, lowStock, outOfStock);
    }
}
