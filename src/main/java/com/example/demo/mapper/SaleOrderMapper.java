package com.example.demo.mapper;

import org.apache.ibatis.annotations.*;
import com.example.demo.model.SaleOrder;
import java.util.List;

@Mapper
public interface SaleOrderMapper {
    @Select("SELECT * FROM sale_orders WHERE user_id = #{userId}")
    List<SaleOrder> findByUserId(Long userId);
    
    @Select("SELECT * FROM sale_orders WHERE id = #{id}")
    SaleOrder findById(Long id);
    
    @Insert("INSERT INTO sale_orders(product_name, quantity, user_id, order_time) " +
            "VALUES(#{productName}, #{quantity}, #{userId}, #{orderTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(SaleOrder order);
    
    @Delete("DELETE FROM sale_orders WHERE id = #{id}")
    void delete(Long id);
    
    @Select("SELECT * FROM sale_orders ORDER BY order_time DESC")
    List<SaleOrder> findAll();
    
    @Select("SELECT COUNT(*) FROM sale_orders WHERE product_name = #{productName}")
    int countByProductName(String productName);
    
    @Select("SELECT so.*, u.username as user_name " +
            "FROM sale_orders so " +
            "LEFT JOIN users u ON so.user_id = u.id " +
            "ORDER BY so.order_time DESC")
    List<SaleOrder> findAllWithUserInfo();
} 
