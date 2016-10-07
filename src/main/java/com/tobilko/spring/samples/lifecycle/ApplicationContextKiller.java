package com.tobilko.spring.samples.lifecycle;

import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * Created by Andrew Tobilko on 10/7/2016.
 *
 */
@Component
public class ApplicationContextKiller implements ApplicationContextAware {

    // keeps a context to kill it later
    private @Getter ConfigurableApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = (ConfigurableApplicationContext) context;
    }
}
