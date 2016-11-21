package com.tobilko.spring.scope;

import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 *
 * Created by Andrew Tobilko on 11/7/2016.
 *
 */
@Scope(scopeName = "super-scope")
public class MyScopeBean {

    @PostConstruct
    public void afterPropertiesSet() {
        System.out.println("init");
    }

    @PreDestroy
    public void beforeDesctruction() {
        System.out.println("destroy");
    }

}
