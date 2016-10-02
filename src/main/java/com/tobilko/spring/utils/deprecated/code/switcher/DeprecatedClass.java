package com.tobilko.spring.utils.deprecated.code.switcher;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * A market interface to highlight a "deprecated" class (or a class with deprecated methods).
 * Another process will replace such class with its new alternative.
 *
 * Created by Andrew Tobilko on 10/2/2016.
 *
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface DeprecatedClass {

    Class<?> value();

}
