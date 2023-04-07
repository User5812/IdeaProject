package com.example.springboot.service;

import com.example.springboot.controller.options.UserOptions;
import com.example.springboot.controller.query.AddressSelectQuery;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Address;
import com.example.springboot.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface AddressService {

    List<Address> getAllAddress(AddressSelectQuery query);

    PageInfo<Address> page(BaseRequest request);

    void save(Address obj);

    Address getById(Integer id);

    void update(Address obj);

    void deleteById(Integer id);


    List<UserOptions> getUserOption();
}
