package com.example.springboot.entity;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
@Data
public class Message {

    private Integer id ;

    private Integer userId;

    private Integer goodsId;

    private String username;

    private String content;

    private LocalDateTime createTime;


}
