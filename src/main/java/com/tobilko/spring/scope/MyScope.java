package com.tobilko.spring.scope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * Created by Andrew Tobilko on 11/6/2016.
 *
 */
public class MyScope implements Scope {

    private Map<String, Object> map = new ConcurrentHashMap<>();
    private final String id = UUID.randomUUID().toString();

    @Override
    public Object get(String name, ObjectFactory<?> factory) {
        if (map.containsKey(name)) {
            return map.get(name);
        } else {
            Object bean = factory.getObject();
            map.put(bean.getClass().getSimpleName(), bean); // todo : name ???

            return bean;
        }
    }

    @Override
    public Object remove(String name) {
        return map.remove(name);
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
        System.out.println("The instance [" + name + "] was removed from the scope! Running a callback...");
        callback.run();
    }

    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }

    @Override
    public String getConversationId() {
        return id;
    }
}
