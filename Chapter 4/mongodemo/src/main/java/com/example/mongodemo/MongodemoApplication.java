package com.example.mongodemo;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.example.mongodemo.converter.MoneyReadConverter;
import com.example.mongodemo.model.Coffee;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@SpringBootApplication
@Slf4j
public class MongodemoApplication implements ApplicationRunner {

    @Resource
    private MongoTemplate mongoTemplate;

    public static void main(String[] args) {
        SpringApplication.run(MongodemoApplication.class, args);
    }

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(Arrays.asList(new MoneyReadConverter()));
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Coffee latte = Coffee.builder()
            .name("latte")
            .price(Money.of(CurrencyUnit.of("CNY"), 20.0))
            .createTime(new Date())
            .updateTime(new Date()).build();

        mongoTemplate.save(latte);
        log.info("coffee {}",latte);

        List<Coffee> coffees = mongoTemplate.find(Query.query(Criteria.where("name").is("latte")), Coffee.class);

        assert coffees.size() == 1;
        assert coffees.get(0).getName().equals("latte");
        log.info("coffee found:{}",coffees.get(0));

        UpdateResult updateResult = mongoTemplate.updateFirst(Query.query(Criteria.where("name").is("latte")),
            new Update().set("name", "latte-improved"), Coffee.class);

        assert updateResult.getModifiedCount() == 1;
        log.info("UpsertedId:"+updateResult.getUpsertedId());

    }
}
