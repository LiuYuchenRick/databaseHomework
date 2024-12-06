package com.example.demo.model;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;        // 姓名
    private String level;       // 职级
    private String phone;       // 电话
    private String position;    // 岗位
    private LocalDateTime createTime;    // 创建时间
    private LocalDateTime updateTime;    // 更新时间
}