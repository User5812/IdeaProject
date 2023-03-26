package com.example.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
@Data
public class Cart {

    private Integer id;

    private Integer goodsId;

    private Integer userId;

    private Integer count;

    private LocalDate createTime;

}
