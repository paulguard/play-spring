package com.pxd.spring;

import com.pxd.spring.model.Car;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author by 平向东
 * @date 2021/12/29 16:05 Copyright 2021 北京交个朋友数码科技有限公司. All rights reserved.
 */
public class Application {

    public static void main(String[] args) {

        ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
        Car car = (Car)application.getBean("car");

        System.out.println(car.getName());
    }



}
