package com.tobilko.spring.utils.dynamic.method.invocation;

/**
 *
 * Created by Andrew Tobilko on 9/30/2016.
 *
 */
public class TestClass {

    public static void main(String[] args) {
        System.out.println(MethodCaller.call(new TestClass(), "sayHello", "Andrew"));
    }

    public String sayHello(String s) {
        return "Hello, " + s + "!";
    }
}
