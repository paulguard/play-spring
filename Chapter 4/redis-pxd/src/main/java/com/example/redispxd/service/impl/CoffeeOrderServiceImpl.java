package com.example.redispxd.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import com.example.redispxd.entity.Coffee;
import com.example.redispxd.entity.CoffeeOrder;
import com.example.redispxd.entity.OrderState;
import com.example.redispxd.repository.CoffeeOrderRepository;
import com.example.redispxd.service.CoffeeOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author by 平向东
 * @date 2021/12/2 10:45 Copyright 2021 北京交个朋友数码科技有限公司. All rights reserved.
 */
@Slf4j
@Service
public class CoffeeOrderServiceImpl implements CoffeeOrderService {

    @Resource
    private CoffeeOrderRepository coffeeOrderRepository;

    @Override
    public CoffeeOrder createOrder(String customer, List<Coffee> coffeeList) {

        CoffeeOrder coffeeOrder = CoffeeOrder.builder()
            .customer(customer)
            .items(coffeeList)
            .state(OrderState.INIT).build();

        CoffeeOrder saved = coffeeOrderRepository.save(coffeeOrder);

        log.info("New order saved {}",coffeeOrder);

        return saved;
    }

    @Override
    public boolean updateState(CoffeeOrder order, OrderState state) {

        if (state.compareTo(order.getState()) <= 0) {
            log.warn("Wrong State order: {}, {}", state, order.getState());
            return false;
        }

        order.setState(state);

        coffeeOrderRepository.save(order);

        log.info("order updated {}",order);

        return true;
    }
}
