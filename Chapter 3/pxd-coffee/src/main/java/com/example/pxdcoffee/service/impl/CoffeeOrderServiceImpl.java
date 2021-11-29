package com.example.pxdcoffee.service.impl;

import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import com.example.pxdcoffee.model.CoffeeOrder;
import com.example.pxdcoffee.repository.CoffeeOrderRepository;
import com.example.pxdcoffee.service.CoffeeOrderService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

/**
 * @author by 平向东
 * @date 2021/11/26 10:06 Copyright 2021 北京交个朋友数码科技有限公司. All rights reserved.
 */
@Service
@Transactional
public class CoffeeOrderServiceImpl implements CoffeeOrderService {

    @Resource
    private CoffeeOrderRepository coffeeOrderRepository;

    @Override
    public Optional<CoffeeOrder> getByCustomer(String customerName){
        return coffeeOrderRepository.findOne(Example.of(CoffeeOrder.builder().customer(customerName).build()));
    }

    @Override
    public CoffeeOrder save(CoffeeOrder coffeeOrder) {
        return coffeeOrderRepository.save(coffeeOrder);
    }
}
