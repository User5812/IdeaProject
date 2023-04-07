package com.example.springboot.controller.query;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class ContentQuery {

    @NonNull
    private Integer goodsId;

    @NonNull
    private Integer userId;

    @NonNull
    private String username;


    private String content;


}
