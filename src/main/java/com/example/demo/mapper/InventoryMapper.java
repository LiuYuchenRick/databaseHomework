package com.example.demo.mapper;

import org.apache.ibatis.annotations.*;
import com.example.demo.model.Inventory;
import java.util.List;

@Mapper
public interface InventoryMapper {
    @Select("SELECT * FROM inventory")
    List<Inventory> findAll();
    
    @Select("SELECT * FROM inventory WHERE name = #{name}")
    Inventory findByName(String name);
    
    @Select("SELECT * FROM inventory WHERE id = #{id}")
    Inventory findById(Long id);
    
    @Insert("INSERT INTO inventory(name, stock, price, min_stock) " +
            "VALUES(#{name}, #{stock}, #{price}, #{minStock})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Inventory inventory);
    
    @Update("UPDATE inventory SET stock = #{stock}, price = #{price}, " +
            "min_stock = #{minStock}, sales_count = #{salesCount} WHERE id = #{id}")
    void update(Inventory inventory);
    
    @Delete("DELETE FROM inventory WHERE id = #{id}")
    void delete(Long id);
    
    @Select("SELECT * FROM inventory WHERE stock <= min_stock")
    List<Inventory> findLowStock();
} 