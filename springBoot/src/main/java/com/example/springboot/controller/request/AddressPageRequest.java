package com.example.springboot.controller.request;

import lombok.Data;

@Data
public class AddressPageRequest extends BaseRequest{
    private String linkUser;
    private String linkPhone;
}
