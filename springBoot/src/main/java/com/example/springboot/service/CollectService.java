package com.example.springboot.service;

import com.example.springboot.controller.query.CollectPageQuery;
import com.example.springboot.controller.query.CollectQuery;
import com.example.springboot.entity.Collect;
import com.github.pagehelper.PageInfo;

public interface CollectService {
    Boolean insertCollect(CollectQuery query);

    Boolean deleteCollect(Integer collectId);

    Boolean updateCollect(CollectQuery query);

    Collect selectCollect(CollectQuery query);

    PageInfo<Collect> pageCollect(CollectPageQuery query);
}
