package com.pxd.mongorepositorydemo;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.pxd.mongorepositorydemo.model.Coffee;
import com.pxd.mongorepositorydemo.repository.CoffeeRepository;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MongorepositoryDemoApplicationTests {

    @Resource
    private CoffeeRepository coffeeRepository;

    @Test
    void contextLoads() {

        Coffee coffee = Coffee.builder()
            .name("latte")
            .price(Money.of(CurrencyUnit.of("CNY"),22.5))
            .createTime(new Date())
            .updateTime(new Date())
            .build();
        coffeeRepository.insert(Arrays.asList(coffee));

        List<Coffee> latte = coffeeRepository.findByName("latte");
        assert latte.size() == 1;
        assert latte.get(0).getName().equals("latte");


        coffee.setName("latte-improved");
        coffeeRepository.save(coffee);

        latte = coffeeRepository.findByName("latte-improved");
        assert latte.size() == 1;
        assert latte.get(0).getName().equals("latte-improved");

        coffeeRepository.deleteAll();
    }

}
