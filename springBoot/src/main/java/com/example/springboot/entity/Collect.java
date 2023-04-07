package com.example.springboot.entity;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
@Data
public class Collect {

    private Integer id;

    private String goodsName;

    private String goodsImg;

    private Integer goodsId;

    private Integer userId;

    private LocalDate createTime;

}
