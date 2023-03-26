package com.example.springboot.entity;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
@Data
public class Content {

    private Integer id;

    private Integer goodsId;

    private String username;

    private String content;

    private LocalDate createTime;
}
