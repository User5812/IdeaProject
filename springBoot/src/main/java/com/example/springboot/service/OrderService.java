package com.example.springboot.service;

import com.example.springboot.controller.dto.OrderVo;
import com.example.springboot.controller.options.UserOptions;
import com.example.springboot.controller.query.OrderInsetQuery;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.controller.request.OrderPageRequest;
import com.example.springboot.entity.Order;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface OrderService {

    PageInfo<OrderVo> getAllOrders(OrderPageRequest request);

    PageInfo<Order> page(BaseRequest request);

    Boolean save(OrderInsetQuery query);

    Order getById(Integer id);

    void update(Order obj);

    void deleteById(Integer id);


}
