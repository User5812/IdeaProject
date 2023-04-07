package com.example.springboot.controller.dto;

import com.example.springboot.entity.Goods;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrderVo {

    private Integer id;
    private String orderNo;
    private Double totalPrice;
    private Integer userId;
    private String linkUser;
    private String linkPhone;
    private String linkAddress;
    private String state;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate createTime;
    private List<Goods> goods;
}
