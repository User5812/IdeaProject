package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.dto.LoginDTO;
import com.example.springboot.controller.dto.LoginUserDTO;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.controller.request.UserPageRequest;
import com.example.springboot.entity.User;
import com.example.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest request){
        System.out.println(request);
        LoginUserDTO login = userService.login(request);
        return Result.success(login);
    };

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        userService.deleteById(id);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody User user){
        userService.update(user);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        User user = userService.getById(id);
        return Result.success(user);
    }

    @PostMapping("/save")
    public Result save(@RequestBody User user){
        return Result.success(userService.save(user));
    }

    @GetMapping("/list")
    public Result list (){
        List<User> users = userService.getAllUsers();
        return Result.success(users);
    }

    @GetMapping("/page")
    public Result page(UserPageRequest userPageRequest){
        Object userPage = userService.page(userPageRequest);
        return Result.success(userPage);
    }
}
