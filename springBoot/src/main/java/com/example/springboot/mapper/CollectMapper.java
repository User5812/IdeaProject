package com.example.springboot.mapper;

import com.example.springboot.controller.query.CollectPageQuery;
import com.example.springboot.entity.Collect;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CollectMapper {

    Boolean save(Collect obj);

    Collect getById(Collect obj);

    Boolean updateById(Collect obj);

    Boolean deleteById(Integer id);

    List<Collect> listByCondition(CollectPageQuery query);
}
