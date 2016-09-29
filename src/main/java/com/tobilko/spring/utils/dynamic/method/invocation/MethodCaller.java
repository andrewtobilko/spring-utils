package com.tobilko.spring.utils.dynamic.method.invocation;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Standard method invocation by Reflection API by its name.
 *
 * Created by Andrew Tobilko on 9/29/2016.
 *
 */
public class MethodCaller {


    public static Object call(Object object, String methodName) {
        return call(object, methodName, null, false);
    }

    public static Object call(Object object, String methodName, Object... arguments) {
        return call(object, methodName, arguments, false);
    }

    /**
     *
     * @param object - an instance to invoke a {@param method} on
     * @param methodName - the method name
     * @param arguments - method's arguments
     * @param digDeep - whether only declared method are counted
     *
     * @return - an invocation method result
     */
    public static Object call(Object object, String methodName, Object[] arguments, boolean digDeep) {
        Object invocationResult = null;
        try {
            Class<?> c = object.getClass();
            Method method = digDeep ? c.getDeclaredMethod(methodName) : c.getMethod(methodName);
            method.setAccessible(true);
            invocationResult = method.invoke(object, arguments);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return invocationResult;
    }

}
