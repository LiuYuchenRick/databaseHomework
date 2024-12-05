package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.InventoryOverview;
import com.example.demo.model.Inventory;
import com.example.demo.model.SaleOrder;
import com.example.demo.mapper.InventoryMapper;
import com.example.demo.mapper.SaleOrderMapper;
import com.example.demo.mapper.PurchaseRecordMapper;
import com.example.demo.mapper.SupplierMapper;
import com.example.demo.model.PurchaseRecord;

import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InventoryService {
    @Autowired
    private InventoryMapper inventoryMapper;
    
    @Autowired
    private SaleOrderMapper saleOrderMapper;
    
    @Autowired
    private PurchaseRecordMapper purchaseRecordMapper;
    
    @Autowired
    private SupplierMapper supplierMapper;



    public InventoryOverview getInventoryOverview() {
        Map<String, Object> result = inventoryMapper.getInventoryOverview();
        return new InventoryOverview(
            ((Number) result.get("total_stock")).intValue(),
            ((Number) result.get("total_sales")).intValue(),
            new BigDecimal(result.get("total_amount").toString())
        );
    }

    @Transactional
    public String createOrder(String productName, Integer quantity, Long userId) {
        try {
            Inventory inventory = inventoryMapper.findByName(productName);
            if (inventory == null) {
                return "商品不存在";
            }
            
            if (inventory.getStock() < quantity) {
                return "库存不足";
            }
            
            // 创建订单
            SaleOrder order = new SaleOrder();
            order.setProductName(productName);
            order.setQuantity(quantity);
            order.setUserId(userId);
            order.setOrderTime(LocalDateTime.now());
            saleOrderMapper.insert(order);
            
            // 减少库存和增加销量
            inventory.setStock(inventory.getStock() - quantity);
            inventory.setSalesCount(inventory.getSalesCount() + quantity);
            
            // 如果更新后的库存小于最小库存，直接在这里补货
            if (inventory.getStock() < inventory.getMinStock()) {
                Long supplierId = supplierMapper.findSupplierIdByProductName(productName);
                if (supplierId != null) {
                    inventory.setStock(inventory.getStock() + 100);
                    // 记录采购信息
                    PurchaseRecord record = new PurchaseRecord(
                        productName,
                        100,
                        supplierId
                    );
                    purchaseRecordMapper.insert(record);
                }
            }
            
            inventoryMapper.update(inventory);
            return "下单成功";
        } catch (Exception e) {
            log.error("下单失败", e);
            throw new RuntimeException("下单失败：" + e.getMessage());
        }
    }

    public List<Inventory> getAllInventory() {
        return inventoryMapper.findAll();
    }

    @Transactional
    public void restockProduct(Long id, int quantity) {
        // 获取商品信息
        Inventory product = inventoryMapper.findById(id);
        if (product == null) {
            throw new RuntimeException("商品不存在");
        }
        
        // 获取供应商信息
        Long supplierId = supplierMapper.findSupplierIdByProductName(product.getName());
        if (supplierId == null) {
            throw new RuntimeException("未找到该商品的供应商信息");
        }
        
        // 更新库存
        product.setStock(product.getStock() + quantity);
        inventoryMapper.update(product);
        
        // 记录采购信息
        PurchaseRecord record = new PurchaseRecord(
            product.getName(),
            quantity,
            supplierId
        );
        purchaseRecordMapper.insert(record);
    }

    @Transactional
    public void addProduct(Inventory product) {
        // 检查商品名是否已存在
        if (inventoryMapper.findByName(product.getName()) != null) {
            throw new RuntimeException("商品名称已存在");
        }
        
        // 设置初始值
        product.setSalesCount(0);
        product.setCreateTime(LocalDateTime.now());
        inventoryMapper.insert(product);
    }
    
    public Inventory getProductById(Long id) {
        Inventory inventory = inventoryMapper.findById(id);
        if (inventory == null) {
            throw new RuntimeException("商品不存在");
        }
        return inventory;
    }
    
    @Transactional
    public void updateProduct(Inventory product) {
        Inventory existingProduct = inventoryMapper.findById(product.getId());
        if (existingProduct == null) {
            throw new RuntimeException("商品不存在");
        }
        
        // 检查商品名是否被其他商品使用
        Inventory productWithSameName = inventoryMapper.findByName(product.getName());
        if (productWithSameName != null && !productWithSameName.getId().equals(product.getId())) {
            throw new RuntimeException("商品名称已被使用");
        }
        
        inventoryMapper.update(product);
    }
    
    @Transactional
    public void deleteProduct(Long id) {
        Inventory inventory = inventoryMapper.findById(id);
        if (inventory == null) {
            throw new RuntimeException("商品不存在");
        }
        
        // 直接删除商品，不检查订单关联
        inventoryMapper.delete(id);
    }
    
    public List<Inventory> getLowStockProducts() {
        return inventoryMapper.findLowStock();
    }

    public List<Inventory> getTopSellingProducts(int limit) {
        return inventoryMapper.findTopSelling(limit);
    }

}
