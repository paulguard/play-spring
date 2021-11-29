package com.example.pxdcoffee.repository;

import com.example.pxdcoffee.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author by 平向东
 * @date 2021/11/26 10:04 Copyright 2021 北京交个朋友数码科技有限公司. All rights reserved.
 */
public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder,Long> {
}
