package com.example.springboot.mapper;

import com.example.springboot.entity.OrderGoods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderGoodsMapper {

    Boolean insertOrderGoods(OrderGoods obj);

    List<OrderGoods> list(String orderNo);
}
