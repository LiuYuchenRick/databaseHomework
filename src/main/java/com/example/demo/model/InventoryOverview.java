package com.example.demo.model;

import java.math.BigDecimal;

public class InventoryOverview {
    private int totalStock;      // 总库存
    private int totalSales;      // 总销售量
    private BigDecimal totalAmount;  // 总销售额

    // 构造函数
    public InventoryOverview(int totalStock, int totalSales, BigDecimal totalAmount) {
        this.totalStock = totalStock;
        this.totalSales = totalSales;
        this.totalAmount = totalAmount;
    }

    // Getter 和 Setter
    public int getTotalStock() { return totalStock; }
    public void setTotalStock(int totalStock) { this.totalStock = totalStock; }

    public int getTotalSales() { return totalSales; }
    public void setTotalSales(int totalSales) { this.totalSales = totalSales; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
}