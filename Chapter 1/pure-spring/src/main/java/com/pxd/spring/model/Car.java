package com.pxd.spring.model;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

/**
 * @author by 平向东
 * @date 2021/12/29 16:05 Copyright 2021 北京交个朋友数码科技有限公司. All rights reserved.
 */
@Component
public class Car {

    private Long id;

    private String name;

    @Resource
    private Driver driver;

    public Car() {
    }

    public Car(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
