package com.example.springboot.controller.query;

import com.example.springboot.controller.request.BaseRequest;
import lombok.Data;
import lombok.NonNull;

@Data
public class CollectPageQuery extends BaseRequest {

    @NonNull
    private Integer userId;

}
