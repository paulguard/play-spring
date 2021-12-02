package com.example.redispxd;

import java.util.Map;

import javax.annotation.Resource;

import com.example.redispxd.service.CoffeeOrderService;
import com.example.redispxd.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Slf4j
@EnableTransactionManagement
@EnableJpaRepositories
@SpringBootApplication
public class RedisPxdApplication implements ApplicationRunner {

    @Resource
    private CoffeeService coffeeService;

    @Resource
    private JedisPoolConfig jedisPoolConfig;

    @Resource
    private JedisPool jedisPool;

    public static void main(String[] args) {
        SpringApplication.run(RedisPxdApplication.class, args);
    }

    @Bean
    @ConfigurationProperties("redis")
    public JedisPoolConfig jedisPoolConfig(){
        return new JedisPoolConfig();
    }

    @Bean(destroyMethod = "close")
    public JedisPool jedisPool(@Value("${redis.host}") String host){
        return new JedisPool(jedisPoolConfig(),host);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        log.info(jedisPoolConfig.toString());

        try(Jedis jedis = jedisPool.getResource()){
            coffeeService.findAllCoffee().forEach(
                c -> jedis.hset("pxdbucks-menu",c.getName(),Long.toString(c.getPrice().getAmountMinorLong()))
            );

            Map<String, String> menu = jedis.hgetAll("pxdbucks-menu");
            log.info("Menu:{}",menu);

            String price = jedis.hget("pxdbucks-menu", "latte");
            log.info("latte - {}",
                Money.ofMinor(CurrencyUnit.of("CNY"), Long.parseLong(price)));
        }

    }
}
