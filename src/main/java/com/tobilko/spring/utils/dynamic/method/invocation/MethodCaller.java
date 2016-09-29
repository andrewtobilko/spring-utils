package com.tobilko.spring.utils.dynamic.method.invocation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 *
 * Created by Andrew Tobilko on 9/29/2016.
 *
 */
public class MethodCaller {

    public static Object call(Object object, String methodName, Object... arguments) {
        return call(object, methodName, arguments, false);
    }

    public static Object call(Object object, String methodName, Object[] arguments, boolean digDeep) {
        Class<?> c = object.getClass();
        try {
            Method method = digDeep ? c.getDeclaredMethod(methodName) : c.getMethod(methodName);
            method.invoke(object, arguments);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
