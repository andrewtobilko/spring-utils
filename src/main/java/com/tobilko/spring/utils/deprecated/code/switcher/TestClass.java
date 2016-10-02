package com.tobilko.spring.utils.deprecated.code.switcher;

import org.springframework.stereotype.Component;

/**
 *
 * Created by Andrew Tobilko on 10/2/2016.
 *
 */
@Component
@DeprecatedClass(NewTestClass.class)
public class TestClass {

    public void method() { System.out.println("The old method gets called."); }

}
