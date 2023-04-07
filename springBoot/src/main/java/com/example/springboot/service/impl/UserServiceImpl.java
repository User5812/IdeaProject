package com.example.springboot.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.example.springboot.controller.dto.LoginDTO;
import com.example.springboot.controller.dto.LoginUserDTO;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.service.UserService;
import com.example.springboot.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper usermapper;


    private static final String DEFAULT_PASS = "123";
    private static final String PASS_SALT = "tu5812";

    @Override
    public LoginUserDTO login(LoginRequest request) {
        User user = null;
        try{
            user = usermapper.getByUsername(request.getUsername());
        }catch (Exception e){
            log.error("根据用户名{}查询出错", request.getUsername());
            throw new ServiceException("用户名错误");
        }
        //判断密码是否合法
        user = usermapper.getByUsername(request.getUsername());
        String securePass = securePass(request.getPassword());
        if(!securePass.equals(user.getPassword())){
            throw new ServiceException("密码错误");
        }
        if(user == null){
            throw new ServiceException("用户名或密码错误");
        }
        LoginUserDTO loginUserDTO = new LoginUserDTO();
        BeanUtils.copyProperties(user,loginUserDTO);
        //生成token
        String token = TokenUtils.genToken(String.valueOf(user.getId()), user.getPassword());
        loginUserDTO.setToken(token);
        return loginUserDTO;
    }

    @Override
    public List<User> getAllUsers() {
        return usermapper.listUsers();
    }

    @Override
    public PageInfo<User> page(BaseRequest request) {
        PageHelper.startPage(request.getPageNum(),request.getPageSize());
        List<User> users = usermapper.listByCondition(request);
        PageInfo<User> userPageInfo = new PageInfo<>(users);
        return userPageInfo;
    }

    @Override
    public Boolean save(User user) {

        user.setIdentify("0");
        user.setPassword(securePass(user.getPassword()));//设置加密
        try {
            return usermapper.save(user);
        }catch (DuplicateKeyException e){
            log.error("数据插入失败，username：{}",user.getUsername());
            throw new ServiceException("用户名重复");
        }
    }

    @Override
    public User getById(Integer id) {
        User user = usermapper.getById(id);
        return user;
    }

    @Override
    public void update(User user) {
        usermapper.updateById(user);
    }

    @Override
    public void deleteById(Integer id) {
        usermapper.deleteById(id);

    }

    //加密密码
    private String securePass(String password){
        String newPass = SecureUtil.md5(password + PASS_SALT);
        return newPass;
    }

}