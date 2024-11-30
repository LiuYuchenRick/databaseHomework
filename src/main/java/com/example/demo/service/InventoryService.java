package com.example.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.InventoryOverview;
import com.example.demo.repository.InventoryRepository;
import com.example.demo.model.Inventory;
import com.example.demo.model.SaleOrder;
import com.example.demo.repository.SaleOrderRepository;

import java.util.List;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private SaleOrderRepository saleOrderRepository;

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

    @Transactional
    public String createOrder(String productName, Integer quantity) {
        try {
            Inventory inventory = inventoryRepository.findByName(productName)
                .orElseThrow(() -> new RuntimeException("商品不存在"));
            
            if (inventory.getStock() < quantity) {
                return "库存不足";
            }
            
            // 减少库存
            inventory.setStock(inventory.getStock() - quantity);
            // 增加销量
            inventory.setSalesCount(inventory.getSalesCount() + quantity);
            inventoryRepository.save(inventory);
            
            // 创建订单
            SaleOrder order = new SaleOrder(productName, quantity);
            saleOrderRepository.save(order);
            
            return "下单成功";
        } catch (Exception e) {
            throw new RuntimeException("下单失败：" + e.getMessage());
        }
    }

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    @Transactional
    public void restockProduct(Long id, Integer quantity) {
        Inventory inventory = inventoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("商品不存在"));
        inventory.setStock(inventory.getStock() + quantity);
        inventoryRepository.save(inventory);
    }

    @Transactional
    public void addProduct(Inventory product) {
        // 检查商品名是否已存在
        if (inventoryRepository.findByName(product.getName()).isPresent()) {
            throw new RuntimeException("商品名称已存在");
        }
        
        // 设置初始销量为0
        product.setSalesCount(0);
        inventoryRepository.save(product);
    }
}
