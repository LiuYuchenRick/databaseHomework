package com.example.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;


import com.example.demo.model.Inventory;
import com.example.demo.model.InventoryOverview;
import com.example.demo.service.InventoryService;

import java.util.Map;
import java.util.List;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api/inventory")  // 基础路径
@Slf4j
public class InventoryController {
    
    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/overview")
    public ResponseEntity<InventoryOverview> getInventoryOverview() {
        return ResponseEntity.ok(inventoryService.getInventoryOverview());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Inventory>> getAllInventory() {
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody Inventory product) {
        try {
            inventoryService.addProduct(product);
            return ResponseEntity.ok("添加商品成功");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/order")
    public ResponseEntity<String> createOrder(@RequestBody Map<String, Object> orderRequest) {
        try {
            String productName = (String) orderRequest.get("productName");
            Integer quantity = (Integer) orderRequest.get("quantity");
            Long userId = ((Number) orderRequest.get("userId")).longValue();
            
            String result = inventoryService.createOrder(productName, quantity, userId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/restock/{id}")  // 确保这个路径映射正确
    public ResponseEntity<String> restockProduct(
            @PathVariable Long id,
            @RequestBody Map<String, Integer> restockData) {
        try {
            inventoryService.restockProduct(id, restockData.get("quantity"));
            return ResponseEntity.ok("补货成功");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/top-selling")
    public ResponseEntity<List<Inventory>> getTopSellingProducts() {
        return ResponseEntity.ok(inventoryService.getTopSellingProducts());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        try {
            inventoryService.deleteProduct(id);
            return ResponseEntity.ok("删除成功");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
