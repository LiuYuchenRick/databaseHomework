package com.example.demo.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;
import com.example.demo.model.Employee;


@Mapper
public interface EmployeeMapper {
    
    @Select("SELECT * FROM employees")
    List<Employee> findAll();
    
    @Insert("INSERT INTO employees (name, level, phone, position, create_time, update_time) " +
            "VALUES (#{name}, #{level}, #{phone}, #{position}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Employee employee);
    
    @Update("UPDATE employees SET name=#{name}, level=#{level}, phone=#{phone}, " +
            "position=#{position}, update_time=#{updateTime} WHERE id=#{id}")
    void update(Employee employee);
    
    @Delete("DELETE FROM employees WHERE id=#{id}")
    void deleteById(Long id);
}