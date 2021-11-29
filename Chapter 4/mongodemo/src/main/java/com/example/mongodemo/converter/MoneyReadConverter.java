package com.example.mongodemo.converter;

import org.bson.Document;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.core.convert.converter.Converter;

/**
 * @author by 平向东
 * @date 2021/11/29 18:08
 * Copyright 2021 北京交个朋友数码科技有限公司. All rights reserved.
 */public class MoneyReadConverter implements Converter<Document, Money> {

    @Override
    public Money convert(Document source) {

        Document money = (Document)source.get("money");
        double amount = Double.parseDouble(money.getString("amount"));
        String currency = ((Document)money.get("currency")).getString("code");
        return Money.of(CurrencyUnit.of(currency),amount);
    }
}
