package com.tobilko.spring.utils.scope;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import static java.util.stream.Stream.*;

/**
 * A class to prevent a high coupling architecture on the context startup phase.
 * Avoids old-fashioned and "dangerous" {@code InitializingBean} and {@code DisposableBean} interfaces.
 *
 * Created by Andrew Tobilko on 10/3/2016.
 */
@Component
@RequiredArgsConstructor
public class HighCouplingPreventerBeanPostProcessor implements BeanPostProcessor {

    // throwing an exception or printing a message out
    private @NonNull boolean isStrictMode;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // gets called exactly before the afterPropertiesSet method
        if(of(bean.getClass().getInterfaces()).anyMatch(c -> c.equals(InitializingBean.class) || c.equals(DisposableBean.class))) {
            String message = "Try not to couple the code to Spring interfaces.";
            if(isStrictMode) {
                throw new BeansException(message) {};
            } else {
                System.err.println(message);
            }
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

}