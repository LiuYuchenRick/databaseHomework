package com.example.demo.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PurchaseRecord {
    private Long id;
    private String productName;
    private Integer quantity;
    private Long supplierId;
    private LocalDateTime purchaseTime;
    private String supplierName; // 用于显示供应商名称
    
    // 构造函数
    public PurchaseRecord() {}
    
    public PurchaseRecord(String productName, Integer quantity, Long supplierId) {
        this.productName = productName;
        this.quantity = quantity;
        this.supplierId = supplierId;
        this.purchaseTime = LocalDateTime.now();
    }
} 