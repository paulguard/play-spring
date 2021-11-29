package com.example.pxdcoffee.service.impl;

import java.util.Optional;

import com.example.pxdcoffee.model.Coffee;
import com.example.pxdcoffee.repository.CoffeeRepository;
import com.example.pxdcoffee.service.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

/**
 * @author by 平向东
 * @date 2021/11/26 11:08 Copyright 2021 北京交个朋友数码科技有限公司. All rights reserved.
 */
@Service
public class CoffeeServiceImpl implements CoffeeService {

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Override
    public Optional<Coffee> findByName(String name) {
        return coffeeRepository.findOne(Example.of(Coffee.builder().name(name).build()));
    }

}
