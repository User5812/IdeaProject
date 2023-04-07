package com.example.springboot.controller.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CartVo {

    private Integer id;

    private Integer goodsId;

    private String imgs;

    private String name;

    private double price;

    private Integer count;

    private Integer store;

    private LocalDate createTime;

}
