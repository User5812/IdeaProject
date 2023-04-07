package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.query.CollectPageQuery;
import com.example.springboot.controller.query.CollectQuery;
import com.example.springboot.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/collect")
public class CollectController {

    @Autowired
    private CollectService service;


    @PostMapping("/insertCollect")
    public Result insertCollect(@RequestBody CollectQuery query) {
        Boolean result = service.insertCollect(query);
        if(result){
            return Result.success();
        }
        else {
            return Result.error("已收藏");
        }
    }

    @DeleteMapping("/deleteCollect/{id}")
    public Result deleteCollect(@PathVariable("id") Integer collectId) {
        return Result.success(service.deleteCollect(collectId));
    }

    @PostMapping("/updateCollect")
    public Result updateCollect(@RequestBody CollectQuery query) {
        return Result.success(service.updateCollect(query));
    }

    @PostMapping("/selectCollect")
    public Result selectCollect(@RequestBody CollectQuery query) {
        return Result.success(service.selectCollect(query));
    }

    @GetMapping("/pageCollect")
    public Result pageCollect(CollectPageQuery query) {
        Object collectPage = service.pageCollect(query);
        return Result.success(collectPage);
    }
}
