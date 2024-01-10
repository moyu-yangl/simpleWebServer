package com.simplewebserver.component;

import com.simplewebserver.exception.LifecycleException;

public interface Component extends Lifecycle {
    @Override
    default void start() throws LifecycleException {

    }

    @Override
    default void init() throws LifecycleException {

    }

    @Override
    default void stop() throws LifecycleException {

    }

    @Override
    default void close() throws LifecycleException {

    }

    @Override
    default LifecycleState getState() {
        return null;
    }

    @Override
    default void addLifecycleListener(LifecycleListener listener) {

    }
}
