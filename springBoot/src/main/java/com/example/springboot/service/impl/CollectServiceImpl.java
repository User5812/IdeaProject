package com.example.springboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.springboot.controller.query.CollectPageQuery;
import com.example.springboot.controller.query.CollectQuery;
import com.example.springboot.entity.Collect;
import com.example.springboot.mapper.CollectMapper;
import com.example.springboot.service.CollectService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CollectServiceImpl implements CollectService {

    @Autowired
    private CollectMapper mapper;

    @Override
    public Boolean insertCollect(CollectQuery query) {
        Collect insertCollect = new Collect();
        BeanUtils.copyProperties(query, insertCollect);
        Collect collect = mapper.getById(insertCollect);
        if(collect != null){
            return false;
        }
        insertCollect.setCreateTime(LocalDate.now());
        return mapper.save(insertCollect);
    }

    @Override
    public Boolean deleteCollect(Integer collectId) {
        return mapper.deleteById(collectId);
    }

    @Override
    public Boolean updateCollect(CollectQuery query) {
        Collect collect = BeanUtil.copyProperties(query, Collect.class);
        return mapper.updateById(collect);
    }

    @Override
    public Collect selectCollect(CollectQuery query) {
        Collect collect = BeanUtil.copyProperties(query, Collect.class);
        return mapper.getById(collect);
    }

    @Override
    public PageInfo<Collect> pageCollect(CollectPageQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<Collect> list = mapper.listByCondition(query);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }


}
