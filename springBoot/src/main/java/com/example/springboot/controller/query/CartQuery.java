package com.example.springboot.controller.query;

import com.sun.istack.internal.NotNull;
import lombok.Data;
import lombok.NonNull;

@Data
public class CartQuery {

    private Integer id;

    private Integer goodsId;

    private Integer userId;

    private Integer count;
}
