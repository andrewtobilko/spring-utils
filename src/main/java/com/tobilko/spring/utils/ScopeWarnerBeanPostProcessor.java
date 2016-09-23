package com.tobilko.spring.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.lang.reflect.Method;


/**
 *
 *
 * Created by Andrew Tobilko on 23/09/16.
 *
 */
@Component
public class ScopeWarnerBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Scope.class) && bean.getClass().getAnnotation(Scope.class).scopeName().equals(ConfigurableBeanFactory.SCOPE_PROTOTYPE)) {
            for (Method method : bean.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(PreDestroy.class)) {
                    throw new IllegalAccessError("!!!!!!!!!!!");
                }
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

}
