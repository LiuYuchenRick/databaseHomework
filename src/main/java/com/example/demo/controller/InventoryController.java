package com.example.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.model.Inventory;
import com.example.demo.model.InventoryOverview;
import com.example.demo.service.InventoryService;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")  // 定义访问路径
public class InventoryController {
    
    @Autowired
    private InventoryService inventoryService;

    @GetMapping  // 处理 GET 请求
    public ResponseEntity<InventoryOverview> getInventoryOverview() {
        InventoryOverview overview = inventoryService.getInventoryOverview();
        return ResponseEntity.ok(overview);  // 返回数据给前端
    }

    @PostMapping("/order")
    public ResponseEntity<String> createOrder(@RequestBody Map<String, Object> orderRequest) {
        String productName = (String) orderRequest.get("productName");
        Integer quantity = Integer.valueOf(orderRequest.get("quantity").toString());
        
        try {
            String result = inventoryService.createOrder(productName, quantity);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Inventory>> getAllInventory() {
        try {
            List<Inventory> inventories = inventoryService.getAllInventory();
            return ResponseEntity.ok(inventories);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/restock/{id}")
    public ResponseEntity<String> restockProduct(@PathVariable Long id, @RequestBody Map<String, Integer> request) {
        try {
            Integer quantity = request.get("quantity");
            inventoryService.restockProduct(id, quantity);
            return ResponseEntity.ok("补货成功");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
}
