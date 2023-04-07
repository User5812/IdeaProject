package com.example.springboot.mapper;

import com.example.springboot.controller.dto.CartVo;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Cart;
import com.example.springboot.entity.CartListDo;
import com.example.springboot.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartMapper {

    Boolean save(Cart obj);

    Cart getById(Cart obj);

    Boolean updateById(Cart obj);

    Boolean deleteById(Integer id);

    List<CartListDo> selectUserCart(Integer id);

    void deleteAll();

    void deleteByUserId(Integer userId);
}
