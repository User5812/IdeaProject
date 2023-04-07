package com.example.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
@Data
public class Goods {
    private Integer id;
    private Integer userId;
    private String sellerPhone;
    private String name;
    private String description;
    private String no;
    private String price;
    private String discount;
    private Integer store;
    private Integer praise;
    private Integer sales;
    private Integer categoryId;
    private String imgs;
    private Integer count;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate createTime;
    private boolean recommend;
    private boolean status;
}
