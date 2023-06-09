package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.query.CartQuery;
import com.example.springboot.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService service;


    @PostMapping("/insertCart")
    public Result insertCart(@RequestBody CartQuery query) {
        Boolean result = service.insertCart(query);
        if(result){
            return Result.success();
        }
        else {
            return Result.error("该商品已加入购物车");
        }
    }

    @DeleteMapping("/deleteCart/{id}")
    public Result deleteCart(@PathVariable("id") Integer id) {
        return Result.success(service.deleteCart(id));
    }

    @PutMapping("/updateCart")
    public Result updateCart(@RequestBody CartQuery query) {
        return Result.success(service.updateCart(query));
    }

    @PostMapping("/selectCart")
    public Result selectCart(@RequestBody CartQuery query) {
        return Result.success(service.selectCart(query));
    }

    @GetMapping("/selectUserCart/{userId}")
    public Result selectUserCart(@PathVariable("userId") Integer id) {
        return service.selectUserCart(id);
    }
}
