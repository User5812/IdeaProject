package com.example.springboot.controller.query;

import lombok.Data;

@Data
public class CartQuery {

    private Integer goodsId;

    private Integer userId;

    private Integer count;
}
