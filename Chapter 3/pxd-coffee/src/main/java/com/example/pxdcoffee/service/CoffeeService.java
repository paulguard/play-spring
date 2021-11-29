package com.example.pxdcoffee.service;

import java.util.Optional;

import javax.transaction.Transactional;

import com.example.pxdcoffee.model.Coffee;
import com.example.pxdcoffee.repository.CoffeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

/**
 * @author by 平向东
 * @date 2021/11/26 10:06 Copyright 2021 北京交个朋友数码科技有限公司. All rights reserved.
 */
@Service
@Transactional
public interface CoffeeService {

    Optional<Coffee> findByName(String name);
}
