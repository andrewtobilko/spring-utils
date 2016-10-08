package com.tobilko.spring;

import com.tobilko.spring.samples.lifecycle.EnhancedLifecycleTestBean;
import com.tobilko.spring.samples.lifecycle.LifecycleTestBean;
import com.tobilko.spring.utils.deprecated.code.switcher.TestClass;
import com.tobilko.spring.utils.scope.TestBeanA;
import com.tobilko.spring.utils.scope.TestBeanB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.springframework.boot.SpringApplication.run;


/**
 *
 * Created by Andrew Tobilko on 23/09/16.
 *
 */
@SpringBootApplication
public class Starter {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext originContext = run(Starter.class, args);
        originContext.stop();
    }

    private @Autowired TestBeanA a;
    private @Autowired TestBeanB b;
    private @Autowired TestClass c;

    // to invoke all callbacks
    private @Autowired EnhancedLifecycleTestBean enhancedLifecycleTestBean;
    private @Autowired LifecycleTestBean lifecycleTestBean;

}