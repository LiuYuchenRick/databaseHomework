package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.mapper.UserMapper;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserMapper userMapper;
    
    public User registerUser(User user) {
        // 验证用户名
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new RuntimeException("用户名不能为空");
        }
        
        // 验证手机号
        if (user.getPhone() == null || !user.getPhone().matches("\\d{11}")) {
            throw new RuntimeException("请输入有效的手机号");
        }
        
        // 验证地址
        if (user.getAddress() == null || user.getAddress().trim().isEmpty()) {
            throw new RuntimeException("地址不能为空");
        }
        
        User existingUser = userMapper.findByUsername(user.getUsername());
        if (existingUser != null) {
            throw new RuntimeException("用户名已存在");
        }
        
        user.setCreateTime(LocalDateTime.now());
        userMapper.insert(user);
        return user;
    }
    
    public User login(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        if (!password.equals(user.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        return user;
    }
    
    public void updatePassword(Long userId, String newPassword) {
        User user = userMapper.findById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        user.setPassword(newPassword);
        userMapper.update(user);
    }
}