package com.example.springboot.controller.request;

import lombok.Data;

@Data
public class OrderPageRequest extends BaseRequest{
    private String orderNo;
    private String state;
    private Integer userId;
}
