package com.example.pxdcoffee;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import com.example.pxdcoffee.model.Coffee;
import com.example.pxdcoffee.repository.CoffeeRepository;
import com.example.pxdcoffee.service.CoffeeOrderService;
import com.example.pxdcoffee.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@EnableTransactionManagement
@SpringBootApplication
@EnableJpaRepositories
public class PxdCoffeeApplication implements ApplicationRunner {

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Autowired
    private CoffeeService coffeeService;

    @Resource
    private CoffeeOrderService coffeeOrderService;

    public static void main(String[] args) {
        SpringApplication.run(PxdCoffeeApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {

        List<Coffee> all = coffeeRepository.findAll();
        log.info("all coffee:"+all);
        log.info("size:"+all.size());

        Optional<Coffee> latte = coffeeService.findByName("latte");
        log.info(latte.get().toString());
        assert latte.get().getName().equals("latte");
    }
}
