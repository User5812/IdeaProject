package com.example.springboot.service;

import com.example.springboot.controller.query.ContentQuery;
import com.example.springboot.entity.Message;

import java.util.List;

public interface MessageService {
    List<Message> listMessage(Integer goodsId);

    Boolean insertMessgae(ContentQuery query);

    Boolean deleteMessage(Integer id);
}
