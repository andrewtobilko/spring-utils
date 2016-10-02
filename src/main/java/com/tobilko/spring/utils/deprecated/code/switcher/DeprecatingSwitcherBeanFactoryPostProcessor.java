package com.tobilko.spring.utils.deprecated.code.switcher;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 *
 * Created by Andrew Tobilko on 10/2/2016.
 *
 */
@Component
public class DeprecatingSwitcherBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
        String[] names = factory.getBeanDefinitionNames();
        for (String name : names) {
            BeanDefinition definition = factory.getBeanDefinition(name);
            try {
                Class<?> beanClass = Class.forName(definition.getBeanClassName());
                if (beanClass.isAnnotationPresent(DeprecatedClass.class)) {
                    DeprecatedClass annotation = beanClass.getAnnotation(DeprecatedClass.class);
                    if (beanClass.isAssignableFrom(annotation.value())) {
                        definition.setBeanClassName(annotation.value().getName());
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

}