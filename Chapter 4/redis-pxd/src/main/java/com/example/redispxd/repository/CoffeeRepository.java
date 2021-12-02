package com.example.redispxd.repository;

import com.example.redispxd.entity.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author by 平向东
 * @date 2021/12/2 10:44 Copyright 2021 北京交个朋友数码科技有限公司. All rights reserved.
 */
public interface CoffeeRepository extends JpaRepository<Coffee,Long> {
}
