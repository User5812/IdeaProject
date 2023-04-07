package com.example.springboot.mapper;

import com.example.springboot.controller.options.UserOptions;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {

    List<Order> listOrders(Integer userId);


    List<Order> listByCondition(BaseRequest baseRequest);

    Boolean save(Order obj);

    Order getById(Integer id);

    void updateById(Order obj);

    void deleteById(Integer id);


}
