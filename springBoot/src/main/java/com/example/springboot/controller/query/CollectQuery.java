package com.example.springboot.controller.query;

import com.example.springboot.controller.request.BaseRequest;
import com.sun.istack.internal.NotNull;
import lombok.Data;
import lombok.NonNull;

@Data
public class CollectQuery {

    @NotNull
    private String goodsName;

    @NonNull
    private String goodsImg;

    @NotNull
    private Integer goodsId;

    @NonNull
    private Integer userId;



}
