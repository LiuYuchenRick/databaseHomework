package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.mapper.EmployeeMapper;
import com.example.demo.model.Employee;

@Service
public class EmployeeService {
    
    @Autowired
    private EmployeeMapper employeeMapper;
    
    public List<Employee> getAllEmployees() {
        return employeeMapper.findAll();
    }
    
    public Employee addEmployee(Employee employee) {
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employeeMapper.insert(employee);
        return employee;
    }
    
    public Employee updateEmployee(Long id, Employee employee) {
        employee.setId(id);
        employee.setUpdateTime(LocalDateTime.now());
        employeeMapper.update(employee);
        return employee;
    }
    
    public void deleteEmployee(Long id) {
        employeeMapper.deleteById(id);
    }
}