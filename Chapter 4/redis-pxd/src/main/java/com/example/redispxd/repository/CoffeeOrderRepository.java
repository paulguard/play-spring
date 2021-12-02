package com.example.redispxd.repository;

import com.example.redispxd.entity.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author by 平向东
 * @date 2021/12/2 10:43 Copyright 2021 北京交个朋友数码科技有限公司. All rights reserved.
 */
public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder,Long> {
}
