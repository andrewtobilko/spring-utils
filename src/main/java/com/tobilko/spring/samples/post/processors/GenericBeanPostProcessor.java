package com.tobilko.spring.samples.post.processors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 *
 * Created by Andrew Tobilko on 10/8/2016.
 *
 */
@Component
public class GenericBeanPostProcessor implements BeanPostProcessor, Ordered {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(this.getClass().getSimpleName() + ":postProcessBeforeInitialization");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(this.getClass().getSimpleName() + ":postProcessAfterInitialization");
        return bean;
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
