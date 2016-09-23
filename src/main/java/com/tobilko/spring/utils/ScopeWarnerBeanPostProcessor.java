package com.tobilko.spring.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.lang.reflect.Method;


/**
 * The purpose is to avoid mistaken destroy methods in beans which become uncontrolled by the container after their creation.
 *
 * Created by Andrew Tobilko on 23/09/16.
 */
@Component
public class ScopeWarnerBeanPostProcessor implements BeanPostProcessor {

    private String warning = "Methods marked by the PreDestroy annotation won't get called!";

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Scope.class) &&
                bean.getClass().getAnnotation(Scope.class).value().equals(ConfigurableBeanFactory.SCOPE_PROTOTYPE)) {
            for (Method method : bean.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(PreDestroy.class)) {
                    throw new BeanCreationException(warning);
                    // not sure whether there is a need of throwing an exception here
                    // possibly, a better way is printing something out in the warning stream
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
