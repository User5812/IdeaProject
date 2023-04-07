package com.example.springboot.controller.request;

import lombok.Data;

@Data
public class GoodsPageRequest extends BaseRequest{
    private String name;
    private Integer categoryId;
    private Integer recommend;
    private Integer status;
}
