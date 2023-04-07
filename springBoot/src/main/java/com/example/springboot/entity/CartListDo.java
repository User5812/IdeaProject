package com.example.springboot.entity;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
@Data
public class CartListDo {

    private Integer id;

    private Integer goodsId;

    private String imgs;

    private String name;

    private double price;

    private double discount;

    private Integer count;

    private Integer store;

    private LocalDate createTime;
}
