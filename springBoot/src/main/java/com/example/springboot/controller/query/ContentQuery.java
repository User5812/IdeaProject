package com.example.springboot.controller.query;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ContentQuery {

    private Integer goodsId;

    private String username;

    private String content;

    private LocalDate createTime;


}
