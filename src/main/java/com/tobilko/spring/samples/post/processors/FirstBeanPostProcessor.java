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
public class FirstBeanPostProcessor extends GenericBeanPostProcessor {

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }

}
