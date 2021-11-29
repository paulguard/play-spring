package com.example.mongodemo.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.money.Money;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author by 平向东
 * @date 2021/11/29 18:05 Copyright 2021 北京交个朋友数码科技有限公司. All rights reserved.
 */
@Document
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coffee {

    @Id
    private String id;

    private String name;

    private Money price;

    private Date createTime;

    private Date updateTime;

}
