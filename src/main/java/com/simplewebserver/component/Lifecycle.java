package com.simplewebserver.component;

import com.simplewebserver.exception.LifecycleException;

public interface Lifecycle {
    void start() throws LifecycleException;

    void init() throws LifecycleException;

    void stop() throws LifecycleException;

    void close() throws LifecycleException;

    LifecycleState getState();

    void addLifecycleListener(LifecycleListener listener);

}
