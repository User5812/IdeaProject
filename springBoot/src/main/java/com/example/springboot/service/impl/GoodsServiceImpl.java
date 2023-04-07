package com.example.springboot.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Category;
import com.example.springboot.entity.Goods;
import com.example.springboot.mapper.GoodsMapper;
import com.example.springboot.service.GoodsService;
import com.example.springboot.utils.SnowFlakeUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    GoodsMapper goodsmapper;

    @Override
    public List<Goods> getAllGoodss() {
        return goodsmapper.listGoodss();
    }


    @Override
    public PageInfo<Goods> page(BaseRequest request) {
        PageHelper.startPage(request.getPageNum(),request.getPageSize());
        List<Goods> goodss = goodsmapper.listByCondition(request);
        PageInfo<Goods> goodsPageInfo = new PageInfo<>(goodss);
        return goodsPageInfo;
    }

    @Override
    public void save(Goods goods) {
        goods.setNo(SnowFlakeUtil.getNextId());
        goodsmapper.save(goods);
    }

    @Override
    public Goods getById(Integer id) {
        Goods goods = goodsmapper.getById(id);
        return goods;
    }

    @Override
    public void update(Goods goods) {
        goodsmapper.updateById(goods);
    }

    @Override
    public void deleteById(Integer id) {
        goodsmapper.deleteById(id);

    }

    @Override
    public List<Category> getOption() {
        return goodsmapper.getOption();

    }

    @Override
    public List<Goods> sales() {
        return goodsmapper.sales();
    }

}