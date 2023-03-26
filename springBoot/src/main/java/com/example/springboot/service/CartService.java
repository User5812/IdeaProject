package com.example.springboot.service;

import com.example.springboot.controller.query.CartQuery;
import com.example.springboot.entity.Cart;

public interface CartService {
    Boolean insertCart(CartQuery query);

    Boolean deleteCart(CartQuery query);

    Boolean updateCart(CartQuery query);

    Cart selectCart(CartQuery query);
}
