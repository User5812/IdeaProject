package com.example.springboot.mapper;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Cart;
import com.example.springboot.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartMapper {

    Boolean save(Cart obj);

    Cart getById(Cart obj);

    Boolean updateById(Cart obj);

    Boolean deleteById(Cart obj);

}
