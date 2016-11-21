package com.tobilko.spring.utils.deprecated.code.switcher;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionValidationException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 *
 * Created by Andrew Tobilko on 10/2/2016.
 *
 */
public class DeprecatingSwitcherBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
        for (String name : factory.getBeanDefinitionNames()) {
            BeanDefinition definition = factory.getBeanDefinition(name);
            try {
                Class<?> beanClass = Class.forName(definition.getBeanClassName());
                if (beanClass.isAnnotationPresent(DeprecatedClass.class)) {
                    DeprecatedClass annotation = beanClass.getAnnotation(DeprecatedClass.class);
                    if (beanClass.isAssignableFrom(annotation.value())) {
                        definition.setBeanClassName(annotation.value().getName());
                        throw new BeanDefinitionValidationException("A new class doesn't satisfy a deprecated one.");
                        // doesn't extend that
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

}