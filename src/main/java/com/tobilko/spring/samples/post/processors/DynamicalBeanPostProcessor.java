package com.tobilko.spring.samples.post.processors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;

/**
 *
 * Created by Andrew Tobilko on 10/8/2016.
 *
 */
public class DynamicalBeanPostProcessor extends GenericBeanPostProcessor {

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
        // like the first is, but this order doesn't matter if I will be register programmatically
    }

}
