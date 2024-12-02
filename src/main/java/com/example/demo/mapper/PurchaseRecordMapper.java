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
} 