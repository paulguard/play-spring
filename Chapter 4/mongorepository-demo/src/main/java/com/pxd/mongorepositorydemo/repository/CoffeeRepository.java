package com.pxd.mongorepositorydemo.repository;

import java.util.List;

import com.pxd.mongorepositorydemo.model.Coffee;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author by 平向东
 * @date 2021/11/29 20:53 Copyright 2021 北京交个朋友数码科技有限公司. All rights reserved.
 */
public interface CoffeeRepository extends MongoRepository<Coffee,String> {

    List<Coffee> findByName(String name);

}
