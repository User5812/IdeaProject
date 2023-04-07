package com.example.springboot.service.impl;

import com.example.springboot.controller.options.UserOptions;
import com.example.springboot.controller.query.AddressSelectQuery;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Address;
import com.example.springboot.entity.User;
import com.example.springboot.mapper.AddressMapper;
import com.example.springboot.service.AddressService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressMapper addressmapper;

    @Override
    public List<Address> getAllAddress(AddressSelectQuery query){
        return addressmapper.listAddress(query);
    }

    @Override
    public PageInfo<Address> page(BaseRequest request) {
        PageHelper.startPage(request.getPageNum(),request.getPageSize());
        List<Address> addresses = addressmapper.listByCondition(request);
        PageInfo<Address> userPageInfo = new PageInfo<>(addresses);
        return userPageInfo;
    }

    @Override
    public void save(Address obj) {
        addressmapper.save(obj);
    }

    @Override
    public Address getById(Integer id) {
        Address address = addressmapper.getById(id);
        return address;
    }

    @Override
    public void update(Address obj) {
        addressmapper.updateById(obj);
    }

    @Override
    public void deleteById(Integer id) {
        addressmapper.deleteById(id);

    }

    @Override
    public List<UserOptions> getUserOption() {
        List<UserOptions> options = addressmapper.getUserOptions();
        return options;
    }


}