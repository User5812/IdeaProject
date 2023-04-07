package com.example.springboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.springboot.common.Result;
import com.example.springboot.controller.dto.CartVo;
import com.example.springboot.controller.dto.OrderVo;
import com.example.springboot.controller.options.UserOptions;
import com.example.springboot.controller.query.OrderInsetQuery;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.controller.request.OrderPageRequest;
import com.example.springboot.entity.*;
import com.example.springboot.mapper.OrderGoodsMapper;
import com.example.springboot.mapper.OrderMapper;
import com.example.springboot.service.AddressService;
import com.example.springboot.service.CartService;
import com.example.springboot.service.GoodsService;
import com.example.springboot.service.OrderService;

import com.example.springboot.utils.SnowFlakeUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper ordermapper;

    @Autowired
    CartService cartService;

    @Autowired
    AddressService addressService;

    @Autowired
    OrderGoodsMapper orderGoodsMapper;

    @Autowired
    GoodsService goodsService;

    @Override
    public PageInfo<OrderVo> getAllOrders(OrderPageRequest request){
        //开启分页
        PageHelper.startPage(request.getPageNum(),request.getPageSize());
        //获取对应用户的订单列表
        List<Order> orderList =  ordermapper.listOrders(request.getUserId());
        List<OrderVo> returnList = new ArrayList<>();
        for (Order item : orderList) {
            OrderVo orderVo = BeanUtil.copyProperties(item, OrderVo.class);
            //获取要查询的订单编号,查询orderGoods
            List<OrderGoods> orderGoodsList = orderGoodsMapper.list(orderVo.getOrderNo());
            List<Goods> goodsList = new ArrayList<>();
            //查询商品名称以及图片进行包装
            for (OrderGoods orderGood : orderGoodsList) {
                Goods good = goodsService.getById(orderGood.getGoodsId());
                good.setCount(orderGood.getCount());
                goodsList.add(good);
            }
            //把查询到的结果写回倒list中
            orderVo.setGoods(goodsList);
            returnList.add(orderVo);
        }
        PageInfo<OrderVo> pageInfo = new PageInfo<>(returnList);
        return pageInfo;
    }

    @Override
    public PageInfo<Order> page(BaseRequest request) {
        PageHelper.startPage(request.getPageNum(),request.getPageSize());
        List<Order> orders = ordermapper.listByCondition(request);
        PageInfo<Order> OrderPageInfo = new PageInfo<>(orders);
        return OrderPageInfo;
    }

    @Override
    public Boolean save(OrderInsetQuery query) {
        //插入订单
        Address userAddress = addressService.getById(query.getAddressId());
        Order order = BeanUtil.copyProperties(userAddress, Order.class);
        order.setCreateTime(LocalDate.now());
        order.setState("已下单");

        //查询用户当前购物车详情
        Result userCart = cartService.selectUserCart(query.getUserId());
        List<CartVo> list = (List)userCart.getData();
        order.setTotalPrice(userCart.getTotalPrice());
        String orderNo = SnowFlakeUtil.getNextId();
        order.setOrderNo(orderNo);

        for(CartVo item : list ) {
            OrderGoods orderGoods = new OrderGoods();
            orderGoods.setOrderNo(orderNo);
            orderGoods.setCount(item.getCount());
            orderGoods.setGoodsId(item.getGoodsId());
            orderGoodsMapper.insertOrderGoods(orderGoods);
        }
        cartService.deleteCartByUserId(query.getUserId());
        return ordermapper.save(order);
    }

    @Override
    public Order getById(Integer id) {
        Order order = ordermapper.getById(id);
        return order;
    }

    @Override
    public void update(Order obj) {
        ordermapper.updateById(obj);
    }

    @Override
    public void deleteById(Integer id) {
        ordermapper.deleteById(id);

    }
    

}