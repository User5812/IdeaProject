package com.example.springboot.service;

import com.example.springboot.common.Result;
import com.example.springboot.controller.dto.CartVo;
import com.example.springboot.controller.query.CartQuery;
import com.example.springboot.entity.Cart;

import java.util.List;

public interface CartService {
    Boolean insertCart(CartQuery query);

    Boolean deleteCart(Integer id);

    Boolean updateCart(CartQuery query);

    Cart selectCart(CartQuery query);

    Result selectUserCart(Integer id);

    void deleteAllCart();

    void deleteCartByUserId(Integer userId);
}
