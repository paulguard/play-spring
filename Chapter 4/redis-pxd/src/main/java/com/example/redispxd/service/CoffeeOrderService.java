package com.example.redispxd.service;

import java.util.List;

import com.example.redispxd.entity.Coffee;
import com.example.redispxd.entity.CoffeeOrder;
import com.example.redispxd.entity.OrderState;

/**
 * @author by 平向东
 * @date 2021/12/2 10:44 Copyright 2021 北京交个朋友数码科技有限公司. All rights reserved.
 */
public interface CoffeeOrderService {

    CoffeeOrder createOrder(String customer, List<Coffee> coffeeList);

    boolean updateState(CoffeeOrder order, OrderState state);

}
