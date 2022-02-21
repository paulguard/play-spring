package com.pxd.spring.model;

import java.lang.reflect.Method;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * @author by 平向东
 * @date 2021/12/30 20:22 Copyright 2021 北京交个朋友数码科技有限公司. All rights reserved.
 */
@Component
public class MyPostProcessor implements BeanPostProcessor {

    @Nullable
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(beanName.equals("car")){
            //返回一个新的代理对象回去
            return new CGLIBProxy(bean).createProxy();
        }
        return bean;
    }

    public class CGLIBProxy implements MethodInterceptor {

        private Object obj;
        public CGLIBProxy(Object obj){
            this.obj = obj;
        }

        public  Object createProxy(){
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(obj.getClass());
            enhancer.setCallback(new CGLIBProxy(obj));
            return enhancer.create();
        }

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            System.out.println("----" + method.getName() + "方法开始----");
            Object res = method.invoke(obj, objects);
            System.out.println("----" + method.getName() + "方法结束----");
            return res;
        }
    }

}
