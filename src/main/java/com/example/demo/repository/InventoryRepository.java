package com.example.demo.repository;
// InventoryRepository.java
import com.example.demo.model.Inventory; // 确保导入语句正确
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByName(String name);
}