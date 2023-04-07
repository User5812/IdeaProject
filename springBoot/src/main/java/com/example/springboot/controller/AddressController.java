package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.options.UserOptions;
import com.example.springboot.controller.query.AddressSelectQuery;
import com.example.springboot.controller.request.AddressPageRequest;
import com.example.springboot.entity.Address;
import com.example.springboot.entity.User;
import com.example.springboot.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/address")
public class AddressController {


    @Autowired
    AddressService addressService;

    @GetMapping("/userOptions")
    public Result options(){
        List<UserOptions> options = addressService.getUserOption();
        return Result.success(options);
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        addressService.deleteById(id);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Address obj){
        //System.out.println(obj);
        addressService.update(obj);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        Address obj = addressService.getById(id);
        return Result.success(obj);
    }

    @PostMapping("/save")
    public Result save(@RequestBody Address obj){
        //System.out.println(obj);
        addressService.save(obj);
        return Result.success();
    }

    @GetMapping("/list")
    public Result list (AddressSelectQuery query){
        List<Address> addresses = addressService.getAllAddress(query);
        return Result.success(addresses);
    }

    @GetMapping("/page")
    public Result page(AddressPageRequest request){
        Object userPage = addressService.page(request);
        return Result.success(userPage);
    }
}
