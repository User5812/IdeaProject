package com.example.springboot.entity;

import lombok.Data;
import org.springframework.stereotype.Repository;

@Data
@Repository
public class OrderGoods {
    private Integer id;

    private String orderNo;

    private Integer goodsId;

    private Integer count;

}
