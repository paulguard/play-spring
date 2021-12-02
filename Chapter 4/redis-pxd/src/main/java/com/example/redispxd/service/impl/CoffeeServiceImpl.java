package com.example.redispxd.service.impl;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import com.example.redispxd.entity.Coffee;
import com.example.redispxd.repository.CoffeeRepository;
import com.example.redispxd.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

/**
 * @author by 平向东
 * @date 2021/12/2 10:45 Copyright 2021 北京交个朋友数码科技有限公司. All rights reserved.
 */
@Slf4j
@Service
public class CoffeeServiceImpl implements CoffeeService {

    @Resource
    private CoffeeRepository coffeeRepository;

    @Override
    public List<Coffee> findAllCoffee() {

        return coffeeRepository.findAll();
    }

    @Override
    public Optional<Coffee> findCoffee(String name) {

        Optional<Coffee> coffee = coffeeRepository.findOne(Example.of(Coffee.builder().name(name).build()));

        log.info("found! {}",coffee);

        return coffee;
    }

}
