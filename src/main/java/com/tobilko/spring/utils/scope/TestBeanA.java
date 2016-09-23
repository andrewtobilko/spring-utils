package com.tobilko.spring.utils.scope;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 *
 * Created by Andrew Tobilko on 23/09/16.
 *
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TestBeanA {

    @PreDestroy
    public void destroy() {
        System.out.println("I shouldn't get called.");
    }

}
