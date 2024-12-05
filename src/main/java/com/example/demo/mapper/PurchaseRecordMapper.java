package com.example.demo.mapper;

import org.apache.ibatis.annotations.*;
import com.example.demo.model.PurchaseRecord;
import java.util.List;

@Mapper
public interface PurchaseRecordMapper {
    @Insert("INSERT INTO purchase_orders(product_name, quantity, supplier_id, purchase_time) " +
            "VALUES(#{productName}, #{quantity}, #{supplierId}, #{purchaseTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(PurchaseRecord record);
    
    @Select("SELECT po.*, s.name as supplier_name " +
            "FROM purchase_orders po " +
            "LEFT JOIN suppliers s ON po.supplier_id = s.id " +
            "ORDER BY po.purchase_time DESC")
    List<PurchaseRecord> findAll();
    
    @Select("SELECT po.*, s.name as supplier_name " +
            "FROM purchase_orders po " +
            "LEFT JOIN suppliers s ON po.supplier_id = s.id " +
            "WHERE po.product_name = #{productName}")
    List<PurchaseRecord> findByProductName(String productName);
    
    @Select("SELECT po.*, s.name as supplier_name " +
            "FROM purchase_orders po " +
            "LEFT JOIN suppliers s ON po.supplier_id = s.id " +
            "WHERE po.id = #{id}")
    PurchaseRecord findById(Long id);
    
    @Delete("DELETE FROM purchase_orders WHERE id = #{id}")
    void delete(Long id);
    
    @Update("UPDATE purchase_orders SET " +
            "quantity = #{quantity}, " +
            "supplier_id = #{supplierId}, " +
            "purchase_time = #{purchaseTime} " +
            "WHERE id = #{id}")
    void update(PurchaseRecord record);
    
    @Select("SELECT COUNT(*) FROM purchase_orders")
    Integer countPurchases();
} 