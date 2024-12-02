package com.example.demo.mapper;

import org.apache.ibatis.annotations.*;

@Mapper
public interface SupplierMapper {
    @Select("SELECT supplier_id FROM product_suppliers WHERE product_name = #{productName}")
    Long findSupplierIdByProductName(String productName);
    
    @Select("SELECT name FROM suppliers WHERE id = #{id}")
    String findSupplierNameById(Long id);
} 