package com.example.demo.mapper;

import org.apache.ibatis.annotations.*;
import com.example.demo.model.User;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO users (username, password, phone, address, create_time) " +
           "VALUES (#{username}, #{password}, #{phone}, #{address}, #{createTime})")
    void insert(User user);

    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(String username);

    @Select("SELECT * FROM users WHERE id = #{id}")
    User findById(Long id);

    @Update("UPDATE users SET password = #{password}, phone = #{phone}, " +
            "address = #{address} WHERE id = #{id}")
    void update(User user);
} 