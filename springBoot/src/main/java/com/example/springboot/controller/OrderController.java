package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.dto.OrderVo;
import com.example.springboot.controller.options.UserOptions;
import com.example.springboot.controller.query.OrderInsetQuery;
import com.example.springboot.controller.request.OrderPageRequest;
import com.example.springboot.entity.Address;
import com.example.springboot.entity.Order;
import com.example.springboot.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
    OrderService orderService;

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        orderService.deleteById(id);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Order obj){
        //System.out.println(obj);
        orderService.update(obj);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        Order obj = orderService.getById(id);
        return Result.success(obj);
    }

    @PostMapping("/save")
    public Result save(@RequestBody OrderInsetQuery query){
        //System.out.println(obj);

        return Result.success(orderService.save(query));
    }

    @GetMapping("/list")
    public Result list (OrderPageRequest request){
        Object OrderPages = orderService.getAllOrders(request);
        return Result.success(OrderPages);
    }

    @GetMapping("/page")
    public Result page(OrderPageRequest request){
        Object OrderPage = orderService.page(request);
        return Result.success(OrderPage);
    }
}
