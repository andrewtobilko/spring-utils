package com.tobilko.spring.utils;

import com.tobilko.spring.utils.deprecated.code.switcher.TestClass;
import com.tobilko.spring.utils.scope.HighCouplingPreventerBeanPostProcessor;
import com.tobilko.spring.utils.scope.TestBeanA;
import com.tobilko.spring.utils.scope.TestBeanB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.springframework.boot.SpringApplication.run;


/**
 *
 * Created by Andrew Tobilko on 23/09/16.
 *
 */
@SpringBootApplication
public class Starter {

    public static void main(String[] args) { run(Starter.class, args); }

    private @Autowired TestBeanA a;
    private @Autowired TestBeanB b;
    private @Autowired TestClass c;

    public BeanPostProcessor getPreventer() {
        return new HighCouplingPreventerBeanPostProcessor(true);
    }

}
