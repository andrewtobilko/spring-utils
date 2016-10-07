package com.tobilko.spring.samples.lifecycle;

import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

/**
 *
 * Created by Andrew Tobilko on 10/7/2016.
 *
 */
@Component
public class EnhancedLifecycleTestBean extends LifecycleTestBean implements SmartLifecycle {

    @Override
    public boolean isAutoStartup() {
        return true; // I'll start on context's refresh event
    }

    @Override
    public void stop(Runnable callback) {
        System.out.println("The callback is running...");
        callback.run();
        System.out.println("The callback has been finished.");
    }

    @Override
    public int getPhase() {
        return 0;
    }
}
