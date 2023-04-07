package com.example.springboot.mapper;

import com.example.springboot.controller.options.UserOptions;
import com.example.springboot.controller.query.AddressSelectQuery;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Address;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressMapper {

    List<Address> listAddress(AddressSelectQuery query);


    List<Address> listByCondition(BaseRequest baseRequest);

    void save(Address obj);

    Address getById(Integer id);

    void updateById(Address obj);

    void deleteById(Integer id);

    List<UserOptions> getUserOptions();
}
