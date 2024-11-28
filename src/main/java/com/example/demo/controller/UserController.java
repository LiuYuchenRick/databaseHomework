package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"}, 
             allowedHeaders = "*",
             methods = {RequestMethod.GET, RequestMethod.POST})
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            log.info("收到注册请求，用户数据：{}", user);
            
            // 添加参数验证
            if (user.getUsername() == null || user.getPassword() == null) {
                log.warn("用户名或密码为空");
                return ResponseEntity.badRequest().body("用户名和密码不能为空");
            }
            
            User registeredUser = userService.registerUser(user);
            log.info("用户注册成功：{}", registeredUser);
            return ResponseEntity.ok(registeredUser);
            
        } catch (Exception e) {
            log.error("注册失败", e);
            return ResponseEntity.badRequest().body("注册失败：" + e.getMessage());
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        log.info("测试接口被访问");
        return ResponseEntity.ok("后端服务正常运行中");
    }
}