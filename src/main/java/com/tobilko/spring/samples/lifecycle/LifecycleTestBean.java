package com.tobilko.spring.samples.lifecycle;

import org.springframework.context.Lifecycle;
import org.springframework.stereotype.Component;

/**
 *
 * Created by Andrew Tobilko on 10/7/2016.
 *
 */
@Component
public class LifecycleTestBean implements Lifecycle {

    public void start() {
        System.out.println("The Context has been started!");
    }

    public void stop() {
        System.out.println("The Context has been shut down!");
    }

    public boolean isRunning() {
        return true; // don't waste time to close, if I'm not running
    }

}