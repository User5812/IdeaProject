package com.example.springboot.common;

import lombok.Data;

@Data
public class Result {
    private static final String SUCCESS_CODE = "200";
    private static final String ERROR_CODE = "-1";

    private String code;
    private Object data;
    private String msg;
    private Integer count;
    private Double totalPrice;

    public static Result success(){
        Result result = new Result();
        result.setCode("200");
        return result;
    }

    public static Result success(Object data){
        Result result = new Result();
        result.setCode("200");
        result.setData(data);
        return result;
    }

    public static Result successCart(Object data, Integer count, Double totalPrice){
        Result result = new Result();
        result.setCode("200");
        result.setData(data);
        result.setCount(count);
        result.setTotalPrice(totalPrice);
        return result;
    }
    public static Result error(String msg){
        Result result = new Result();
        result.setCode(ERROR_CODE);
        result.setMsg(msg);
        return result;
    }

    public static Result error(String code, String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }



}
