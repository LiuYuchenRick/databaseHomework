package com.example.demo.model;

public class InventoryOverview {
    private int totalStock;  // 总库存
    private int lowStock;    // 低库存
    private int outOfStock;  // 缺货

    // 构造函数
    public InventoryOverview(int totalStock, int lowStock, int outOfStock) {
        this.totalStock = totalStock;
        this.lowStock = lowStock;
        this.outOfStock = outOfStock;
    }

    // Getter 和 Setter
    // 用来读取和修改数据
    public int getTotalStock() { return totalStock; }
    public void setTotalStock(int totalStock) { this.totalStock = totalStock; }

    public int getLowStock() { return lowStock; }
    public void setLowStock(int lowStock) { this.lowStock = lowStock; }

    public int getOutOfStock() { return outOfStock; }
    public void setOutOfStock(int outOfStock) { this.outOfStock = outOfStock; }
}