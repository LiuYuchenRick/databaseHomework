package com.example.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.InventoryOverview;
import com.example.demo.service.InventoryService;

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
}
