package com.example.demo.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;


@Table(name = "sale_orders")
public class SaleOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String productName;
    private Integer quantity;
    private Long userId;
    private LocalDateTime orderTime;
    private Double price;  // 从 Inventory 表获取的单价
    public SaleOrder() {}
    
    public SaleOrder(String productName, Integer quantity, Long userId) {
        this.productName = productName;
        this.quantity = quantity;
        this.userId = userId;
        this.orderTime = LocalDateTime.now();
    }
    
    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public LocalDateTime getOrderTime() { return orderTime; }
    public void setOrderTime(LocalDateTime orderTime) { this.orderTime = orderTime; }
    
    public Double getPrice() { 
        return price; 
    }
    
    public void setPrice(Double price) { 
        this.price = price; 
    }
}
