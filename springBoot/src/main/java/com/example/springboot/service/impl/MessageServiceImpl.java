package com.example.springboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.springboot.controller.query.ContentQuery;
import com.example.springboot.entity.Content;
import com.example.springboot.entity.Message;
import com.example.springboot.mapper.MessageMapper;
import com.example.springboot.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper mapper;

    @Override
    public List<Message> listMessage(Integer goodsId) {
        List<Message> list = mapper.listMessage(goodsId);
        return list;
    }

    @Override
    public Boolean insertMessgae(ContentQuery query) {
        Message message = BeanUtil.copyProperties(query, Message.class);
        message.setCreateTime(LocalDateTime.now());
        return mapper.insertMessage(message);
    }

    @Override
    public Boolean deleteMessage(Integer id) {
        return mapper.deleteMessage(id);
    }
}
