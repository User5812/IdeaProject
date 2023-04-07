package com.example.springboot.service;

import com.example.springboot.controller.dto.LoginDTO;
import com.example.springboot.controller.dto.LoginUserDTO;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface UserService {

    List<User> getAllUsers();

    PageInfo<User> page(BaseRequest baseRequest);

    Boolean save(User user);

    User getById(Integer id);

    void update(User user);

    void deleteById(Integer id);

    LoginUserDTO login(LoginRequest request);
}
