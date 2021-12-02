package com.example.redispxd.service;

import java.util.List;
import java.util.Optional;

import com.example.redispxd.entity.Coffee;

/**
 * @author by 平向东
 * @date 2021/12/2 10:45 Copyright 2021 北京交个朋友数码科技有限公司. All rights reserved.
 */
public interface CoffeeService {

    List<Coffee> findAllCoffee();

   Optional<Coffee> findCoffee(String name);

}
