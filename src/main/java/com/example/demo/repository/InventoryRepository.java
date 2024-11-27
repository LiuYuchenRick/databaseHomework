package com.example.demo.repository;
// InventoryRepository.java
import com.example.demo.model.Inventory; // 确保导入语句正确
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    // 你可以在这里定义一些查询数据库的方法
}