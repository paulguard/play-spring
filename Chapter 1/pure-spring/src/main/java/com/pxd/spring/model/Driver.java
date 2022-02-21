package com.pxd.spring.model;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

/**
 * @author by 平向东
 * @date 2021/12/30 20:20 Copyright 2021 北京交个朋友数码科技有限公司. All rights reserved.
 */
@Component
public class Driver {

    private Long id;

    @Resource
    private Car car;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
