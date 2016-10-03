package com.tobilko.spring.utils.scope;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * A class to prevent a high coupling architecture on the context startup phase.
 * Avoids old-fashioned and "dangerous" {@code InitializingBean} and {@code DisposableBean} interfaces.
 *
 * Created by Andrew Tobilko on 10/3/2016.
 *
 */
public class HighCouplingPreventerBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // gets called exactly before the afterPropertiesSet method
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

}
