package com.example.demo.controller;

import com.example.demo.model.SaleOrder;
import com.example.demo.model.PurchaseRecord;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/orders")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/sales")
    public ResponseEntity<List<SaleOrder>> getAllSalesOrders() {
        try {
            List<SaleOrder> orders = orderService.getAllSalesOrders();
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            log.error("获取销售订单失败", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/purchases")
    public ResponseEntity<List<PurchaseRecord>> getAllPurchaseOrders() {
        try {
            List<PurchaseRecord> records = orderService.getAllPurchaseOrders();
            return ResponseEntity.ok(records);
        } catch (Exception e) {
            log.error("获取采购订单失败", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SaleOrder>> getUserOrders(@PathVariable Long userId) {
        try {
            List<SaleOrder> orders = orderService.getUserOrders(userId);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/total-sales")
    public ResponseEntity<BigDecimal> getTotalSales() {
        return ResponseEntity.ok(orderService.getTotalSales());
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getOrderCount() {
        return ResponseEntity.ok(orderService.getOrderCount());
    }

    @GetMapping("/purchases/count")
    public ResponseEntity<Integer> getPurchaseCount() {
        return ResponseEntity.ok(orderService.getPurchaseCount());
    }
    
} 