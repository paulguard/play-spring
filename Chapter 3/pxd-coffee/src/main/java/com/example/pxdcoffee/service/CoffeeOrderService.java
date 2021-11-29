package com.example.pxdcoffee.service;

import java.util.Optional;

import com.example.pxdcoffee.model.CoffeeOrder;

/**
 * @author by 平向东
 * @date 2021/11/26 11:11 Copyright 2021 北京交个朋友数码科技有限公司. All rights reserved.
 */
public interface CoffeeOrderService {

    Optional<CoffeeOrder> getByCustomer(String customerName);

    CoffeeOrder save(CoffeeOrder coffeeOrder);
}
