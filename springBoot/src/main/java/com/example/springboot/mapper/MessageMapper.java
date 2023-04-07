package com.example.springboot.mapper;

import com.example.springboot.entity.Content;
import com.example.springboot.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {

    List<Message> listMessage(Integer goodsId);

    Boolean insertMessage(Message content);

    Boolean deleteMessage(Integer id);
}
