package com.example.springboot.mapper;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface UserMapper {

    List<User> listUsers();


    List<User> listByCondition(BaseRequest baseRequest);

    Boolean save(User user);

    User getById(Integer id);

    void updateById(User user);

    void deleteById(Integer id);

    User getByUsername(String username);
}
