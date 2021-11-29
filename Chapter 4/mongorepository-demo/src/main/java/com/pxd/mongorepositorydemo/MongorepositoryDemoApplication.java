package com.pxd.mongorepositorydemo;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.pxd.mongorepositorydemo.converter.MoneyReadConverter;
import com.pxd.mongorepositorydemo.model.Coffee;
import com.pxd.mongorepositorydemo.repository.CoffeeRepository;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

@SpringBootApplication
public class MongorepositoryDemoApplication {

    @Bean
    public MongoCustomConversions mongoCustomConversions(){
        return new MongoCustomConversions(Arrays.asList(new MoneyReadConverter()));
    }

    public static void main(String[] args) {
        SpringApplication.run(MongorepositoryDemoApplication.class, args);
    }
}
