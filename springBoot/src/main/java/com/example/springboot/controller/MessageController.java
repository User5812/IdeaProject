package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.query.ContentQuery;
import com.example.springboot.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/listMessage/{id}")
    public Result listMessage(@PathVariable("id") Integer goodsId) {
        return Result.success(messageService.listMessage(goodsId));
    }


    @PostMapping("/insertMessage")
    public Result insertMessgae(@RequestBody ContentQuery query) {
        return Result.success(messageService.insertMessgae(query));
    }

    @DeleteMapping("/deleteMessage/{id}")
    public Result deleteMessage(@PathVariable("id") Integer id) {
        return Result.success(messageService.deleteMessage(id));
    }
}
