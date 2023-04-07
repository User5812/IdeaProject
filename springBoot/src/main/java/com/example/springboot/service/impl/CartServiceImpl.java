package com.example.springboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.springboot.common.Result;
import com.example.springboot.controller.dto.CartVo;
import com.example.springboot.controller.query.CartQuery;
import com.example.springboot.entity.Cart;
import com.example.springboot.entity.CartListDo;
import com.example.springboot.mapper.CartMapper;
import com.example.springboot.service.CartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    public Boolean deleteCart(Integer id) {
        return mapper.deleteById(id);
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

    @Override
    public Result selectUserCart(Integer id) {
        List<CartListDo> list = mapper.selectUserCart(id);
        List<CartVo> returnList = new ArrayList<>();
        Integer count = 0;
        BigDecimal totalPrice = new BigDecimal("0.0");
        for(CartListDo item : list) {
            CartVo vo = BeanUtil.copyProperties(item, CartVo.class);
            double total = item.getPrice() * item.getDiscount();
            BigDecimal decimal = new BigDecimal(total);
            Double price = decimal.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
            vo.setPrice(price);
            count += item.getCount();
            BigDecimal add1 = totalPrice;
            BigDecimal add2 = new BigDecimal(total * item.getCount());
            totalPrice = add1.add(add2);
            System.out.println(totalPrice);
            returnList.add(vo);
        }
        return Result.successCart(returnList, count, totalPrice.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    @Override
    public void deleteAllCart() {
        mapper.deleteAll();
    }

    @Override
    public void deleteCartByUserId(Integer userId) {
        mapper.deleteByUserId(userId);
    }


}
