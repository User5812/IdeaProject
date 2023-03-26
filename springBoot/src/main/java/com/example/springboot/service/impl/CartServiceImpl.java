package com.example.springboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.springboot.controller.query.CartQuery;
import com.example.springboot.entity.Cart;
import com.example.springboot.mapper.CartMapper;
import com.example.springboot.service.CartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper mapper;

    @Override
    public Boolean insertCart(CartQuery query) {
        Cart insertCart = new Cart();
        BeanUtils.copyProperties(query, insertCart);
        Cart cart = mapper.getById(insertCart);
        if(cart != null){
            return false;
        }
        insertCart.setCreateTime(LocalDate.now());
        return mapper.save(insertCart);
    }

    @Override
    public Boolean deleteCart(CartQuery query) {
        Cart cart = BeanUtil.copyProperties(query, Cart.class);
        return mapper.deleteById(cart);
    }

    @Override
    public Boolean updateCart(CartQuery query) {
        Cart cart = BeanUtil.copyProperties(query, Cart.class);
        return mapper.updateById(cart);
    }

    @Override
    public Cart selectCart(CartQuery query) {
        Cart cart = BeanUtil.copyProperties(query, Cart.class);
        return mapper.getById(cart);
    }


}
